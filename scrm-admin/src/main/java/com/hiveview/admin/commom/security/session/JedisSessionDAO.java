/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hiveview.admin.commom.security.session;

import com.google.common.collect.Sets;
import com.hiveview.admin.commom.RequestContextHolder;
import com.hiveview.cache.redis.RedisService;
import com.hiveview.base.util.DateUtils;
import com.hiveview.base.util.serializer.ObjectUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 自定义授权会话管理类
 * @author ThinkGem
 * @version 2014-7-20
 */
public class JedisSessionDAO extends AbstractSessionDAO implements SessionDAO {
	
	@Autowired
	private RedisService redisService;

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private String sessionKeyPrefix;

	@Override
	public void update(Session session) throws UnknownSessionException {
		if (session == null || session.getId() == null) {
            return;
        }
		
		HttpServletRequest request = RequestContextHolder.getRequest();
		if (request != null){
			
		}
		
		try {
			// 获取登录者编号
			PrincipalCollection pc = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			String principalId = pc != null ? pc.getPrimaryPrincipal().toString() : "";
			redisService.setHashString(sessionKeyPrefix, session.getId().toString(), principalId + "|" + session.getTimeout() + "|" + session.getLastAccessTime().getTime());
			redisService.setObjectBytes(sessionKeyPrefix + session.getId(), ObjectUtils.serialize(session));
			// 设置超期时间
			int timeoutSeconds = (int)(session.getTimeout() / 1000);

			redisService.expire(sessionKeyPrefix + session.getId(), timeoutSeconds);

			logger.debug("update {} {}", session.getId(), request != null ? request.getRequestURI() : "");
		} catch (Exception e) {
			logger.error("update {} {}", session.getId(), request != null ? request.getRequestURI() : "");
		}
	}

	@Override
	public void delete(Session session) {
		if (session == null || session.getId() == null) {
			return;
		}
		try {
			redisService.mapRemove(sessionKeyPrefix, session.getId().toString());
			redisService.deleteByKey(sessionKeyPrefix + session.getId());
			logger.debug("delete {} ", session.getId());
		} catch (Exception e) {
			logger.error("delete {} ", session.getId(), e);
		} 
	}
	
	@Override
	public Collection<Session> getActiveSessions() {
		return getActiveSessions(true);
	}
	
	/**
	 * 获取活动会话
	 * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @return
	 */
	@Override
	public Collection<Session> getActiveSessions(boolean includeLeave) {
		return getActiveSessions(includeLeave, null, null);
	}
	
	/**
	 * 获取活动会话
	 * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @param principal 根据登录者对象获取活动会话
	 * @param filterSession 不为空，则过滤掉（不包含）这个会话。
	 * @return
	 */
	@Override
	public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession){
		Set<Session> sessions = Sets.newHashSet();
		
		try {
			Map<String, String> map = redisService.getHashAllString(sessionKeyPrefix);
			if(null != map){
				for (Map.Entry<String, String> e : map.entrySet()){
					if (StringUtils.hasText(e.getKey()) && StringUtils.hasText(e.getValue())){
						
						String[] ss = StringUtils.split(e.getValue(), "|");
						if (ss != null && ss.length == 3){
							SimpleSession session = new SimpleSession();
							session.setId(e.getKey());
							session.setAttribute("principalId", ss[0]);
							session.setTimeout(Long.valueOf(ss[1]));
							session.setLastAccessTime(new Date(Long.valueOf(ss[2])));
							try{
								// 验证SESSION
								session.validate();
								
								boolean isActiveSession = false;
								// 不包括离线并符合最后访问时间小于等于3分钟条件。
								if (includeLeave || DateUtils.pastMinutes(session.getLastAccessTime()) <= 3){
									isActiveSession = true;
								}
								// 符合登陆者条件。
								if (principal != null){
									PrincipalCollection pc = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
									if (principal.toString().equals(pc != null ? pc.getPrimaryPrincipal().toString() : "")){
										isActiveSession = true;
									}
								}
								// 过滤掉的SESSION
								if (filterSession != null && filterSession.getId().equals(session.getId())){
									isActiveSession = false;
								}
								if (isActiveSession){
									sessions.add(session);
								}
								
							}
							// SESSION验证失败
							catch (Exception e2) {
								redisService.mapRemove(sessionKeyPrefix, e.getKey());
							}
						}
						// 存储的SESSION不符合规则
						else{
							redisService.mapRemove(sessionKeyPrefix, e.getKey());
						}
					}
					// 存储的SESSION无Value
					else if (StringUtils.hasText(e.getKey())){
						redisService.mapRemove(sessionKeyPrefix, e.getKey());
					}
				}
			}
			
			//logger.info("getActiveSessions size: {} ", sessions.size());
		} catch (Exception e) {
			logger.error("getActiveSessions", e);
		} 
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		this.update(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		Session session = null;
		HttpServletRequest request=RequestContextHolder.getRequest();
		if(null != request){
			session = (Session)request.getAttribute("session_"+sessionId);
		}
		if(null != session){
			return session;
		}
		try {
			byte[] bytes=redisService.getObjectBytes(sessionKeyPrefix + sessionId);
			session = (Session) ObjectUtils.unserialize(bytes);
		} catch (Exception e) {
			logger.error("doReadSession {} {}", sessionId, e.getMessage());
		}
		if (request != null && session != null){
			request.setAttribute("session_"+sessionId, session);
		}
		return session;
	}
	
	@Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
    	try{
        	return super.readSession(sessionId);
    	}catch (UnknownSessionException e) {
			return null;
		}
    }

	public String getSessionKeyPrefix() {
		return sessionKeyPrefix;
	}

	public void setSessionKeyPrefix(String sessionKeyPrefix) {
		this.sessionKeyPrefix = sessionKeyPrefix;
	}

}

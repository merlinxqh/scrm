/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hiveview.admin.commom.security.session;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * 自定义WEB会话管理类
 * @author ThinkGem
 * @version 2014-7-20
 */
public class SessionManager extends DefaultWebSessionManager {
	private static final Logger log= LoggerFactory.getLogger(SessionManager.class);

	public SessionManager() {
		super();
	}

	@Override
	protected void onStart(Session session, SessionContext context) {
		super.onStart(session, context);
		ServletRequest request = WebUtils.getRequest(context);
		request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
	}

	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		// 如果参数中包含“__sid”参数，则使用此sid会话。 例如：http://localhost/project?__sid=xxx&__cookie=true
		String sid = request.getParameter("hiveview.admin.session.id");
		if (StringUtils.hasText(sid)) {
			// 是否将sid保存到cookie，浏览器模式下使用此参数。
			if (WebUtils.isTrue(request, "__cookie")){
		        HttpServletRequest rq = (HttpServletRequest)request;
		        HttpServletResponse rs = (HttpServletResponse)response;
				Cookie template = getSessionIdCookie();
		        Cookie cookie = new SimpleCookie(template);
				cookie.setValue(sid);
				cookie.saveTo(rq, rs);
			}
			// 设置当前session状态
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,ShiroHttpServletRequest.URL_SESSION_ID_SOURCE); // session来源与url
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sid);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        	return sid;
		}else{
			return super.getSessionId(request, response);
		}
	}
	
	@Override
	public void validateSessions() {
		super.validateSessions();
	}
	
	protected Session retrieveSession(SessionKey sessionKey) {
		/**
		 * 调试问题 将 父级方法code copy到此处
		 */
		Serializable sessionId = getSessionId(sessionKey);
		if (sessionId == null) {
			log.debug("Unable to resolve session ID from SessionKey [{}].  Returning null to indicate a " +
					"session could not be found.", sessionKey);
			return null;
		}

		ServletRequest request = null;
		if (sessionKey instanceof WebSessionKey) {
			request = ((WebSessionKey) sessionKey).getServletRequest();
		}

		if (request != null) {
			Object s = request.getAttribute(sessionId.toString());
			if (s != null) {
				return (Session) s;
			}
		}

		Session s = retrieveSessionFromDataSource(sessionId);
		if (s == null) {
			//session ID was provided, meaning one is expected to be found, but we couldn't find one:
			String msg = "Could not find session with ID [" + sessionId + "]";
			throw new UnknownSessionException(msg);
		}

		// ***************Add By Goma****************
		if (request != null) {
			request.setAttribute(sessionId.toString(),s);
		}
		return s;
	}

    public Date getStartTimestamp(SessionKey key) {
    	try{
    		return super.getStartTimestamp(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    public Date getLastAccessTime(SessionKey key) {
    	try{
    		return super.getLastAccessTime(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    public long getTimeout(SessionKey key){
    	try{
    		return super.getTimeout(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return 0;
		}
    }

    public void setTimeout(SessionKey key, long maxIdleTimeInMillis) {
    	try{
    		super.setTimeout(key, maxIdleTimeInMillis);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
		}
    }

    public void touch(SessionKey key) {
    	try{
	    	super.touch(key);
		}catch (InvalidSessionException e) {
			// 获取不到SESSION不抛出异常
		}
    }

    public String getHost(SessionKey key) {
    	try{
    		return super.getHost(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    public Collection<Object> getAttributeKeys(SessionKey key) {
    	try{
    		return super.getAttributeKeys(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    public Object getAttribute(SessionKey sessionKey, Object attributeKey) {
    	try{
    		return super.getAttribute(sessionKey, attributeKey);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value) {
    	try{
    		super.setAttribute(sessionKey, attributeKey, value);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
		}
    }

    public Object removeAttribute(SessionKey sessionKey, Object attributeKey) {
    	try{
    		return super.removeAttribute(sessionKey, attributeKey);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
        	return null;
		}
    }

    public void stop(SessionKey key) {
    	try{
    		super.stop(key);
    	}catch (InvalidSessionException e) {
    		// 获取不到SESSION不抛出异常
		}
    }
    
    public void checkValid(SessionKey key) {
    	try{
    		super.checkValid(key);
		}catch (InvalidSessionException e) {
			// 获取不到SESSION不抛出异常
		}
    }
    
    @Override
    protected Session doCreateSession(SessionContext context) {
    	try{
    		return super.doCreateSession(context);
		}catch (IllegalStateException e) {
			return null;
		}
    }

	@Override
	protected Session newSessionInstance(SessionContext context) {
		Session session = super.newSessionInstance(context);
		session.setTimeout(getGlobalSessionTimeout());
		return session;
	}
    
    @Override
    public Session start(SessionContext context) {
    	try{
    		return super.start(context);
		}catch (NullPointerException e) {
			SimpleSession session = new SimpleSession();
			session.setId(0);
			return session;
		}
    }
}
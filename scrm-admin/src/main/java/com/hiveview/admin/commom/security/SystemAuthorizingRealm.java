/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hiveview.admin.commom.security;

import com.hiveview.admin.commom.SystemUserUtils;
import com.hiveview.base.common.properties.Global;
import com.hiveview.base.spring.SpringContextHolder;
import com.hiveview.pms.dto.SysUserDto;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 系统安全认证实现类
 * @author ThinkGem
 * @version 2014-7-5
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private SystemService systemService;



	/**
     * Shiro权限认证 回调方法
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String principal = (String) getAvailablePrincipal(principals);
		if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))){
			Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(true, principal, SystemUserUtils.getSession());
			if (sessions.size() > 0){
				// 如果是登录进来的，则踢出已在线用户
				if (SystemUserUtils.getSubject().isAuthenticated()){
					for (Session session : sessions){
						getSystemService().getSessionDao().delete(session);
					}
				}
				// 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
				else{
					SystemUserUtils.getSubject().logout();
					throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
				}
			}
		}
		
		SysUserDto user = SystemUserUtils.getCurrentUser();
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			Map<String,String> map = SystemUserUtils.getPermissionMap();
			for (String permission : map.keySet()){
				info.addStringPermission(permission);
			}
			info.addRole(user.getDefaultRole().getCode());
			return info;
		} else {
			return null;
		}
	}
	
	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}
	
	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
        		authorizationValidate(permission);
            }
        }
		return super.isPermitted(permissions, info);
	}
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		return super.isPermitted(principals, permission);
	}
	
	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
            	authorizationValidate(permission);
            }
        }
		return super.isPermittedAll(permissions, info);
	}
	
	
	/**
	 * 授权验证方法
	 * @param permission
	 */
	private boolean authorizationValidate(Permission permission){
		Map<String,String> map = SystemUserUtils.getPermissionMap();
		String currentPermission=permission.toString();
		currentPermission=currentPermission.replaceAll("[\\[\\]]", "").trim();
		return map.containsKey(currentPermission);
	}
	
	 /**
     * 登录校验
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        SysUserDto user;
        try {
			user=getSystemService().getUserByUserName(token.getUsername());
			SystemUserUtils.setCurrentUser(user);
			//TODO 判断账号 禁用状态 等等...
		} catch (RuntimeException e) {
				throw new AuthenticationException("msg:"+e.getMessage());
		}
		return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
	}
	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}
}

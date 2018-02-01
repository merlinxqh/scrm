package com.hiveview.admin.commom.security;

import com.hiveview.admin.commom.SystemUserUtils;
import com.hiveview.base.util.encry.EncryUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * shiro密码方式配置
 * @author leo
 *
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher{

	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken; 
		Object tokenCredentials = EncryUtils.encryPwd(String.valueOf(token.getPassword()));
        Object accountCredentials = getCredentials(info);
        boolean result=equals(tokenCredentials, accountCredentials);
        if(result){
        	//登录校验通过
        	SystemUserUtils.initUserLoginSuccessEvent(SystemUserUtils.getCurrentUser());
		}
		return  result;
	}
}

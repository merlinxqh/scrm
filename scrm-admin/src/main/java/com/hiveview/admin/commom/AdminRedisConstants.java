package com.hiveview.admin.commom;

/**
 * admin subsystem redis key constants
 */
public class AdminRedisConstants {


   /**
     * 角色信息
     */
    public static final String ADMIN_SECURITY_ROLE_KEY ="admin:security:role:%s";

    /**
     * 资源信息
     */
    public static final String ADMIN_SECURITY_RESOURCE_KEY ="admin:security:resource:%s";
    
    /**
     * 存放所有登录用户信息
     */
    public static final String ADMIN_SECURITY_USER_MAP_KEY ="admin:security:user:map";

}

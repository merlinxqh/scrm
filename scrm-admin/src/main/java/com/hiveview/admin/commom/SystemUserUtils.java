package com.hiveview.admin.commom;

import com.hiveview.admin.rpc.pms.SysResourceApiConsumer;
import com.hiveview.base.spring.SpringContextHolder;
import com.hiveview.cache.redis.RedisService;
import com.hiveview.pms.dto.SysResourceDto;
import com.hiveview.pms.dto.SysRoleDto;
import com.hiveview.pms.dto.SysUserDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统 用户工具类
 * @author leo
 *
 */
public class SystemUserUtils {

	private static RedisService redisService= SpringContextHolder.getBean(RedisService.class);

	private static SysResourceApiConsumer sysResourceApiConsumer = SpringContextHolder.getBean(SysResourceApiConsumer.class);

	private static ThreadLocal<Map<String,String>> currentPermissionMap=new NamedThreadLocal<>("current permission map...");

	private static ThreadLocal<SysUserDto> currentUser=new NamedThreadLocal<>("current user info...");

	/**
	 * 获取当前登录用户
	 * @return
	 */
    public static SysUserDto getCurrentUser(){
		if(null != currentUser.get()){
    		return currentUser.get();
		}
		SysUserDto user=null;
    	String userName=(String) SecurityUtils.getSubject().getPrincipal();
    	if(redisService.mapExists(AdminRedisConstants.ADMIN_SECURITY_USER_MAP_KEY, userName)){
    		user=redisService.getHashObject(AdminRedisConstants.ADMIN_SECURITY_USER_MAP_KEY, userName, SysUserDto.class);
    	}
    	if(null != user){
			currentUser.set(user);
		}
    	return user;
    }

	/**
	 *
	 * @param dto
	 */
	public static void setCurrentUser(SysUserDto dto){
    	currentUser.set(dto);
	}


    /**
     * 初始threadLocal化信息
     */
    public static void setInitInfo(){
    	getCurrentUser();
    	getPermissionMap();
    }

    /**
     * 清空threadLocal信息
     */
    public static void clearInitInfo(){
		currentPermissionMap.remove();
		currentUser.remove();
    }

	/**
	 * 退出登录
	 */
	public static void logout(){
		String userName=(String) SecurityUtils.getSubject().getPrincipal();
		if(redisService.mapExists(AdminRedisConstants.ADMIN_SECURITY_USER_MAP_KEY, userName)){
			redisService.mapRemove(AdminRedisConstants.ADMIN_SECURITY_USER_MAP_KEY,userName);
		}
	}


	/**
	 * shiro登录校验通过后
	 * 初始化 用户登录成功 事件
	 * @param user
	 */
	public static void initUserLoginSuccessEvent(SysUserDto user){
        redisService.setHashObject(AdminRedisConstants.ADMIN_SECURITY_USER_MAP_KEY,user.getUsername(),user);
		String resourceKey=String.format(AdminRedisConstants.ADMIN_SECURITY_RESOURCE_KEY,user.getDefaultRole().getCode());
		//登录 资源数据重新获取 因此这里删除redis 该角色数据
		if(redisService.exists(resourceKey)){
			redisService.deleteByKey(resourceKey);
		}
	}

    /**
     * 获取当前登录人 资源数据
     * @return
     */
	public static List<SysResourceDto> getResourceList(){
    	List<SysResourceDto> resourceList=null;
    	SysUserDto user = getCurrentUser();
    	if(null != user && null != user.getDefaultRole()){
    		String resourceKey=String.format(AdminRedisConstants.ADMIN_SECURITY_RESOURCE_KEY,user.getDefaultRole().getCode());
    		if(redisService.exists(resourceKey)){
    			resourceList=redisService.getListObj(resourceKey,SysResourceDto.class);
			}else{
    			//调用rpc获取该用户权限数据
                resourceList=sysResourceApiConsumer.getResourceByRole(user.getDefaultRole().getCode());
                redisService.setListObj(resourceKey,resourceList);
			}
    	}
    	return resourceList;
    }

	/**
	 * 获取权限项
	 * @return
	 */
	public static Map<String,String> getPermissionMap(){
		if(null != currentPermissionMap.get()){
			return currentPermissionMap.get();
		}
		List<SysResourceDto> resourceList=getResourceList();
		Map<String,String> permissionMap=new HashMap<>();
		if(!CollectionUtils.isEmpty(resourceList)){
			resourceList.forEach(re->{
				if(org.springframework.util.StringUtils.hasText(re.getPermission())){
					permissionMap.put(re.getPermission().trim(),"");
				}
			});
		}
		currentPermissionMap.set(permissionMap);
		return permissionMap;
	}

	/**
	 * 是否包含权限
	 * @param permission
	 * @return
	 */
	public static boolean hasPermission(String permission){
		return getPermissionMap().containsKey(permission);
	}

    /**
     * 获取当前登录用户 角色
     * @return
     */
    public static List<SysRoleDto> getUserRoleList(){
    	List<SysRoleDto> roleList=null;
    	SysUserDto curUser=getCurrentUser();
    	if(null != curUser){
    		String redisKey=String.format(AdminRedisConstants.ADMIN_SECURITY_ROLE_KEY, curUser.getUsername());
    		if(redisService.exists(redisKey)){
    			roleList=redisService.getListObj(redisKey, SysRoleDto.class);
    		}
    	}
    	return roleList;
    }

	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
		}catch (InvalidSessionException e){

		}
		return null;
	}

	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
}

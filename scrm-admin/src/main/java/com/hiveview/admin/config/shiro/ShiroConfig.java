//package com.hiveview.admin.config.shiro;
//
//import com.hiveview.admin.commom.security.CustomCredentialsMatcher;
//import com.hiveview.admin.commom.security.FormAuthenticationFilter;
//import com.hiveview.admin.commom.security.SystemAuthorizingRealm;
//import com.hiveview.admin.commom.security.session.JedisCacheManager;
//import com.hiveview.admin.commom.security.session.JedisSessionDAO;
//import com.hiveview.admin.commom.security.session.SessionManager;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.servlet.SimpleCookie;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.context.annotation.Lazy;
//
//import javax.servlet.Filter;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by leo on 2018/2/5.
// * shiro 配置
// */
//@Configuration
//@Lazy
//public class ShiroConfig {
//
//    @Value("${redis.keyPrefix}")
//    private String keyPrefix;
//
//    @Value("${session.sessionTimeout}")
//    private long sessionTimeout;
//
//    /**
//     * 安全定义过滤器
//     * @return
//     */
//    @Bean
//    public ShiroFilterFactoryBean shiroFilter(){
//        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
//        filter.setSecurityManager(securityManager());
//        filter.setLoginUrl("/login");
//        filter.setSuccessUrl("/index");
//        filter.setUnauthorizedUrl("/unauthorized");
//        Map<String, Filter> filterMap = new HashMap<>();
//        filterMap.put("authc", formAuthenticationFilter());
//        filter.setFilters(filterMap);
//        filter.setFilterChainDefinitions("\n" +
//                "\t\t\t\t/mobile/** = anon\n" +
//                "\t\t\t\t/assets/** = anon\n" +
//                "\t\t\t\t/login = authc\n" +
//                "\t\t\t\t/logout = logout\n" +
//                "\t\t\t\t/** = user\n" +
//                "\t\t\t");
//        return filter;
//    }
//
//    @Bean
//    public FormAuthenticationFilter formAuthenticationFilter(){
//        FormAuthenticationFilter filter = new FormAuthenticationFilter();
//        return filter;
//    }
//
//    @Bean
//    public DefaultWebSecurityManager securityManager(){
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(systemAuthorizingRealm());
//        securityManager.setSessionManager(sessionManager());
//        securityManager.setCacheManager(shiroCacheManager());
//        return securityManager;
//    }
//
//    @Bean
//    public SystemAuthorizingRealm systemAuthorizingRealm(){
//        SystemAuthorizingRealm realm = new SystemAuthorizingRealm();
//        realm.setCredentialsMatcher(credentialsMatcher());
//        realm.setAuthenticationCacheName("");
//        return realm;
//    }
//
//    @Bean //down
//    public SessionManager sessionManager(){
//        SessionManager sessionManager = new SessionManager();
//        sessionManager.setSessionDAO(sessionDAO());
//        sessionManager.setGlobalSessionTimeout(sessionTimeout);
//        sessionManager.setSessionValidationSchedulerEnabled(false);
//        sessionManager.setSessionIdCookie(sessionIdCookie());
//        sessionManager.setSessionIdCookieEnabled(true);
//        return sessionManager;
//    }
//
//    @Bean
//    public SimpleCookie sessionIdCookie(){
//        SimpleCookie cookie = new SimpleCookie("hiveview.admin.session.id");
//        return cookie;
//    }
//
//    @Bean
//    public JedisSessionDAO sessionDAO(){
//        JedisSessionDAO sessionDAO = new JedisSessionDAO();
//        sessionDAO.setSessionKeyPrefix(keyPrefix+":cache:");
//        return sessionDAO;
//    }
//
//    @Bean
//    public JedisCacheManager shiroCacheManager(){
//        JedisCacheManager cacheManager = new JedisCacheManager();
//        cacheManager.setCacheKeyPrefix(keyPrefix+":cache:");
//        return cacheManager;
//    }
//
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
//        return new LifecycleBeanPostProcessor();
//    }
//
//    @Bean
//    @DependsOn(value = "lifecycleBeanPostProcessor")
//    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
//        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
//        proxyCreator.setProxyTargetClass(true);
//        return proxyCreator;
//    }
//
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
//        AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        sourceAdvisor.setSecurityManager(securityManager());
//        return sourceAdvisor;
//    }
//
//    @Bean
//    public CustomCredentialsMatcher credentialsMatcher(){
//        CustomCredentialsMatcher credentialsMatcher = new CustomCredentialsMatcher();
//        return credentialsMatcher;
//    }
//
//}

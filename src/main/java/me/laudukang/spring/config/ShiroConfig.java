package me.laudukang.spring.config;

import me.laudukang.spring.shiro.AdminShiroRealm;
import me.laudukang.spring.shiro.UserShiroRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/24
 * <p>Time: 10:55
 * <p>Version: 1.0
 */
@Configuration
@ComponentScan({"me.laudukang.spring.shiro"})
@Import(PersistenceJPAConfig.class)
public class ShiroConfig {

    @Bean
    public EhCacheManager cacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return ehCacheManager;
    }

    @Bean
    public CacheManager ehCacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }

    @Bean
    public AuthorizingRealm userShiroRealm() {
        AuthorizingRealm userShiroRealm = new UserShiroRealm();
        userShiroRealm.setCachingEnabled(true);
        userShiroRealm.setAuthenticationCachingEnabled(false);
        //userShiroRealm.setAuthenticationCacheName("UserAuthenticationCache");
        userShiroRealm.setAuthorizationCachingEnabled(true);
        userShiroRealm.setAuthorizationCacheName("UserAuthorizationCache");
        return userShiroRealm;
    }

    @Bean
    public AuthorizingRealm adminShiroRealm() {
        AuthorizingRealm adminShiroRealm = new AdminShiroRealm();
        adminShiroRealm.setCachingEnabled(true);
        adminShiroRealm.setAuthenticationCachingEnabled(false);
        //adminShiroRealm.setAuthenticationCacheName("AdminAuthenticationCache");
        adminShiroRealm.setAuthorizationCachingEnabled(true);
        adminShiroRealm.setAuthorizationCacheName("AdminAuthorizationCache");
        return adminShiroRealm;
    }

    @Bean
    public DefaultSessionManager sessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(1000 * 60 * 60);//1000*sec*min
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(60 * 60);
        defaultWebSessionManager.setSessionIdCookie(simpleCookie);
//        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
//        defaultWebSessionManager.setSessionValidationScheduler(sessionValidationScheduler());
        defaultWebSessionManager.setSessionDAO(sessionDAO());
        return defaultWebSessionManager;
    }

    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public SessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return sessionDAO;
    }

//    @Bean
//    public SessionValidationScheduler sessionValidationScheduler() {
//        QuartzSessionValidationScheduler quartzSessionValidationScheduler = new QuartzSessionValidationScheduler();
//        quartzSessionValidationScheduler.setSessionValidationInterval(1000 * 60 * 60);
//        return quartzSessionValidationScheduler;
//    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        AuthorizingRealm userShiroRealm = userShiroRealm();
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager(userShiroRealm);
        defaultWebSecurityManager.setCacheManager(cacheManager());
        defaultWebSecurityManager.setSessionManager(sessionManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManagerAdmin() {
        AuthorizingRealm adminShiroRealm = adminShiroRealm();
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager(adminShiroRealm);
        defaultWebSecurityManager.setCacheManager(cacheManager());
        defaultWebSecurityManager.setSessionManager(sessionManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public LogoutFilter logoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login");
        return logoutFilter;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setSuccessUrl("/docs");
        Map<String, String> filterChainDefinitionMapping = new HashMap<>();
        filterChainDefinitionMapping.put("/register", "anon");
        filterChainDefinitionMapping.put("/resetRequest", "anon");
        filterChainDefinitionMapping.put("/resetPassword", "anon");
        filterChainDefinitionMapping.put("/download", "anon");
        filterChainDefinitionMapping.put("/userInfo", "authc,roles[user]");
        filterChainDefinitionMapping.put("/updatePassword", "authc,roles[user]");
        filterChainDefinitionMapping.put("/docs", "authc,roles[user]");
        filterChainDefinitionMapping.put("/newDoc", "authc,roles[user]");
        filterChainDefinitionMapping.put("/updateDoc", "authc,roles[user]");
        filterChainDefinitionMapping.put("/deleteDoc/*", "roles[user]");
        filterChainDefinitionMapping.put("/messages", "roles[user]");
        filterChainDefinitionMapping.put("/userMessages", "roles[user]");
        filterChainDefinitionMapping.put("/logout", "logoutFilter");
        filterChainDefinitionMapping.put("/upload/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMapping);
        return shiroFilterFactoryBean;
    }

    @Bean
    public ShiroFilterFactoryBean adminShiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManagerAdmin());
        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setSuccessUrl("/admin/docs");
        Map<String, String> filterChainDefinitionMapping = new HashMap<>();
        filterChainDefinitionMapping.put("/admin", "anon");
        filterChainDefinitionMapping.put("/admin/login", "anon");
        filterChainDefinitionMapping.put("/admin/updatePassword", "authc");
        filterChainDefinitionMapping.put("/admin/delete/*", "authc,perms[ADMIN:DELETE]");
        filterChainDefinitionMapping.put("/admin/able", "authc,perms[ADMIN:UPDATE]");
        filterChainDefinitionMapping.put("/admin/updateInfo", "authc");
        filterChainDefinitionMapping.put("/admin/updateOtherInfo", "authc,perms[ADMIN:UPDATE]");
        filterChainDefinitionMapping.put("/admin/adminInfo/*", "authc,perms[ADMIN:UPDATE]");
        filterChainDefinitionMapping.put("/admin/info", "authc,roles[Administrator,Reviewer]");
        filterChainDefinitionMapping.put("/admin/admins", "authc,perms[ADMIN:QUERY]");
        filterChainDefinitionMapping.put("/admin/reviewerList", "roles[Administrator]");
        filterChainDefinitionMapping.put("/admin/docs", "authc,roles[Administrator]");
        filterChainDefinitionMapping.put("/admin/docInfo/*", "authc,roles[Administrator]");
        filterChainDefinitionMapping.put("/admin/reviewDocInfo/*", "authc,roles[Reviewer]");
        filterChainDefinitionMapping.put("/admin/deleteDocSuper", "authc,roles[Administrator]");
        filterChainDefinitionMapping.put("/admin/review", "authc,roles[Reviewer]");
        filterChainDefinitionMapping.put("/admin/distributedDocs", "authc,roles[Administrator]");
        filterChainDefinitionMapping.put("/admin/messages", "roles[Administrator,Reviewer]");
        filterChainDefinitionMapping.put("/admin/adminMessages", "authc,roles[Administrator,Reviewer]");
        filterChainDefinitionMapping.put("/admin/saveReview", "authc,roles[Administrator,Reviewer]");
        filterChainDefinitionMapping.put("/admin/distribute", "authc,perms[DOC:DISTRIBUTE]");
        filterChainDefinitionMapping.put("/admin/roles", "authc,perms[ROLE:QUERY]");
        filterChainDefinitionMapping.put("/admin/saveRole", "authc,perms[ROLE:UPDATE]");
        filterChainDefinitionMapping.put("/admin/updateRole", "authc,perms[ROLE:UPDATE]");
        filterChainDefinitionMapping.put("/admin/roleInfo", "authc,perms[ROLE:QUERY]");
        filterChainDefinitionMapping.put("/admin/deleteUser", "authc,perms[USER:DELETE]");
        filterChainDefinitionMapping.put("/admin/users", "authc,perms[USER:QUERY]");
        filterChainDefinitionMapping.put("/admin/userInfo/*", "authc,perms[USER:UPDATE]");
        filterChainDefinitionMapping.put("/admin/logs", "perms[LOG:QUERY]");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMapping);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}

package me.laudukang.spring.config;

import me.laudukang.spring.shiro.AdminShiroRealm;
import me.laudukang.spring.shiro.UserShiroRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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
    public AuthorizingRealm userShiroRealm() {
        return new UserShiroRealm();
    }

    @Bean
    public AuthorizingRealm adminShiroRealm() {
        return new AdminShiroRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        AuthorizingRealm userShiroRealm = userShiroRealm();
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager(userShiroRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManagerAdmin() {
        AuthorizingRealm adminShiroRealm = adminShiroRealm();
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager(adminShiroRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setSuccessUrl("/docs");
        Map<String, String> filterChainDefinitionMapping = new HashMap<>();
        filterChainDefinitionMapping.put("/docs", "authc");
        filterChainDefinitionMapping.put("/newDoc", "authc");
        filterChainDefinitionMapping.put("/logout", "logout");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMapping);
        return shiroFilterFactoryBean;
    }

    @Bean
    public ShiroFilterFactoryBean adminShiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManagerAdmin());
        shiroFilterFactoryBean.setLoginUrl("/admin/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setSuccessUrl("/admin/docs");
        Map<String, String> filterChainDefinitionMapping = new HashMap<>();
        filterChainDefinitionMapping.put("/admin/docs", "authc");
        filterChainDefinitionMapping.put("/admin/login", "anon");
        filterChainDefinitionMapping.put("/admin/logout", "logout");
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
    public Cookie simpleCookie() {
        Cookie cookie = new SimpleCookie();
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 60);// min * second
        return cookie;
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}

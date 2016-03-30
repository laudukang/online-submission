package me.laudukang.spring.config;

import me.laudukang.util.CleanupContextListener;
import me.laudukang.util.MySessionListener;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.IntrospectorCleanupListener;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/22
 * <p>Time: 16:25
 * <p>Version: 1.0
 * 注释掉web.xml配置后使用
 */
public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationConfig.class);
        //rootContext.scan("me.laudukang.spring.config");
        servletContext.addListener(IntrospectorCleanupListener.class);
        servletContext.addListener(new MySessionListener());
        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.addListener(new CleanupContextListener());

        //Shiro
        DelegatingFilterProxy adminShiroFilter = new DelegatingFilterProxy();
        adminShiroFilter.setTargetFilterLifecycle(true);
        FilterRegistration filterRegistrationDelegatingFilterProxy2 = servletContext.addFilter("adminShiroFilter", adminShiroFilter);
        filterRegistrationDelegatingFilterProxy2.addMappingForUrlPatterns(null, true, "/admin/*");

        DelegatingFilterProxy shiroFilter = new DelegatingFilterProxy();
        shiroFilter.setTargetFilterLifecycle(true);
        FilterRegistration filterRegistrationDelegatingFilterProxy = servletContext.addFilter("shiroFilter", shiroFilter);
        filterRegistrationDelegatingFilterProxy.addMappingForUrlPatterns(null, false, "/*");

        //CharacterEncodingFilter
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        FilterRegistration filterRegistration =
                servletContext.addFilter("characterEncodingFilter", characterEncodingFilter);
        filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");

        //OpenEntityManagerInViewFilter
        OpenEntityManagerInViewFilter openEntityManagerInViewFilter = new OpenEntityManagerInViewFilter();
        FilterRegistration filterRegistrationOpenEntityManagerInViewFilter =
                servletContext.addFilter("openEntityManagerInViewFilter", openEntityManagerInViewFilter);
        //filterRegistrationOpenEntityManagerInViewFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
        filterRegistrationOpenEntityManagerInViewFilter.addMappingForUrlPatterns(null, false, "/*");

        //Spring MVC
        AnnotationConfigWebApplicationContext springMvcContext = new AnnotationConfigWebApplicationContext();
        springMvcContext.register(MvcConfig.class);

        //DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(springMvcContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

        ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcherServlet", dispatcherServlet);
        dynamic.setLoadOnStartup(1);
        dynamic.addMapping("/");


    }

}

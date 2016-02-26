package me.laudukang.spring.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/4
 * <p>Time: 10:18
 * <p>Version: 1.0
 */
@SpringBootApplication
public class SpringBootApp extends SpringBootServletInitializer {
    private static Class<SpringBootApp> applicationClass = SpringBootApp.class;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootApp.class);
    }

    //@Bean
    //public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
    //    ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
    //    //registration.getUrlMappings().clear();
    //    registration.addUrlMappings("/");
    //    return registration;
    //}

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SpringBootApp.class, args);
        //String[] beanNames = ctx.getBeanDefinitionNames();
        //Arrays.sort(beanNames);
        //for (String beanName : beanNames) {
        //    System.out.println(beanName);
        //}
    }


}

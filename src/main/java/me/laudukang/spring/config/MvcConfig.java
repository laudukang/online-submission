package me.laudukang.spring.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import me.laudukang.spring.interceptor.LoginInterceptor;
import me.laudukang.spring.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/22
 * <p>Time: 16:25
 * <p>Version: 1.0
 * Todo: spring cache see D:\Git\spring4-showcase\spring-cache
 */
@Configuration
@ComponentScan(basePackages = "me.laudukang.spring.controller", useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class})
})
public class MvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private Environment environment;

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");
        bean.setOrder(1);
        return bean;
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/upload/**").addResourceLocations("/upload/");
        registry.addResourceHandler("/font/**").addResourceLocations("/font/");
        registry.addResourceHandler("/favicon.ico");
    }

    /**
     * How to Pretty Print Your JSON With Spring and Jackson
     * http://springinpractice.com/2013/11/01/how-to-pretty-print-your-json-with-spring-and-jackson
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(byteArrayHttpMessageConverter());
        converters.add(converter());
        addDefaultHttpMessageConverters(converters);
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        return byteArrayHttpMessageConverter;
    }

    @Bean
    public MappingJackson2HttpMessageConverter converter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        /**
         * see http://websystique.com/springmvc/spring-mvc-requestbody-responsebody-example/
         * 2016年1月22日15:52:08
         * laudukang
         */
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //objectMapper.registerModules(new Hibernate4Module());
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        long maxFileSizeLimit;
        try {
            maxFileSizeLimit = Long.valueOf(environment.getProperty("file.upload.size"));
        } catch (Exception e) {
            maxFileSizeLimit = 100;
        }
        multipartResolver.setMaxUploadSize(1024 * 1024 * maxFileSizeLimit);
        multipartResolver.setMaxInMemorySize(40960);
        File tmp = new File(environment.getProperty("file.upload.tmp"));
        if (!tmp.exists()) {
            tmp.mkdirs();
        }
        FileSystemResource fileSystemResource = new FileSystemResource(environment.getProperty("file.upload.tmp"));
        try {
            multipartResolver.setUploadTempDir(fileSystemResource);
        } catch (IOException e) {
            //multipartResolver.setUploadTempDir(new FileSystemResource(System.getProperty("java.io.tmpdir")));
        }
        return multipartResolver;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new DocUploadInterceptor()).addPathPatterns("/upload");//文件上传拦截器
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
//    @Bean
//    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
//        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
//        simpleMappingExceptionResolver.setDefaultErrorView("error");
//        simpleMappingExceptionResolver.setExceptionAttribute("ex");
//        Properties properties = new Properties();
//        properties.put("org.springframework.web.multipart.MaxUploadSizeExceededException", "error");
//        properties.put("java.lang.Exception", "error");
//        properties.put("java.lang.RuntimeException", "error");
//        properties.put("java.lang.Throwable", "error");
//        simpleMappingExceptionResolver.setExceptionMappings(properties);
//        return simpleMappingExceptionResolver;
//    }

}


package me.laudukang.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/22
 * Time: 16:25
 * Version: 1.0
 */
@Configuration
@EnableTransactionManagement
//@EnableWebMvc
//@ComponentScan(basePackages = "me.laudukang.spring.controller", excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class})
//})
@ComponentScan(basePackages = {"me.laudukang.persistence", "me.laudukang.spring.config"})
public class ApplicationConfig {
}

package me.laudukang.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/22
 * <p>Time: 16:25
 * <p>Version: 1.0
 */
@Configuration
@EnableTransactionManagement
@EnableSpringDataWebSupport
@PropertySource({"classpath:system.properties"})
//@EnableWebMvc
//@ComponentScan(basePackages = "me.laudukang.spring.controller", excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class})
//})
//@ComponentScan(basePackages = {"me.laudukang.spring.config"})//"me.laudukang.persistence",
public class ApplicationConfig {
    @Autowired
    private Environment environment;

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(environment.getProperty("mail.host"));
        mailSender.setPort(Integer.valueOf(environment.getProperty("mail.port")));
        mailSender.setUsername(environment.getProperty("mail.username"));
        mailSender.setPassword(environment.getProperty("mail.password"));
        mailSender.setDefaultEncoding(environment.getProperty("mail.defaultEncoding"));
        mailSender.setProtocol(environment.getProperty("mail.protocol"));
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", environment.getProperty("mail.transport.protocol"));
        properties.put("mail.smtp.auth", environment.getProperty("mail.smtp.auth"));
        properties.put("mail.smtp.starttls.enable", environment.getProperty("mail.smtp.starttls.enable"));
        properties.put("mail.debug", environment.getProperty("mail.debug"));
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() throws UnsupportedEncodingException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(environment.getProperty("mail.from"));
        simpleMailMessage.setSubject(environment.getProperty("mail.default.subject"));
        simpleMailMessage.setText(environment.getProperty("mail.default.text"));
        return simpleMailMessage;
    }
}

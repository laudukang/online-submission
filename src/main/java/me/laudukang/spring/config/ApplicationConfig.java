package me.laudukang.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/22
 * <p>Time: 16:25
 * <p>Version: 1.0
 */
@Configuration
@EnableSpringDataWebSupport
@PropertySource({"classpath:system.properties"})
@Import({AsyncConfig.class, ShiroConfig.class})
@EnableCaching
//@EnableWebMvc
//@EnableTransactionManagement
//@ComponentScan(basePackages = {"me.laudukang.spring.config"})
public class ApplicationConfig {
    @Autowired
    private Environment environment;


    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(environment.getProperty("mail.host"));
        javaMailSender.setPort(Integer.valueOf(environment.getProperty("mail.port")));
        javaMailSender.setUsername(environment.getProperty("mail.username"));
        javaMailSender.setPassword(environment.getProperty("mail.password"));
        javaMailSender.setDefaultEncoding(environment.getProperty("mail.defaultEncoding"));
        javaMailSender.setProtocol(environment.getProperty("mail.protocol"));
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", environment.getProperty("mail.transport.protocol"));
        properties.setProperty("mail.smtp.auth", environment.getProperty("mail.smtp.auth"));
        properties.setProperty("mail.smtp.starttls.enable", environment.getProperty("mail.smtp.starttls.enable"));
        properties.setProperty("mail.debug", environment.getProperty("mail.debug"));
        properties.setProperty("mail.smtp.timeout", environment.getProperty("mail.smtp.timeout"));
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() throws UnsupportedEncodingException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(environment.getProperty("mail.from"));
        simpleMailMessage.setSubject(environment.getProperty("mail.default.subject"));
        simpleMailMessage.setText(environment.getProperty("mail.default.text"));
        return simpleMailMessage;
    }

    @Bean
    public MimeMessage mimeMessage(JavaMailSender javaMailSender) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.setSubject(environment.getProperty("mail.default.subject"));
            mimeMessage.setText(environment.getProperty("mail.default.text"));
            mimeMessage.setFrom(new InternetAddress(environment.getProperty("mail.from")));
        } catch (MessagingException e) {
        }
        return mimeMessage;
    }

    @Bean
    public SimpleDateFormat simpleDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return sdf;
    }

    @Bean
    public DateFormat dateFormat() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return df;
    }
}

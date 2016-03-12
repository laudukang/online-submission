package me.laudukang.persistence.service;

import me.laudukang.spring.config.ApplicationConfig;
import me.laudukang.spring.config.AsyncConfig;
import me.laudukang.spring.config.PersistenceJPAConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/12
 * <p>Time: 14:45
 * <p>Version: 1.0
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, PersistenceJPAConfig.class, AsyncConfig.class}, loader = AnnotationConfigContextLoader.class)
@Rollback(false)
public class CustomEmailServiceTest {
    @Autowired
    private ICustomEmailService customEmailService;

    @Test
    public void sendMail() {
        customEmailService.send("751611201@qq.com", "subject 751611201", "hello laudukang");
    }
}

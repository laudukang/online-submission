package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsLog;
import me.laudukang.spring.config.PersistenceJPAConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/31
 * Time: 16:50
 * Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceJPAConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
public class LogServiceTest {
    @Autowired
    private ILogService logService;

    @Test
    @Rollback(false)
    public void saveWithJPA() {
        OsLog osLog = new OsLog();
        osLog.setTime(new Timestamp(new Date().getTime()));
        osLog.setContent("content_" + System.currentTimeMillis());
        logService.save(osLog);
    }

    @Test
    @Rollback(false)
    public void saveWithEM() {
        OsLog osLog = new OsLog();
        osLog.setTime(new Timestamp(new Date().getTime()));
        osLog.setContent("content_" + System.currentTimeMillis());
        logService.saveEM(osLog);
    }

    @Test
    public void findByContentEquals() {
        System.out.println(logService.findByContent("content_20160131174836"));
    }

    @Test
    public void findAll() {
        System.out.println(logService.findAll());
    }

    @Test
    public void findByUserOrAdminName() {
        System.out.println("in findByUserOrAdminName");
        System.out.println(logService.findByUserOrAdminName("lau"));
    }
}

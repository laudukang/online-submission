package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsLog;
import me.laudukang.spring.config.AsyncConfig;
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
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/31
 * <p>Time: 16:50
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceJPAConfig.class, AsyncConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class LogServiceTest {
    @Autowired
    private ILogService logService;

    @Test
    //@Rollback(false)
    public void saveWithJPA() {
        OsLog osLog = new OsLog();
        // osLog.setTime(new Timestamp(new Date().getTime()));
        osLog.setContent("content_" + System.currentTimeMillis());
        logService.save(osLog);
    }

    @Test
    //@Rollback(false)
    public void saveWithEM() {
        OsLog osLog = new OsLog();
        //osLog.setTime(new Timestamp(new Date().getTime()));
        osLog.setContent("content_" + System.currentTimeMillis());
        logService.saveWithEM(osLog);
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
        System.out.println(logService.findByUserOrAdminName("au"));
    }

    @Test
    public void updateTimeById() {
        System.out.println(logService.updateTimeById(1, new Timestamp(new Date().getTime())));
        System.out.println(logService.updateTimeById(21, new Timestamp(new Date().getTime())));
    }

    @Test
    public void deleteById() {
        //logService.deleteById(1);
        try {
            logService.deleteById(32);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void deleteByEntity() {
        OsLog osLog = new OsLog();
        osLog.setId(33);
        try {
            logService.deleteByEntity(osLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

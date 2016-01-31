package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsLog;
import me.laudukang.persistence.service.impl.LogService;
import me.laudukang.spring.config.PersistenceJPAConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

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
public class LogServiceTest {
    @Autowired
    private LogService logService;

    @Test
    public void save() {
        OsLog osLog = new OsLog();
        osLog.setTime(new Timestamp(new Date().getTime()));
        osLog.setContent("content_" + System.currentTimeMillis());
        logService.save(osLog);
    }
}

package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.model.OsMessage;
import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.util.PrintUtil;
import me.laudukang.spring.config.AsyncConfig;
import me.laudukang.spring.config.PersistenceJPAConfig;
import me.laudukang.spring.domain.OsMessageDomain;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/24
 * <p>Time: 21:36
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceJPAConfig.class, AsyncConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class MessageServiceTest {
    @Autowired
    private IMessageService messageService;
    private PrintUtil printUtil;
    private Pageable pageable;
    private SimpleDateFormat sdf;

    @Before
    public void initTest() {
        this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.printUtil = new PrintUtil();
        this.pageable = new PageRequest(0,
                10, new Sort(Sort.Direction.ASC, "id"));
    }

    @After
    public void endTest() {
        System.out.println("waiting async...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void save() {
        OsMessage osMessage = new OsMessage();
        OsAdmin osAdmin = new OsAdmin();
        osAdmin.setId(4);

        OsUser osUser = new OsUser();
        osUser.setId(4);

        osMessage.setOsAdmin(osAdmin);
        osMessage.setOsUser(osUser);
        osMessage.setContent("message_" + sdf.format(new Date()));
        messageService.save(osMessage);
    }

    @Test
    public void deleteById() {
        messageService.deleteById(2);
    }

    @Test
    public void findAllByUserId() {
        List<OsMessageDomain> result = messageService.findAllByUserId(4);
        printUtil.printListToConsole(result);
    }

    @Test
    public void findAllByAdminId() {
        List<OsMessageDomain> result = messageService.findAllByAdminId(1);
        printUtil.printListToConsole(result);
    }
}

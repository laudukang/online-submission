package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.util.PrintUtil;
import me.laudukang.spring.config.AsyncConfig;
import me.laudukang.spring.config.PersistenceJPAConfig;
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
 * <p>Date: 2016/2/1
 * <p>Time: 12:11
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceJPAConfig.class, AsyncConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class AdminServiceTest {
    @Autowired
    private IAdminService adminService;
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
        OsAdmin osAdmin = new OsAdmin();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        osAdmin.setAccount("lau_" + sdf.format(new Date()));
        adminService.save(osAdmin);
    }

    @Test
    public void updatePassword() {
        adminService.updatePassword(18, "666");
        adminService.updatePassword(1, "666");
    }

    @Test
    public void updateById() {
        OsAdmin osAdmin = new OsAdmin();
        osAdmin.setId(2);
        osAdmin.setRemark("remark_" + System.currentTimeMillis());
        osAdmin.setBirth(new java.sql.Date(new Date().getTime()));
        adminService.updateById(osAdmin);
    }

    @Test
    public void deleteById() {
        adminService.deleteById(16);
    }

    @Test
    public void findAll() {
        printUtil.printPageToConsole(adminService.findAll(pageable));
    }

    @Test
    public void findOne() {
        List<Object[]> result = adminService.findOne("lau_2016-02-10 20:03:19", "123");
        if (result.size() != 0) {
            System.out.println("id=" + result.get(0)[0] + " account=" + result.get(0)[1] + " name=" + result.get(0)[2]);
        }
    }

    @Test
    public void login() {
        Object[] result = adminService.login("lau_2016-02-10 20:03:19", "1234");
        System.out.println(result.length);
    }
}

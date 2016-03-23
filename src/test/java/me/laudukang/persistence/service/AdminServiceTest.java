package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.util.PrintUtil;
import me.laudukang.spring.config.ApplicationConfig;
import me.laudukang.spring.domain.AdminDomain;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/1
 * <p>Time: 12:11
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class AdminServiceTest {
    @Autowired
    private IAdminService iAdminService;
    private PrintUtil printUtil;
    private Pageable pageable;
    private SimpleDateFormat sdf;

    @Before
    public void initTest() {
        this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.printUtil = new PrintUtil();
        this.pageable = new PageRequest(0,
                1, new Sort(Sort.Direction.ASC, "id"));
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
        iAdminService.save(osAdmin);
    }

    @Test
    public void updatePassword() {
        iAdminService.updatePassword(18, "666");
        iAdminService.updatePassword(1, "666");
    }

    @Test
    public void updateById() {
        AdminDomain adminDomain = new AdminDomain();
        adminDomain.setId(2);
        adminDomain.setRemark("remark_" + System.currentTimeMillis());
        adminDomain.setBirth(new java.sql.Date(new Date().getTime()));
        iAdminService.updateById(adminDomain);
    }

    @Test
    public void deleteById() {
        iAdminService.deleteById(16);
    }

    @Test
    public void findAllByPage() {
        //Page<OsAdmin> osAdminPage = iAdminService.findAll("%lau%", "%lau%", pageable);
        AdminDomain adminDomain = new AdminDomain();
        adminDomain.setAccount("%lau%");
        adminDomain.setName("%lau%");
        adminDomain.setPage(0);
        adminDomain.setPageSize(2);
        adminDomain.setSortCol("id");
        adminDomain.setSortDir("ASC");
        Page<OsAdmin> osAdminPage = iAdminService.findAll(adminDomain);
        int size = osAdminPage.getContent().size();
        System.out.println(size);
        if (size > 0)
            System.out.println(osAdminPage.getContent().get(0).getAccount());

        printUtil.printPageToConsole(osAdminPage);
    }


    @Test
    public void login() {
        OsAdmin result = iAdminService.login("lau_2016-02-10 20:03:19", "1234");
        System.out.println(result.getAccount());
    }

    @Test
    public void ableAdmin() {
        System.out.println(iAdminService.ableAdmin(1, 0));
    }

    @Test
    public void findAllReviewer() {
        AdminDomain adminDomain = new AdminDomain();
        adminDomain.setAccount("%lau%");
        adminDomain.setName("%lau%");
        adminDomain.setPage(0);
        adminDomain.setPageSize(10);
        adminDomain.setSortCol("id");
        adminDomain.setSortDir("ASC");
        Page<OsAdmin> osAdminPage = iAdminService.findAllReviewer(adminDomain);
        int size = osAdminPage.getContent().size();
        System.out.println(size);
    }
}

package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.util.PrintUtil;
import me.laudukang.spring.config.ApplicationConfig;
import me.laudukang.spring.domain.UserDomain;
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
 * <p>Date: 2016/2/20
 * <p>Time: 22:22
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class UserServiceTest {
    @Autowired
    private IUserService iUserService;
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
        OsUser osUser = new OsUser();
        osUser.setAccount("account_" + sdf.format(new Date()));
        osUser.setPassword("123");
        iUserService.save(osUser);
    }

    @Test
    public void updateById() {
        UserDomain userDomain = new UserDomain();
        userDomain.setId(4);
        userDomain.setName("update_" + sdf.format(new Date()));
        userDomain.setPassword("123");
        iUserService.updateById(userDomain);
    }

    @Test
    public void deleteById() {
        iUserService.deleteById(3);
    }

    @Test
    public void findOne() {
        OsUser osUser = iUserService.findOne(1);
        try {
            com.google.common.base.Preconditions.checkNotNull(osUser, "Warning:user is null");
            System.out.println("user.name=" + osUser.getName());
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void login() {
        OsUser result = iUserService.login("lau", "123");
        System.out.println(result.getAccount());
    }

    @Test
    public void findAll() {
        UserDomain userDomain = new UserDomain();
        userDomain.setAccount("lau");
        userDomain.setName("lau");
        userDomain.setSortCol("id");
        userDomain.setSortDir("ASC");
        userDomain.setPage(1);
        userDomain.setPageSize(10);
        Page<OsUser> page = iUserService.findAll(userDomain);
        System.out.println(page.getContent().size());
    }
}

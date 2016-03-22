package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsDocAdmin;
import me.laudukang.persistence.model.OsDocAdminPK;
import me.laudukang.persistence.util.PrintUtil;
import me.laudukang.spring.config.ApplicationConfig;
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

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/25
 * <p>Time: 19:15
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class DocAdminServiceTest {
    @Autowired
    private IDocAdminService iDocAdminService;
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
        OsDocAdmin osDocAdmin = new OsDocAdmin();
        OsDocAdminPK osDocAdminPK = new OsDocAdminPK(1, 1);
        osDocAdmin.setId(osDocAdminPK);
        osDocAdmin.setReviewResult("r_" + sdf.format(new Date()));
        osDocAdmin.setPropose("拟采用");
        iDocAdminService.save(osDocAdmin);
    }

    @Test
    public void deleteById() {
        iDocAdminService.deleteById(new OsDocAdminPK(1, 1));
    }

    @Test
    public void update() {
        OsDocAdmin osDocAdmin = new OsDocAdmin();
        OsDocAdminPK osDocAdminPK = new OsDocAdminPK(1, 1);
        osDocAdmin.setId(osDocAdminPK);
        osDocAdmin.setReviewResult("update_" + sdf.format(new Date()));
        iDocAdminService.update(osDocAdmin);
    }

    @Test
    public void findOneByDocId() {
        System.out.println(iDocAdminService.findOneByDocId(1));
    }

    @Test
    public void findOneByDocAdmin() {
        OsDocAdmin osDocAdmin = iDocAdminService.findOneByDocAdmin(1, 1);
        System.out.println(osDocAdmin.getReviewResult());
    }
}

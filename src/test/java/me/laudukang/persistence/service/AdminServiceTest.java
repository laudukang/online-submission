package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.spring.config.PersistenceJPAConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/2/1
 * Time: 12:11
 * Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceJPAConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
public class AdminServiceTest {
    @Autowired
    private IAdminService adminService;

    @Test
    @Rollback(false)
    public void saveAdmin() {
        OsAdmin osAdmin = new OsAdmin();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        osAdmin.setAccount("lau_" + sdf.format(new Date()));
        adminService.saveAdmin(osAdmin);
    }

}

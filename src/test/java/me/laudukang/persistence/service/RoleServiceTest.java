package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsPermission;
import me.laudukang.persistence.model.OsRole;
import me.laudukang.persistence.util.PrintUtil;
import me.laudukang.spring.config.ApplicationConfig;
import me.laudukang.spring.domain.RoleDomain;
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
 * <p>Date: 2016/2/18
 * <p>Time: 20:34
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class RoleServiceTest {
    @Autowired
    private IRoleService iRoleService;
    private PrintUtil printUtil;
    private Pageable pageable;
    private SimpleDateFormat sdf;

    @Before
    public void initTest() {
        this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.printUtil = new PrintUtil();
        this.pageable = new PageRequest(0,
                10, new Sort(Sort.Direction.DESC, "id"));
    }

    @After
    public void endTest() {
        System.out.println("waiting async...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void save() {
        OsRole osRole = new OsRole();
        osRole.setName("SAVE");
        int[] osPermissions = {1, 2};
        iRoleService.save(osRole, osPermissions);
    }

    @Test
    public void deleteById() {
        iRoleService.deleteById(5);
    }

    @Test
    public void update() {
        RoleDomain roleDomain = new RoleDomain();
        roleDomain.setId(1);
        roleDomain.setName("UPDATE_" + sdf.format(new Date()));
        int[] osPermissions = {2, 3};
        iRoleService.updateById(roleDomain, osPermissions);
    }

    @Test
    public void findRoleByAdminId() {
        List<OsRole> osRoleList = iRoleService.findRoleByAdminId(1);
        if (null != osRoleList)
            System.out.println(osRoleList.size());
    }

    @Test
    public void findUnAssignPermission() {
        List<OsPermission> osPermissionList = iRoleService.findUnAssignPermission();
        System.out.println(osPermissionList.size());

    }

}

package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.repository.AdminRepository;
import me.laudukang.persistence.service.IAdminService;
import me.laudukang.spring.events.LogEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/1
 * <p>Time: 12:09
 * <p>Version: 1.0
 */
@Service
@Transactional
public class AdminService implements IAdminService, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    AdminRepository adminRepository;


    @Override
    public int updateById(OsAdmin osAdmin) {
        return 0;
    }

    @Override
    public void saveAdmin(OsAdmin osAdmin) {
        System.out.println("in AdminService saveAdmin");
        //adminRepository.saveAdmin(osAdmin);
        adminRepository.save(osAdmin);
        applicationContext.publishEvent(new LogEvent(this, "content_" + System.currentTimeMillis(), "lau", "localhost"));
        System.out.println("in AdminService saveAdmin done");
    }
}

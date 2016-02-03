package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.repository.AdminRepository;
import me.laudukang.persistence.service.IAdminService;
import me.laudukang.spring.events.CustomSpringEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdminService implements IAdminService {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    CustomSpringEventPublisher customSpringEventPublisher;

    @Override
    public int updateById(OsAdmin osAdmin) {
        return 0;
    }

    @Override
    public void saveAdmin(OsAdmin osAdmin) {
        System.out.println("in AdminService saveAdmin");
        adminRepository.saveAdmin(osAdmin);
        customSpringEventPublisher.publishLogEvent("content_" + System.currentTimeMillis(), "lau", "localhost");
    }
}

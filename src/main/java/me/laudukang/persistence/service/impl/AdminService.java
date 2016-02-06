package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.repository.AdminRepository;
import me.laudukang.persistence.service.IAdminService;
import me.laudukang.spring.events.LogEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void updateById(OsAdmin osAdminToSave) {
        OsAdmin osAdmin = adminRepository.findOne(osAdminToSave.getId());
        if (null != osAdmin) {
            osAdmin.setBirth(osAdminToSave.getBirth());
            osAdmin.setAddress(osAdminToSave.getAddress());
            osAdmin.setName(osAdminToSave.getName());
            osAdmin.setMobilePhone(osAdminToSave.getMobilePhone());
            osAdmin.setOfficePhone(osAdminToSave.getOfficePhone());
            osAdmin.setRemark(osAdminToSave.getRemark());
            osAdmin.setSex(osAdminToSave.getSex());
            osAdmin.setSubject(osAdminToSave.getSubject());
            adminRepository.save(osAdmin);
        }
    }


    @Override
    public void updatePassword(int id, String password) {
        OsAdmin osAdmin = adminRepository.findOne(id);
        if (null != osAdmin) {
            osAdmin.setPassword(password);
            adminRepository.save(osAdmin);
        }
    }

    @Override
    public Page<OsAdmin> findAll(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    @Override
    public void save(OsAdmin osAdmin) {
        //adminRepository.saveAdminWithEM(osAdmin);
        adminRepository.save(osAdmin);
        applicationContext.publishEvent(new LogEvent(this, "content_" + System.currentTimeMillis(), "lau", "localhost"));
    }

    @Override
    public void deleteById(int id) {
        adminRepository.delete(id);
    }
}

package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.repository.AdminRepository;
import me.laudukang.persistence.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/2/1
 * Time: 12:09
 * Version: 1.0
 */
@Service
@Transactional
public class AdminService implements IAdminService {
    @Autowired
    AdminRepository adminRepository;

    @Override
    public void saveAdmin(OsAdmin osAdmin) {
        System.out.println("in AdminService saveAdmin");
        adminRepository.saveAdmin(osAdmin);
    }
}

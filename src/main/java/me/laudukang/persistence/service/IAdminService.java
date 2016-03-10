package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.spring.domain.AdminDomain;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/1
 * <p>Time: 12:09
 * <p>Version: 1.0
 */
public interface IAdminService extends ApplicationContextAware {

    void save(OsAdmin osAdmin);

    void deleteById(int id);

    void updateById(OsAdmin osAdminToSave);

    int updatePassword(int id, String password);

    Page<OsAdmin> findAll(AdminDomain adminDomain);

    Page<OsAdmin> findAllReviewer(AdminDomain adminDomain);

    boolean existAccount(String account);

    List<Object[]> findOne(String account, String password);

    Object[] login(String account, String password);

    OsAdmin findOne(int id);

    int ableAdmin(int id, int status);
}

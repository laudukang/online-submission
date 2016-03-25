package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.model.OsRole;
import me.laudukang.spring.domain.AdminDomain;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/1
 * <p>Time: 12:09
 * <p>Version: 1.0
 */
public interface IAdminService {

    void save(OsAdmin osAdmin);

    void deleteById(int id);

    void updateById(AdminDomain adminDomain);

    int updatePassword(int id, String password);

    Page<OsAdmin> findAll(AdminDomain adminDomain);

    Page<OsAdmin> findAllReviewer(AdminDomain adminDomain);

    List<OsAdmin> listReviewer();

    boolean existAccount(String account);

    //List<Object[]> findOne(String account, String password);

    OsAdmin login(String account, String password);

    OsAdmin findOne(int id);

    int ableAdmin(int id, int status);

    OsRole findReviewerRole();
}

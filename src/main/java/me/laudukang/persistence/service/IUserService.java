package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsUser;
import me.laudukang.spring.domain.UserDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/20
 * <p>Time: 12:07
 * <p>Version: 1.0
 */
public interface IUserService {

    void save(OsUser osUser);

    void updateById(OsUser osUser);

    void deleteById(int id);

    OsUser findOne(int id);

    OsUser findByAccount(String account);

    OsUser login(String account, String password);

    boolean existAccount(String account);

    int updatePassword(int id, String password);

    Page<OsUser> findAll(Pageable pageable);

    Page<OsUser> findAll(UserDomain userDomain);
}

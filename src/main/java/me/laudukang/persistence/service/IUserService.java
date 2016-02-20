package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsUser;
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

    Page<OsUser> findAll(Pageable pageable);
}

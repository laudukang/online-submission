package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsAdmin;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/1
 * <p>Time: 12:09
 * <p>Version: 1.0
 */
public interface IAdminService {
    void saveAdmin(OsAdmin osAdmin);

    int updateById(OsAdmin osAdmin);
}

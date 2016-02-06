package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsAdmin;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/31
 * <p>Time: 15:37
 * <p>Version: 1.0
 */
public interface AdminRepositoryCustom {
    void saveAdminWithEM(OsAdmin osAdmin);
}

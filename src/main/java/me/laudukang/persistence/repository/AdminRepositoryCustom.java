package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsAdmin;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/31
 * Time: 15:37
 * Version: 1.0
 */
public interface AdminRepositoryCustom {
    OsAdmin findByAccount(String account);
}

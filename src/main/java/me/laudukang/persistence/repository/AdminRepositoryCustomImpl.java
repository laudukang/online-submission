package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsAdmin;
import org.springframework.stereotype.Repository;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/31
 * Time: 15:38
 * Version: 1.0
 */
@Repository
public class AdminRepositoryCustomImpl implements AdminRepositoryCustom {
    @Override
    public OsAdmin findByAccount(String account) {
        System.out.println("in AdminRepositoryCustomImpl");

        return null;
    }
}

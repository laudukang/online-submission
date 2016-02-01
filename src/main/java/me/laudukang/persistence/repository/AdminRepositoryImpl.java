package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsAdmin;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/31
 * Time: 15:38
 * Version: 1.0
 */
@Repository
public class AdminRepositoryImpl implements AdminRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveAdmin(OsAdmin osAdmin) {
        System.out.println("in AdminRepositoryCustomImpl saveAccount");
        entityManager.merge(osAdmin);
        entityManager.flush();
    }
}

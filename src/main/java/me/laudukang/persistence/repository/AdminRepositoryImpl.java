package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsAdmin;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/31
 * <p>Time: 15:38
 * <p>Version: 1.0
 */
@Repository
public class AdminRepositoryImpl implements AdminRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveAdminWithEM(OsAdmin osAdmin) {
        entityManager.merge(osAdmin);
        entityManager.flush();
    }
}

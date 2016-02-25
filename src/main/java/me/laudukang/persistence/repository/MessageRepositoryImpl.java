package me.laudukang.persistence.repository;

import me.laudukang.spring.domain.OsMessageDomain;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/25
 * <p>Time: 15:51
 * <p>Version: 1.0
 */
@Repository
public class MessageRepositoryImpl implements MessageRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OsMessageDomain> findAllByUserId(int id) {
        String queryStr = "select new me.laudukang.spring.domain.OsMessageDomain(message.id,message.title,message.content,message.postTime,message.osUser.account,message.osAdmin.account) from OsMessage message where message.osUser.id=:id order by message.postTime";
        TypedQuery<OsMessageDomain> query = entityManager.createQuery(queryStr, OsMessageDomain.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<OsMessageDomain> findAllByAdminId(int id) {
        String queryStr = "select new me.laudukang.spring.domain.OsMessageDomain(message.id,message.title,message.content,message.postTime,message.osUser.account,message.osAdmin.account) from OsMessage message where message.osAdmin.id=:id order by message.postTime";
        TypedQuery<OsMessageDomain> query = entityManager.createQuery(queryStr, OsMessageDomain.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}

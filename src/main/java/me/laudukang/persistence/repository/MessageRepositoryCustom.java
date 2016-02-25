package me.laudukang.persistence.repository;

import me.laudukang.spring.domain.OsMessageDomain;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/25
 * <p>Time: 15:51
 * <p>Version: 1.0
 */
public interface MessageRepositoryCustom {
    List<OsMessageDomain> findAllByUserId(int id);

    List<OsMessageDomain> findAllByAdminId(int id);
}

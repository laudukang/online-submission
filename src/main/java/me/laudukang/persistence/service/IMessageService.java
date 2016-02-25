package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsMessage;
import me.laudukang.spring.domain.OsMessageDomain;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/24
 * <p>Time: 21:15
 * <p>Version: 1.0
 */
public interface IMessageService {

    void save(OsMessage osMessage);

    void deleteById(int id);

    List<OsMessageDomain> findAllByUserId(int id);

    List<OsMessageDomain> findAllByAdminId(int id);
}

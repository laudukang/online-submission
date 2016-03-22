package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsMessage;
import me.laudukang.spring.domain.MessageDomain;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/24
 * <p>Time: 21:15
 * <p>Version: 1.0
 */
public interface IMessageService {

    OsMessage save(OsMessage osMessage);

    void deleteById(int id);

    List<MessageDomain> findAllByUserId(int id, MessageDomain messageDomain);

    List<MessageDomain> findAllByAdminId(int id, MessageDomain messageDomain);
}

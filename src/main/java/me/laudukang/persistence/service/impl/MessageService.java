package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsMessage;
import me.laudukang.persistence.repository.MessageRepository;
import me.laudukang.persistence.service.IMessageService;
import me.laudukang.spring.domain.MessageDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/24
 * <p>Time: 21:15
 * <p>Version: 1.0
 */
@Service
public class MessageService implements IMessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Async
    @Override
    public OsMessage save(OsMessage osMessage) {
        return messageRepository.save(osMessage);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict
    @Override
    public void deleteById(int id) {
        messageRepository.delete(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable("message-cache")
    @Override
    public List<MessageDomain> findAllByUserId(int id) {
        return messageRepository.findAllByUserId(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable("message-cache")
    @Override
    public List<MessageDomain> findAllByAdminId(int id) {
        return messageRepository.findAllByAdminId(id);
    }
}

package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsLog;
import me.laudukang.persistence.repository.LogRepository;
import me.laudukang.persistence.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/31
 * <p>Time: 16:13
 * <p>Version: 1.0
 */
@Service
@Transactional
public class LogService implements ILogService {

    @Autowired
    private LogRepository logRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(OsLog osLog) {
        System.out.println("in LogService save");

        logRepository.save(osLog);
    }

    public void saveWithEM(OsLog osLog) {
        System.out.println("in LogService saveEM");
        entityManager.merge(osLog);
        //entityManager.flush();
    }

    public List<OsLog> findByContent(String content) {
        System.out.println("in LogService findByContent");
        return logRepository.findByContentLike(content);
    }

    @Override
    public List<OsLog> findAll() {
        return logRepository.findAll();
    }

    @Override
    public List<OsLog> findByUserOrAdminName(String userOrAdminName) {
        return logRepository.findByUserOrAdminNameLike(userOrAdminName);

    }

    @Override
    public int updateTimeById(int id, Timestamp timestamp) {
        return logRepository.updateTimeById(id, timestamp);
    }

    @Override
    public void deleteById(int id) throws Exception {
        logRepository.delete(id);
    }

    @Override
    public void deleteByEntity(OsLog osLog) throws Exception {
        logRepository.delete(osLog);
    }
}

package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsLog;
import me.laudukang.persistence.repository.LogRepository;
import me.laudukang.persistence.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.Future;

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

    //@PersistenceContext
    //private EntityManager entityManager;
    //public void saveWithEM(OsLog osLog) {
    //    entityManager.merge(osLog);
    //    entityManager.flush();
    //}

    @Override
    @Async
    public void save(OsLog osLog) {
        logRepository.save(osLog);
    }

    public Page<OsLog> findByContent(String content, Pageable pageable) {
        return logRepository.findByContentLike(content, pageable);
    }

    @Override
    public Page<OsLog> findAll(Pageable pageable) {
        return logRepository.findAll(pageable);
    }

    @Override
    public Page<OsLog> findByUserOrAdminName(String userOrAdminName, Pageable pageable) {
        return logRepository.findByUserOrAdminNameLike(userOrAdminName, pageable);

    }

    @Async
    @Override
    public void deleteById(int id) throws Exception {
        logRepository.delete(id);
    }

    @Async
    @Override
    public void deleteByEntity(OsLog osLog) throws Exception {
        logRepository.delete(osLog);
    }

    @Override
    @Async
    public void asyncMethodWithVoidReturnType() {
        System.out.println("in asyncMethodWithVoidReturnType");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Async
    public Future<String> asyncMethodWithReturnType() {
        System.out.println("in asyncMethodWithReturnType");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<String>("hello world");
    }
}

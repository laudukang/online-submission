package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.Future;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/31
 * <p>Time: 16:13
 * <p>Version: 1.0
 */
public interface ILogService {
    void save(OsLog log);

    //void saveWithEM(OsLog osLog);

    Page<OsLog> findByContent(String content, Pageable pageable);

    Page<OsLog> findAll(Pageable pageable);

    Page<OsLog> findByUserOrAdminName(String userOrAdminName, Pageable pageable);

    void deleteById(int id) throws Exception;

    void deleteByEntity(OsLog osLog) throws Exception;

    void asyncMethodWithVoidReturnType();

    Future<String> asyncMethodWithReturnType();
}

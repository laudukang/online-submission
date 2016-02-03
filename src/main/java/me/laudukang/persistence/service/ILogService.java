package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsLog;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/31
 * <p>Time: 16:13
 * <p>Version: 1.0
 */
public interface ILogService {
    void save(OsLog log);

    void saveWithEM(OsLog osLog);

    List<OsLog> findByContent(String content);

    List<OsLog> findAll();

    List<OsLog> findByUserOrAdminName(String userOrAdminName);

    int updateTimeById(int id, Timestamp timestamp);

    void deleteById(int id) throws Exception;

    void deleteByEntity(OsLog osLog) throws Exception;
}

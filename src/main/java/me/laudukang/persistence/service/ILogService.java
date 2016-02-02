package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsLog;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/31
 * Time: 16:13
 * Version: 1.0
 */
public interface ILogService {
    void save(OsLog log);

    void saveEM(OsLog osLog);

    List<OsLog> findByContent(String content);

    List<OsLog> findAll();

    List<OsLog> findByUserOrAdminName(String userOrAdminName);

    int updateTimeById(int id, Timestamp timestamp);

    void deleteById(int id) throws Exception;

    void deleteByEntity(OsLog osLog) throws Exception;
}

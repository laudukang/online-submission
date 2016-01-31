package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsLog;
import me.laudukang.persistence.repository.LogRepository;
import me.laudukang.persistence.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/31
 * Time: 16:13
 * Version: 1.0
 */
@Service
@Transactional
public class LogService implements ILogService {

    @Autowired
    LogRepository logRepository;

    @Override
    public void save(OsLog osLog) {
        System.out.println("in LogService save");
        logRepository.save(osLog);
    }
}

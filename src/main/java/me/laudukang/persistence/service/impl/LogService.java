package me.laudukang.persistence.service.impl;

import com.google.common.base.Strings;
import me.laudukang.persistence.model.OsLog;
import me.laudukang.persistence.repository.LogRepository;
import me.laudukang.persistence.service.ILogService;
import me.laudukang.spring.domain.LogDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/31
 * <p>Time: 16:13
 * <p>Version: 1.0
 */
@Service
public class LogService extends CustomPageService<OsLog> implements ILogService {

    @Autowired
    SimpleDateFormat simpleDateFormat;
    @Autowired
    DateFormat dateFormat;
    @Autowired
    private LogRepository logRepository;

    //@PersistenceContext
    //private EntityManager entityManager;
    //public void saveWithEM(OsLog osLog) {
    //    entityManager.merge(osLog);
    //    entityManager.flush();
    //}

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    @Async
    public void save(OsLog osLog) {
        logRepository.save(osLog);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable("log-cache")
    public Page<OsLog> findByContent(String content, Pageable pageable) {
        return logRepository.findByContentLike(content, pageable);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable("log-cache")
    @Override
    public Page<OsLog> findByUserOrAdminName(String userOrAdminName, Pageable pageable) {
        return logRepository.findByUserOrAdminNameLike(userOrAdminName, pageable);

    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable("log-cache")
    @Override
    public Page<OsLog> findAll(Pageable pageable) {
        return logRepository.findAll(pageable);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable("log-cache")
    @Override
    public Page<OsLog> findAll(LogDomain logDomain) {
        return logRepository.findAll(getSpecification(logDomain),
                getPageRequest(logDomain.getPage(), logDomain.getPageSize(), logDomain.getSortCol(), logDomain.getSortDir()));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict
    @Async
    @Override
    public void deleteById(int id) {
        logRepository.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict
    @Async
    @Override
    public void deleteByEntity(OsLog osLog) {
        logRepository.delete(osLog);
    }

    private Specification<OsLog> getSpecification(LogDomain logDomain) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (!Strings.isNullOrEmpty(logDomain.getContent()))
                predicate.getExpressions().add(cb.like(root.get("content").as(String.class), logDomain.getContent().trim()));
            if (!Strings.isNullOrEmpty(logDomain.getFromTime()) && !Strings.isNullOrEmpty(logDomain.getToTime())) {
                try {
                    predicate.getExpressions().add(cb.between(root.get("time"), new Date(dateFormat.parse(logDomain.getFromTime()).getTime()), new Date(dateFormat.parse(logDomain.getToTime()).getTime())));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return predicate;
        };
    }

//    @Override
//    @Async
//    public void asyncMethodWithVoidReturnType() {
//        System.out.println("in asyncMethodWithVoidReturnType");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    @Async
//    public Future<String> asyncMethodWithReturnType() {
//        System.out.println("in asyncMethodWithReturnType");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return new AsyncResult<String>("hello world");
//    }
}

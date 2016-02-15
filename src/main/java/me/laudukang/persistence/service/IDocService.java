package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/6
 * <p>Time: 18:21
 * <p>Version: 1.0
 */
public interface IDocService {
    void save(OsDoc osDoc);

    void updateById(OsDoc osDoc);

    void deleteById(int id);

    Page<OsDoc> findAll(Pageable pageable);
}

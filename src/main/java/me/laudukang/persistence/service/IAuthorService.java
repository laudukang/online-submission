package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsAuthor;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/6
 * <p>Time: 18:17
 * <p>Version: 1.0
 */
public interface IAuthorService {
    void save(OsAuthor osAuthor);

    void updateById(OsAuthor osAuthor);

    void deleteById(int id);

}

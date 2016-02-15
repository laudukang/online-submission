package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsAuthor;
import me.laudukang.persistence.service.IAuthorService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/6
 * <p>Time: 18:17
 * <p>Version: 1.0
 */
@Service
@Transactional
public class AuthorService implements IAuthorService {

    @Override
    public void save(OsAuthor osAuthor) {

    }

    @Override
    public void updateById(OsAuthor osAuthor) {

    }

    @Override
    public void deleteById(int id) {

    }
}

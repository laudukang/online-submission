package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsDoc;
import me.laudukang.persistence.repository.AuthorRepository;
import me.laudukang.persistence.repository.DocRepository;
import me.laudukang.persistence.service.IDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/6
 * <p>Time: 18:21
 * <p>Version: 1.0
 */
@Service
@Transactional
public class DocService implements IDocService {
    @Autowired
    private DocRepository docRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public void save(OsDoc osDoc) {

        //for (OsAuthor osAuthor : osDoc.getOsAuthors()
        //        ) {
        //    authorRepository.save(osAuthor);
        //}
        try {
            docRepository.save(osDoc);
        } catch (Exception ex) {
            System.out.println("in save Exception=" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void updateById(OsDoc osDoc) {
        docRepository.save(osDoc);

    }

    @Override
    public void deleteById(int id) {
        docRepository.delete(id);
    }

    @Override
    public Page<OsDoc> findAll(Pageable pageable) {
        return docRepository.findAll(pageable);
    }
}

package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsDoc;
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

    @Override
    public void save(OsDoc osDoc) {
        docRepository.save(osDoc);
    }

    @Override
    public void updateById(OsDoc osDoc) {
        OsDoc result = docRepository.findOne(osDoc.getId());
        if (null != result) {
            result.setZhTitle(osDoc.getZhTitle());
            result.setZhSummary(osDoc.getZhSummary());
            result.setZhKeyword(osDoc.getZhKeyword());
            result.setEnKeyword(osDoc.getEnKeyword());
            result.setEnSummary(osDoc.getEnSummary());
            result.setEnTitle(osDoc.getEnTitle());
            result.setSubject(osDoc.getSubject());
            result.setClassification(osDoc.getClassification());
            result.setType(osDoc.getType());

            result.setOsAuthors(osDoc.getOsAuthors());
            docRepository.save(osDoc);
        }

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

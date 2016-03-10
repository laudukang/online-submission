package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsDoc;
import me.laudukang.persistence.repository.DocRepository;
import me.laudukang.persistence.service.IDocService;
import me.laudukang.spring.domain.DocDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/6
 * <p>Time: 18:21
 * <p>Version: 1.0
 */
@Service
@Transactional
public class DocService extends CustomPageService<OsDoc> implements IDocService {
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
    public Page<OsDoc> findAll(String zhTitle, Date fromTime, Date toTime, Pageable pageable) {
        return docRepository.findAll(getSpecification(zhTitle, fromTime, toTime), pageable);
    }

    @Override
    public OsDoc findOne(int id) {
        return docRepository.findOne(id);
    }

    @Override
    public Page<OsDoc> findAll(DocDomain docDomain) {
        return docRepository.findAll(getSpecification(docDomain),
                getPageRequest(docDomain.getPage(), docDomain.getPageSize(), docDomain.getSortCol(), docDomain.getSortDir()));
    }

    @Override
    public Page<OsDoc> findAllByUserId(DocDomain docDomain) {
        return docRepository.findAll(getSpecification(docDomain.getId()),
                getPageRequest(docDomain.getPage(), docDomain.getPageSize(), docDomain.getSortCol(), docDomain.getSortDir()));
    }

    private Specification<OsDoc> getSpecification(final String zhTitle, final Date fromTime, final Date toTime) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate.getExpressions().add(cb.like(root.get("zhTitle"), zhTitle));
            predicate.getExpressions().add(cb.between(root.get("postTime"), fromTime, toTime));
            return predicate;
        };
    }

    private Specification<OsDoc> getSpecification(final DocDomain docDomain) {
        return (root, query, cb) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd HH:mm:ss
            Predicate predicate = cb.conjunction();
            predicate.getExpressions().add(cb.like(root.get("zhTitle"), docDomain.getZhTitle()));
            predicate.getExpressions().add(cb.like(root.get("classification"), docDomain.getClassification()));
            predicate.getExpressions().add(cb.like(root.get("subject"), docDomain.getSubject()));
            predicate.getExpressions().add(cb.like(root.get("zhKeyword"), docDomain.getZhKeyword()));
            predicate.getExpressions().add(cb.like(root.get("type"), docDomain.getType()));
            predicate.getExpressions().add(cb.like(root.get("status"), docDomain.getStatus()));
            try {
                predicate.getExpressions().add(cb.between(root.get("postTime"), sdf.parse(docDomain.getFromTime()), sdf.parse(docDomain.getFromTime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return predicate;
        };
    }

    private Specification<OsDoc> getSpecification(final int id) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate.getExpressions().add(cb.equal(root.get("osUser").get("id"), id));
            // TODO: 2016/3/10 根据user.id查找 
            return predicate;
        };
    }

}

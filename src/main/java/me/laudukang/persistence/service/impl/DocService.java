package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.*;
import me.laudukang.persistence.repository.AuthorRepository;
import me.laudukang.persistence.repository.DocRepository;
import me.laudukang.persistence.service.IDocService;
import me.laudukang.spring.domain.DocDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/6
 * <p>Time: 18:21
 * <p>Version: 1.0
 */
@Service("docService")
@Transactional
public class DocService extends CustomPageService<OsDoc> implements IDocService {

    @Autowired
    SimpleDateFormat simpleDateFormat;
    @Autowired
    DateFormat dateFormat;
    @Autowired
    private DocRepository docRepository;
    @Autowired
    private AuthorRepository authorRepository;


    @Override
    public void save(OsDoc osDoc) {
        docRepository.save(osDoc);
    }

    @Override
    public void update(OsDoc osDoc, List<OsAuthor> osAuthorList) {
        authorRepository.delete(osAuthorList);
        docRepository.save(osDoc);
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
    public Page<OsDoc> findAllSuper(DocDomain docDomain) {
        return docRepository.findAll(getSuperSpecification(docDomain),
                getPageRequest(docDomain.getPage(), docDomain.getPageSize(), docDomain.getSortCol(), docDomain.getSortDir()));
    }

    @Override
    public Page<OsDoc> findAllByUserId(DocDomain docDomain) {
        return docRepository.findAll(getUserSpecification(docDomain),
                getPageRequest(docDomain.getPage(), docDomain.getPageSize(), docDomain.getSortCol(), docDomain.getSortDir()));
    }

    @Override
    public Page<OsDoc> findByAdminId(DocDomain docDomain) {
        return docRepository.findAll(getAdminSpecification(docDomain),
                getPageRequest(docDomain.getPage(), docDomain.getPageSize(), docDomain.getSortCol(), docDomain.getSortDir()));
    }

    private Specification<OsDoc> getSpecification(final String zhTitle, final Date fromTime, final Date toTime) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate.getExpressions().add(cb.like(root.get("zhTitle").as(String.class), "%" + zhTitle.trim() + "%"));
            predicate.getExpressions().add(cb.between(root.get("postTime").as(Timestamp.class), fromTime, toTime));
            return predicate;
        };
    }

    private Specification<OsDoc> getAdminSpecification(final DocDomain docDomain) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (!isNullOrEmpty(docDomain.getZhTitle()))
                predicate.getExpressions().add(cb.like(root.get("zhTitle").as(String.class), "%" + docDomain.getZhTitle().trim() + "%"));
            if (!isNullOrEmpty(docDomain.getClassification()))
                predicate.getExpressions().add(cb.equal(root.get("classification").as(String.class), docDomain.getClassification()));
            //if (!isNullOrEmpty(docDomain.getSubject()))
            //    predicate.getExpressions().add(cb.like(root.get("subject").as(String.class), "%"+docDomain.getSubject()+"%"));
            if (!isNullOrEmpty(docDomain.getZhKeyword()))
                predicate.getExpressions().add(cb.like(root.get("zhKeyword").as(String.class), "%" + docDomain.getZhKeyword().trim() + "%"));
            if (!isNullOrEmpty(docDomain.getType()))
                predicate.getExpressions().add(cb.equal(root.get("type").as(String.class), docDomain.getType()));
            if (!isNullOrEmpty(docDomain.getStatus()))
                predicate.getExpressions().add(cb.equal(root.get("status").as(String.class), docDomain.getStatus()));
            if (!isNullOrEmpty(docDomain.getFromTime()) && !isNullOrEmpty(docDomain.getToTime())) {
                try {
                    predicate.getExpressions().add(cb.between(root.get("postTime"), new Date(dateFormat.parse(docDomain.getFromTime()).getTime()), new Date(dateFormat.parse(docDomain.getToTime()).getTime())));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (0 != docDomain.getAdminid()) {
                ListJoin<OsDoc, OsDocAdmin> osDocAdminListJoin = root.join(root.getModel().getList("osDocAdmins", OsDocAdmin.class), JoinType.INNER);
                //query.where(cb.equal(osDocAdminListJoin.<OsAdmin>get("osAdmin").get("id").as(String.class),"1"));
                predicate.getExpressions().add(cb.equal(osDocAdminListJoin.<OsAdmin>get("osAdmin").get("id").as(String.class), docDomain.getAdminid()));
            }
            return predicate;

        };
    }

    private Specification<OsDoc> getSuperSpecification(final DocDomain docDomain) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (!isNullOrEmpty(docDomain.getZhTitle()))
                predicate.getExpressions().add(cb.like(root.get("zhTitle").as(String.class), "%" + docDomain.getZhTitle().trim() + "%"));
            if (!isNullOrEmpty(docDomain.getClassification()))
                predicate.getExpressions().add(cb.equal(root.get("classification").as(String.class), docDomain.getClassification()));
            //if (!isNullOrEmpty(docDomain.getSubject()))
            //    predicate.getExpressions().add(cb.like(root.get("subject").as(String.class), "%"+docDomain.getSubject()+"%"));
            if (!isNullOrEmpty(docDomain.getZhKeyword()))
                predicate.getExpressions().add(cb.like(root.get("zhKeyword").as(String.class), "%" + docDomain.getZhKeyword().trim() + "%"));
            if (!isNullOrEmpty(docDomain.getType()))
                predicate.getExpressions().add(cb.equal(root.get("type").as(String.class), docDomain.getType()));
            if (!isNullOrEmpty(docDomain.getStatus()))
                predicate.getExpressions().add(cb.equal(root.get("status").as(String.class), docDomain.getStatus()));
            if (!isNullOrEmpty(docDomain.getFromTime()) && !isNullOrEmpty(docDomain.getToTime())) {
                try {
                    predicate.getExpressions().add(cb.between(root.get("postTime"), new Date(dateFormat.parse(docDomain.getFromTime()).getTime()), new Date(dateFormat.parse(docDomain.getToTime()).getTime())));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return predicate;
        };
    }

    private Specification<OsDoc> getUserSpecification(final DocDomain docDomain) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (0 != docDomain.getUserid())
                predicate.getExpressions().add(cb.equal(root.<OsUser>get("osUser").get("id").as(Integer.class), docDomain.getUserid()));
            if (!isNullOrEmpty(docDomain.getZhTitle()))
                predicate.getExpressions().add(cb.like(root.get("zhTitle").as(String.class), "%" + docDomain.getZhTitle().trim() + "%"));
            if (!isNullOrEmpty(docDomain.getClassification()))
                predicate.getExpressions().add(cb.equal(root.get("classification").as(String.class), docDomain.getClassification()));
            //if (!isNullOrEmpty(docDomain.getSubject()))
            //    predicate.getExpressions().add(cb.like(root.get("subject").as(String.class), "%"+docDomain.getSubject()+"%"));
            if (!isNullOrEmpty(docDomain.getZhKeyword()))
                predicate.getExpressions().add(cb.like(root.get("zhKeyword").as(String.class), "%" + docDomain.getZhKeyword() + "%"));
            if (!isNullOrEmpty(docDomain.getType()))
                predicate.getExpressions().add(cb.equal(root.get("type").as(String.class), docDomain.getType()));
            if (!isNullOrEmpty(docDomain.getStatus()))
                predicate.getExpressions().add(cb.equal(root.get("status").as(String.class), docDomain.getStatus()));
            if (!isNullOrEmpty(docDomain.getFromTime()) && !isNullOrEmpty(docDomain.getToTime())) {
                try {
                    predicate.getExpressions().add(cb.between(root.get("postTime"), new Date(dateFormat.parse(docDomain.getFromTime()).getTime()), new Date(dateFormat.parse(docDomain.getToTime()).getTime())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return predicate;
        };
    }

}

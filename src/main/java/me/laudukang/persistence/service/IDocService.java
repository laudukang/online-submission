package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsAuthor;
import me.laudukang.persistence.model.OsDoc;
import me.laudukang.spring.domain.DocDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/6
 * <p>Time: 18:21
 * <p>Version: 1.0
 */
public interface IDocService {
    void save(OsDoc osDoc);

    OsDoc findOne(int id);

    void update(OsDoc osDoc, List<OsAuthor> osAuthorList);

    void deleteById(int id);

    Page<OsDoc> findAll(String zhTitle, Date fromTime, Date toTime, Pageable pageable);

    Page<OsDoc> findAllSuper(DocDomain docDomain);

    Page<OsDoc> findAllByUserId(DocDomain docDomain);

    Page<OsDoc> findByAdminId(DocDomain docDomain);

    Page<OsDoc> findDistributedDoc(DocDomain docDomain);
}

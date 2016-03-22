package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsDocAdmin;
import me.laudukang.persistence.model.OsDocAdminPK;
import me.laudukang.persistence.repository.DocAdminRepository;
import me.laudukang.persistence.service.IDocAdminService;
import me.laudukang.spring.domain.DocAdminDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/25
 * <p>Time: 18:13
 * <p>Version: 1.0
 */
@Service
@Transactional
public class DocAdminService implements IDocAdminService {
    @Autowired
    private DocAdminRepository docAdminRepository;

    @Override
    public void save(OsDocAdmin osDocAdmin) {
        docAdminRepository.save(osDocAdmin);
    }

    @Override
    public void deleteById(OsDocAdminPK osDocAdminPK) {
        docAdminRepository.delete(osDocAdminPK);
    }

    @Override
    public void update(OsDocAdmin osDocAdmin) {
        OsDocAdmin result = docAdminRepository.findOne(osDocAdmin.getId());
        if (null != result) {
            result.setReviewResult(osDocAdmin.getReviewResult());
            result.setReviewTime(osDocAdmin.getReviewTime());
            docAdminRepository.save(result);
        }
    }

    @Override
    public DocAdminDomain findOneByDocId(int id) {
        return docAdminRepository.findOneByDocId(id);
    }

    @Override
    public OsDocAdmin findOneByDocAdmin(int docid, int adminid) {
        return docAdminRepository.findOneByDocAdmin(docid, adminid);
    }

    //private Specification<OsDocAdmin> getSpecification(final String account, final String name) {
    //    return (root, query, cb) -> {
    //        Predicate predicate = cb.conjunction();
    //        predicate.getExpressions().add(cb.like(root.get("account"), account));
    //        predicate.getExpressions().add(cb.like(root.get("name"), name));
    //        return predicate;
    //    };
    //}

}

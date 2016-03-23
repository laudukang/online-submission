package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.repository.AdminRepository;
import me.laudukang.persistence.service.IAdminService;
import me.laudukang.spring.domain.AdminDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/1
 * <p>Time: 12:09
 * <p>Version: 1.0
 */
@Service("adminService")
@Transactional
public class AdminService extends CustomPageService<OsAdmin> implements IAdminService {
    @Autowired
    private AdminRepository adminRepository;


    @Override
    public void updateById(AdminDomain adminDomain) {
        OsAdmin osAdmin = adminRepository.findOne(adminDomain.getId());
        if (null != osAdmin) {
            osAdmin.setBirth(adminDomain.getBirth());
            osAdmin.setAddress(adminDomain.getAddress());
            osAdmin.setName(adminDomain.getName());
            osAdmin.setMobilePhone(adminDomain.getMobilePhone());
            osAdmin.setOfficePhone(adminDomain.getOfficePhone());
            osAdmin.setRemark(adminDomain.getRemark());
            osAdmin.setSex(adminDomain.getSex());
            osAdmin.setSubject(adminDomain.getSubject());
//            if (!Strings.isNullOrEmpty(adminDomain.getPassword())) {
//                osAdmin.setPassword(osAdminToSave.getPassword());
//            }
            adminRepository.save(osAdmin);
        }
    }

    @Override
    public int updatePassword(int id, String password) {
        return adminRepository.updatePassword(id, password);
    }

    @Override
    public void save(OsAdmin osAdmin) {
        //adminRepository.saveAdminWithEM(osAdmin);
        adminRepository.save(osAdmin);
    }

    @Override
    public void deleteById(int id) {
        adminRepository.delete(id);
    }

//    @Override
//    public List<Object[]> findOne(String account, String password) {
//        return adminRepository.findOne(account, password);
//    }

    @Override
    public OsAdmin login(String account, String password) {
        return adminRepository.login(account, password);
    }

    @Override
    public boolean existAccount(String account) {
        return 1 == adminRepository.existAccount(account) ? true : false;
    }

    @Override
    public OsAdmin findOne(int id) {
        return adminRepository.findOne(id);
    }

    @Override
    public Page<OsAdmin> findAll(AdminDomain adminDomain) {
        return adminRepository.findAll(getSpecification(adminDomain.getAccount(), adminDomain.getName()), getPageRequest(adminDomain.getPage(), adminDomain.getPageSize(), adminDomain.getSortCol(), adminDomain.getSortDir()));
    }

    @Override
    public Page<OsAdmin> findAllReviewer(AdminDomain adminDomain) {
        return adminRepository.findAll(getReviewerSpecification(adminDomain.getAccount(), adminDomain.getName()), getPageRequest(adminDomain.getPage(), adminDomain.getPageSize(), adminDomain.getSortCol(), adminDomain.getSortDir()));
    }

    @Override
    public List<OsAdmin> listReviewer() {
        return adminRepository.listReviewer();
    }

    @Override
    public int ableAdmin(int id, int status) {
        return adminRepository.ableAdmin(id, status);
    }

    private Specification<OsAdmin> getReviewerSpecification(final String account, final String name) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate.getExpressions().add(cb.like(root.get("account").as(String.class), account));
            predicate.getExpressions().add(cb.like(root.get("name").as(String.class), name));
            predicate.getExpressions().add(cb.equal(root.get("reviewer").as(String.class), "1"));
            return predicate;
        };
    }

    @Override
    public Specification<OsAdmin> getSpecification(String account, String name) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate.getExpressions().add(cb.like(root.get("account").as(String.class), account));
            predicate.getExpressions().add(cb.like(root.get("name").as(String.class), name));
            predicate.getExpressions().add(cb.equal(root.get("reviewer").as(String.class), "0"));
            return predicate;
        };
    }
}
package me.laudukang.persistence.service.impl;

import com.google.common.base.Strings;
import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.repository.AdminRepository;
import me.laudukang.persistence.service.IAdminService;
import me.laudukang.spring.domain.AdminDomain;
import me.laudukang.spring.events.LogEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void updateById(OsAdmin osAdminToSave) {
        OsAdmin osAdmin = adminRepository.findOne(osAdminToSave.getId());
        if (null != osAdmin) {
            osAdmin.setBirth(osAdminToSave.getBirth());
            osAdmin.setAddress(osAdminToSave.getAddress());
            osAdmin.setName(osAdminToSave.getName());
            osAdmin.setMobilePhone(osAdminToSave.getMobilePhone());
            osAdmin.setOfficePhone(osAdminToSave.getOfficePhone());
            osAdmin.setRemark(osAdminToSave.getRemark());
            osAdmin.setSex(osAdminToSave.getSex());
            osAdmin.setSubject(osAdminToSave.getSubject());
            if (!Strings.isNullOrEmpty(osAdminToSave.getPassword())) {
                osAdmin.setPassword(osAdminToSave.getPassword());
            }
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
        applicationContext.publishEvent(new LogEvent(this, "content_" + System.currentTimeMillis(), "lau", "localhost"));
    }

    @Override
    public void deleteById(int id) {
        adminRepository.delete(id);
    }

    @Override
    public List<Object[]> findOne(String account, String password) {
        return adminRepository.findOne(account, password);
    }

    @Override
    public Object[] login(String account, String password) {
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
    public int ableAdmin(int id, int status) {
        return adminRepository.ableAdmin(id, status);
    }

    private Specification<OsAdmin> getReviewerSpecification(final String account, final String name) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate.getExpressions().add(cb.like(root.get("account"), account));
            predicate.getExpressions().add(cb.like(root.get("name"), name));
            predicate.getExpressions().add(cb.equal(root.get("reviewer"), "1"));
            return predicate;
        };
    }

    @Override
    public Specification<OsAdmin> getSpecification(String account, String name) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate.getExpressions().add(cb.like(root.get("account"), account));
            predicate.getExpressions().add(cb.like(root.get("name"), name));
            predicate.getExpressions().add(cb.equal(root.get("reviewer"), "0"));
            return predicate;
        };
    }
}
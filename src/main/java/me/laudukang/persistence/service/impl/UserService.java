package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.repository.UserRepository;
import me.laudukang.persistence.service.IUserService;
import me.laudukang.spring.domain.UserDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/20
 * <p>Time: 12:07
 * <p>Version: 1.0
 */
@Service
public class UserService extends CustomPageService<OsUser> implements IUserService {
    @Autowired
    private UserRepository userRepository;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(OsUser osUser) {
        userRepository.save(osUser);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = "user-cache", key = "'user'+#userDomain.id")
    @Override
    public void updateById(UserDomain userDomain) {
        OsUser result = userRepository.findOne(userDomain.getId());
        if (null != result) {
            result.setName(userDomain.getName());
            result.setRemark(userDomain.getRemark());
            result.setAddress(userDomain.getAddress());
            result.setBirth(userDomain.getBirth());
            result.setCity(userDomain.getCity());
            result.setCountry(userDomain.getCountry());
            result.setMobilePhone(userDomain.getMobilePhone());
            result.setOfficePhone(userDomain.getOfficePhone());
            result.setPostcode(userDomain.getPostcode());
            result.setSex(userDomain.getSex());
            result.setProvince(userDomain.getProvince());
            result.setRemark(userDomain.getRemark());
            userRepository.save(result);
        }
//        else {
//            System.out.println("user id=" + osUser.getId() + " not found");
//        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = "user-cache", key = "'user'+#id")
    @Override
    public void deleteById(int id) {
        userRepository.delete(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable(value = "user-cache")
    @Override
    public Page<OsUser> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable(value = "user-cache")
    @Override
    public Page<OsUser> findAll(UserDomain userDomain) {
        return userRepository.findAll(getSpecification(userDomain.getAccount(), userDomain.getName()),
                getPageRequest(userDomain.getPage(), userDomain.getPageSize(), userDomain.getSortCol(), userDomain.getSortDir()));
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable(value = "user-cache", key = "'user'+#id")
    @Override
    public OsUser findOne(int id) {
        return userRepository.findOne(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable(value = "user-cache", key = "'user'+#account")
    @Override
    public OsUser findByAccount(String account) {
        return userRepository.findByAccountEquals(account);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public OsUser login(String account, String password) {
        return userRepository.login(account, password);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public boolean existAccount(String account) {
        return 0 < userRepository.existAccount(account);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updatePassword(int id, String password) {
        return userRepository.updatePassword(id, password);
    }

}


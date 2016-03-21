package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.repository.UserRepository;
import me.laudukang.persistence.service.IUserService;
import me.laudukang.spring.domain.UserDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/20
 * <p>Time: 12:07
 * <p>Version: 1.0
 */
@Service
@Transactional
public class UserService extends CustomPageService<OsUser> implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(OsUser osUser) {
        userRepository.save(osUser);
    }

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
            userRepository.save(result);
        }
//        else {
//            System.out.println("user id=" + osUser.getId() + " not found");
//        }

    }

    @Override
    public void deleteById(int id) {
        userRepository.delete(id);
    }

    @Override
    public Page<OsUser> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<OsUser> findAll(UserDomain userDomain) {
        return userRepository.findAll(getSpecification(userDomain.getAccount(), userDomain.getName()),
                getPageRequest(userDomain.getPage(), userDomain.getPageSize(), userDomain.getSortCol(), userDomain.getSortDir()));
    }

    @Override
    public OsUser findOne(int id) {
        return userRepository.findOne(id);
    }

    @Override
    public OsUser findByAccount(String account) {
        return userRepository.findByAccountEquals(account);
    }

    @Override
    public OsUser login(String account, String password) {
        return userRepository.login(account, password);
    }

    @Override
    public boolean existAccount(String account) {
        return 1 == userRepository.existAccount(account);
    }

    @Override
    public int updatePassword(int id, String password) {
        return userRepository.updatePassword(id, password);
    }

}


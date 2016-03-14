package me.laudukang.persistence.service.impl;

import com.google.common.base.Strings;
import me.laudukang.persistence.model.OsUser;
import me.laudukang.persistence.repository.UserRepository;
import me.laudukang.persistence.service.IUserService;
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
    public void updateById(OsUser osUser) {
        OsUser result = userRepository.findOne(osUser.getId());
        if (null != result) {
            result.setName(osUser.getName());
            result.setRemark(osUser.getRemark());
            result.setAddress(osUser.getAddress());
            result.setBirth(osUser.getBirth());
            result.setCity(osUser.getCity());
            result.setCountry(osUser.getCountry());
            result.setMobilePhone(osUser.getMobilePhone());
            result.setOfficePhone(osUser.getOfficePhone());
            result.setPostcode(osUser.getPostcode());
            result.setSex(osUser.getSex());
            result.setProvince(osUser.getProvince());
            if (!Strings.isNullOrEmpty(osUser.getPassword())) {
                result.setPassword(osUser.getPassword());
            }
            userRepository.save(result);
            userRepository.flush();
        } else {
            System.out.println("user id=" + osUser.getId() + " not found");
        }

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
        return 1 == userRepository.existAccount(account) ? true : false;
    }

    @Override
    public int updatePassword(int id, String password) {
        return userRepository.updatePassword(id, password);
    }

}


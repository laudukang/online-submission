package me.laudukang.persistence.dao;

import me.laudukang.persistence.model.OsUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/30
 * Time: 23:36
 * Version: 1.0
 */
public interface UserRepository extends JpaRepository<OsUser, Integer> {
}

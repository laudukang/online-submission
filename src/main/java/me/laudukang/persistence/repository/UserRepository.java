package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 23:36
 * <p>Version: 1.0
 */
public interface UserRepository extends JpaRepository<OsUser, Integer> {
    @Query("select user from OsUser user where user.account=:account and user.password=:password")
    OsUser findOne(@Param("account") String account, @Param("password") String password);
}

package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 23:36
 * <p>Version: 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<OsUser, Integer>, JpaSpecificationExecutor<OsUser> {
    //@Query("select user from OsUser user where user.account=:account and user.password=:password")
    //OsUser findOne(@Param("account") String account, @Param("password") String password);

    @Query("select user.id,user.account,user.name from OsUser user where user.account=:account and user.password=:password")
    Object[] login(@Param("account") String account, @Param("password") String password);

    @Query("select count(*) from OsUser u where u.account=:account")
    int existAccount(@Param("account") String account);

    @Query("update OsUser u set u.password=:password where u.id=:id")
    @Modifying
    int updatePassword(@Param("id") int id, @Param("password") String password);
}

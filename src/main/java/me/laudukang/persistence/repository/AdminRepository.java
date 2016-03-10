package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 23:33
 * <p>Version: 1.0
 */
@Repository
public interface AdminRepository extends JpaRepository<OsAdmin, Integer>, AdminRepositoryCustom, JpaSpecificationExecutor<OsAdmin> {
    //void updatePassword(OsAdmin osAdmin);
    //@Query("select admin from OsAdmin admin where admin.account=:account and admin.password=:password")
    //OsAdmin findOne2(@Param("account") String account, @Param("password") String password);

    @Query("select admin.id,admin.account,admin.name from OsAdmin admin where admin.account=:account and admin.password=:password")
    List<Object[]> findOne(@Param("account") String account, @Param("password") String password);

    @Query("select admin.id,admin.account,admin.name from OsAdmin admin where admin.account=:account and admin.password=:password")
    Object[] login(@Param("account") String account, @Param("password") String password);

    @Query("select count(*) from OsAdmin u where u.account=:account")
    int existAccount(@Param("account") String account);

    @Query("update OsAdmin u set u.password=:password where u.id=:id")
    @Modifying
    int updatePassword(@Param("id") int id, @Param("password") String password);

    @Query("update OsAdmin u set u.status=:status where u.id=:id")
    @Modifying
    int ableAdmin(@Param("id") int id, @Param("status") int status);
}

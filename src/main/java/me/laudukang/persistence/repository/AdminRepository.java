package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 23:33
 * <p>Version: 1.0
 */
public interface AdminRepository extends JpaRepository<OsAdmin, Integer>, AdminRepositoryCustom {
    //void updatePassword(OsAdmin osAdmin);
    //@Query("select admin.id,admin.account from OsAdmin admin where admin.account=:account and admin.password=:password")
    //OsAdmin findOne(@Param("account") String account, @Param("password") String password);

    @Query("select admin.id,admin.account,admin.name from OsAdmin admin where admin.account=:account and admin.password=:password")
    List<Object[]> findOne(@Param("account") String account, @Param("password") String password);

}

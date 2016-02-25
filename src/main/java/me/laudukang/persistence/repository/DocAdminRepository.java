package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsDocAdmin;
import me.laudukang.persistence.model.OsDocAdminPK;
import me.laudukang.spring.domain.OsDocAdminDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 23:38
 * <p>Version: 1.0
 */
public interface DocAdminRepository extends JpaRepository<OsDocAdmin, OsDocAdminPK> {

    //@Query("select da from OsDocAdmin da where da.osDoc.id=:id")
    //OsDocAdmin findOneByDocId(@Param("id") int id);

    @Query("select new me.laudukang.spring.domain.OsDocAdminDomain(da.osDoc.id,da.osAdmin.id,da.reviewResult,da.reviewTime,da.osAdmin.account) from OsDocAdmin da where da.osDoc.id=:id")
    OsDocAdminDomain findOneByDocId(@Param("id") int id);
}

package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsDocAdmin;
import me.laudukang.persistence.model.OsDocAdminPK;
import me.laudukang.spring.domain.DocAdminDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 23:38
 * <p>Version: 1.0
 */
@Repository
public interface DocAdminRepository extends JpaRepository<OsDocAdmin, OsDocAdminPK>, JpaSpecificationExecutor<OsDocAdmin> {

    @Query("select da from OsDocAdmin da where da.osDoc.id=:docid and da.osAdmin.id=:adminid")
    OsDocAdmin findOneByDocAdmin(@Param("docid") int docid, @Param("adminid") int adminid);

    @Query("select new me.laudukang.spring.domain.DocAdminDomain(da.osDoc.id,da.osAdmin.id,da.reviewResult,da.reviewTime,da.osAdmin.account) from OsDocAdmin da where da.osDoc.id=:id")
    DocAdminDomain findOneByDocId(@Param("id") int id);
}

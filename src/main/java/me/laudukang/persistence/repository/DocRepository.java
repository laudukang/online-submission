package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 23:37
 * <p>Version: 1.0
 */
@Repository
public interface DocRepository extends JpaRepository<OsDoc, Integer>, JpaSpecificationExecutor<OsDoc> {
    //Page<OsDoc> findByOsUserIdEquals(int id, Pageable pageable);

    //@Query("select doc from OsDoc doc join doc.osDocAdmins da where doc.id=da.osDoc.id and da.osAdmin.id=:adminid")
    //Page<OsDoc> findByAdminId(@Param("adminid") int id, Pageable pageable);
}

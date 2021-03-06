package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface LogRepository extends JpaRepository<OsLog, Integer>, JpaSpecificationExecutor<OsLog> {

    Page<OsLog> findByContentLike(String content, Pageable pageable);

    @Query("select log from OsLog log where log.userOrAdminName like CONCAT('%',:userOrAdminName,'%')")
    Page<OsLog> findByUserOrAdminNameLike(@Param("userOrAdminName") String userOrAdminName, Pageable pageable);

}

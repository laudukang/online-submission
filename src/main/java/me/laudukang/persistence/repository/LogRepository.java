package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 23:38
 * <p>Version: 1.0
 */
public interface LogRepository extends JpaRepository<OsLog, Integer> {
    List<OsLog> findByContentLike(String content);

    @Query("select log from OsLog log where log.userOrAdminName like CONCAT('%',:userOrAdminName,'%')")
    List<OsLog> findByUserOrAdminNameLike(@Param("userOrAdminName") String userOrAdminName);


}

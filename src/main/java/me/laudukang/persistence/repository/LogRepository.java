package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/30
 * Time: 23:38
 * Version: 1.0
 */
public interface LogRepository extends JpaRepository<OsLog, Integer> {
    List<OsLog> findByContentEquals(String content);

    @Query("select log from OsLog log where log.userOrAdminName= ?1")
    List<OsLog> findByUserOrAdminNameEquals(String userOrAdminName);

    @Modifying
    @Query("update OsLog log set log.time=:timestamp where log.id=:id")
    int updateTimeById(@Param("id") int id, @Param("timestamp") Timestamp timestamp);
}

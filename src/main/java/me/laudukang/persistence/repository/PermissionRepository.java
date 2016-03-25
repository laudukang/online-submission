package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 23:38
 * <p>Version: 1.0
 */
@Repository
public interface PermissionRepository extends JpaRepository<OsPermission, Integer> {
    @Query("select p from OsPermission p where p.name='DOC:REVIEW'")
    OsPermission findByDocReviewName();
}

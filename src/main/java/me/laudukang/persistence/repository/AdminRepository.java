package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 23:33
 * <p>Version: 1.0
 */
public interface AdminRepository extends JpaRepository<OsAdmin, Integer>, AdminRepositoryCustom {
}

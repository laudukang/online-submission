package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 23:39
 * <p>Version: 1.0
 */
public interface RoleRepository extends JpaRepository<OsRole, Integer> {
}

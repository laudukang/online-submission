package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsPermission;
import me.laudukang.persistence.model.OsRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 23:39
 * <p>Version: 1.0
 */
@Repository
public interface RoleRepository extends JpaRepository<OsRole, Integer>, JpaSpecificationExecutor<OsRole> {

    @Query("select role from OsRole role left join role.osAdmins admin where admin.id=:id")
    List<OsRole> findRoleByAdminId(@Param("id") int id);

    @Query("select permission from  OsPermission permission where permission.osRole is null ")
    List<OsPermission> findUnAssignPermission();
}

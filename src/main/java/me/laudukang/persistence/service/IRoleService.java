package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsRole;
import me.laudukang.spring.domain.RoleDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/18
 * <p>Time: 18:53
 * <p>Version: 1.0
 */
public interface IRoleService {

    void save(OsRole osRole, int[] osPermissions);

    void deleteById(int id);

    OsRole findOne(int id);

    void updateById(RoleDomain roleDomain, int[] osPermissions);

    Page<OsRole> findAll(Pageable pageable);

    List<OsRole> findRoleByAdminId(int id);
}

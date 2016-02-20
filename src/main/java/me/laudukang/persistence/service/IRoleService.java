package me.laudukang.persistence.service;

import me.laudukang.persistence.model.OsRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/18
 * <p>Time: 18:53
 * <p>Version: 1.0
 */
public interface IRoleService {

    void save(OsRole osRole, int[] permission);

    void deleteById(int id);

    void updateById(OsRole osRole, int[] permission);

    Page<OsRole> findAll(Pageable pageable);
}

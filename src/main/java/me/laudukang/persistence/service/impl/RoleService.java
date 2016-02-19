package me.laudukang.persistence.service.impl;

import me.laudukang.persistence.model.OsPermission;
import me.laudukang.persistence.model.OsRole;
import me.laudukang.persistence.repository.PermissionRepository;
import me.laudukang.persistence.repository.RoleRepository;
import me.laudukang.persistence.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/18
 * <p>Time: 18:53
 * <p>Version: 1.0
 */
@Service
@Transactional
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public void save(OsRole osRole, int[] permission) {

        for (int id : permission
                ) {
            OsPermission osPermission = permissionRepository.findOne(id);
            if (null != osPermission) {
                osRole.addOsPermission(osPermission);
            } else {
                System.out.println("id=" + id + " permission is not exit");
            }
        }
        roleRepository.save(osRole);
    }

    @Override
    public void deleteById(int id) {
        roleRepository.delete(id);
    }

    @Override
    public void update(OsRole osRole, int[] permission) {
        OsRole result = roleRepository.findOne(osRole.getId());
        if (null != result) {
            result.setName(osRole.getName());
            result.setRemark(osRole.getRemark());
            if (null != result.getOsPermissions()) {
                for (OsPermission op : result.getOsPermissions()
                        ) {
                    op.setOsRole(null);
                }
            }
            permissionRepository.save(result.getOsPermissions());
        }

        List<OsPermission> osPermissions = new ArrayList<>();
        for (int id : permission
                ) {
            OsPermission osPermission = permissionRepository.findOne(id);
            if (null != osPermission) {
                osPermission.setOsRole(result);
                osPermissions.add(osPermission);
            } else {
                System.out.println("id=" + id + " permission is not exit");
            }
        }

        result.setOsPermissions(osPermissions);
        roleRepository.save(result);
    }

    @Override
    public Page<OsRole> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }
}

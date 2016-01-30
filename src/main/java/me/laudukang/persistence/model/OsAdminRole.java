package me.laudukang.persistence.model;

import javax.persistence.*;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/30
 * Time: 22:45
 * Version: 1.0
 */
@Entity
@Table(name = "os_admin_role", schema = "online_submission")
@IdClass(OsAdminRolePK.class)
public class OsAdminRole {
    private int adminId;
    private int roleId;

    @Id
    @Column(name = "admin_id", nullable = false)
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Id
    @Column(name = "role_id", nullable = false)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OsAdminRole that = (OsAdminRole) o;

        if (adminId != that.adminId) return false;
        return roleId == that.roleId;

    }

    @Override
    public int hashCode() {
        int result = adminId;
        result = 31 * result + roleId;
        return result;
    }
}

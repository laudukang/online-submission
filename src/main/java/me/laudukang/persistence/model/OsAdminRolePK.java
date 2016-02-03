package me.laudukang.persistence.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 22:45
 * <p>Version: 1.0
 */
public class OsAdminRolePK implements Serializable {
    private int adminId;
    private int roleId;

    @Column(name = "admin_id", nullable = false)
    @Id
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Column(name = "role_id", nullable = false)
    @Id
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

        OsAdminRolePK that = (OsAdminRolePK) o;

        if (adminId != that.adminId) return false;
        return roleId == that.roleId;

    }

    @Override
    public int hashCode() {
        int result = adminId;
        result = 31 * result + roleId;
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }
}

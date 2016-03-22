package me.laudukang.spring.domain;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/18
 * <p>Time: 0:34
 * <p>Version: 1.0
 */
public class RoleDomain {
    private int id;

    @NotEmpty
    private String name;

    private String remark;

    private int[] osPermissions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int[] getOsPermissions() {
        return osPermissions;
    }

    public void setOsPermissions(int[] osPermissions) {
        this.osPermissions = osPermissions;
    }
}

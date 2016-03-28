package me.laudukang.spring.domain;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/18
 * <p>Time: 0:34
 * <p>Version: 1.0
 */
public class RoleDomain {
    private int id;

    private String name;

    private String remark;

    private int[] osPermissions;

    private int page;
    private int pageSize;
    private String sortDir;
    private String sortCol;

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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public String getSortCol() {
        return sortCol;
    }

    public void setSortCol(String sortCol) {
        this.sortCol = sortCol;
    }
}

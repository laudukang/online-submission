package me.laudukang.spring.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/27
 * <p>Time: 16:25
 * <p>Version: 1.0
 */
public class AdminDomain {
    @NotEmpty(message = "账号不能为空")
    private String account;
    @NotEmpty(message = "密码不能为空")
    private String password;
    //@NotEmpty(message = "验证码不能为空")
    private String verificationCode;

    private String name;
    private String fromTime;
    private String toTime;
    private int page;
    private int pageSize;
    private String sortDir;
    private String sortCol;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page - 1;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }

}

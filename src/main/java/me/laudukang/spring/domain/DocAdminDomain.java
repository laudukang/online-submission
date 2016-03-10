package me.laudukang.spring.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/25
 * <p>Time: 23:47
 * <p>Version: 1.0
 */
public class DocAdminDomain {
    private int docId;
    private int adminId;
    private String reviewResult;
    private Date reviewTime;
    private String adminAccount;

    public DocAdminDomain() {
    }

    public DocAdminDomain(int docId, int adminId, String reviewResult, Date reviewTime, String adminAccount) {
        this.docId = docId;
        this.adminId = adminId;
        this.reviewResult = reviewResult;
        this.reviewTime = reviewTime;
        this.adminAccount = adminAccount;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(String reviewResult) {
        this.reviewResult = reviewResult;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }
}

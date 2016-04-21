package me.laudukang.spring.events;

import org.springframework.context.ApplicationEvent;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/22
 * <p>Time: 14:36
 * <p>Version: 1.0
 */
public class DocEvent extends ApplicationEvent {
    int docid;
    int adminid;
    String propose;
    String reviewResult;
    String account;
    String mailType;
    String url;
    String ip;

    public DocEvent(Object source) {
        super(source);
    }

    public DocEvent(Object source, int docid, int adminid, String propose, String reviewResult,
                    String account, String mailType, String url, String ip) {
        super(source);
        this.docid = docid;
        this.adminid = adminid;
        this.reviewResult = reviewResult;
        this.propose = propose;
        this.account = account;
        this.mailType = mailType;
        this.url = url;
        this.ip = ip;
    }

    public int getDocid() {
        return docid;
    }

    public void setDocid(int docid) {
        this.docid = docid;
    }

    public int getAdminid() {
        return adminid;
    }

    public void setAdminid(int adminid) {
        this.adminid = adminid;
    }

    public String getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(String reviewResult) {
        this.reviewResult = reviewResult;
    }

    public String getPropose() {
        return propose;
    }

    public void setPropose(String propose) {
        this.propose = propose;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

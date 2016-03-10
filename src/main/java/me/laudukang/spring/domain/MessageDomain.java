package me.laudukang.spring.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/25
 * <p>Time: 14:06
 * <p>Version: 1.0
 */
public class MessageDomain {
    private int id;
    private String title;
    private String content;
    private Date postTime;
    private String userAccount;
    private String adminAccount;

    public MessageDomain() {
    }

    public MessageDomain(int id) {
        this.id = id;
    }

    public MessageDomain(int id, String title, String content, Date postTime, String userAccount, String adminAccount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postTime = postTime;
        this.userAccount = userAccount;
        this.adminAccount = adminAccount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
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

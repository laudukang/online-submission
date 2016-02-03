package me.laudukang.persistence.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 22:45
 * <p>Version: 1.0
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "os_chat", schema = "online_submission")
public class OsChat {
    private int id;
    private String title;
    private String content;
    private Date postTime;
    private Integer userId;
    private Integer adminId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content", nullable = true, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    @Column(name = "post_time", nullable = true)
    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "admin_id", nullable = true)
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OsChat osChat = (OsChat) o;

        if (id != osChat.id) return false;
        if (title != null ? !title.equals(osChat.title) : osChat.title != null) return false;
        if (content != null ? !content.equals(osChat.content) : osChat.content != null) return false;
        if (postTime != null ? !postTime.equals(osChat.postTime) : osChat.postTime != null) return false;
        if (userId != null ? !userId.equals(osChat.userId) : osChat.userId != null) return false;
        return adminId != null ? adminId.equals(osChat.adminId) : osChat.adminId == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (postTime != null ? postTime.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (adminId != null ? adminId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }
}

package me.laudukang.persistence.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 22:45
 * <p>Version: 1.0
 */
@Entity
@Table(name = "os_doc", schema = "online_submission")
public class OsDoc {
    private int id;
    private String zhTitle;
    private String enTitle;
    private String subject;
    private String column;
    private String type;
    private String zhKeyword;
    private String enKeyword;
    private String zhSummary;
    private String enSummary;
    private Timestamp postTime;
    private String status;
    private Timestamp statusTime;
    private Integer userId;
    private Set<OsAuthor> osAuthors = new HashSet<OsAuthor>();

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
    @Column(name = "zh_title", nullable = true, length = 255)
    public String getZhTitle() {
        return zhTitle;
    }

    public void setZhTitle(String zhTitle) {
        this.zhTitle = zhTitle;
    }

    @Basic
    @Column(name = "en_title", nullable = true, length = 255)
    public String getEnTitle() {
        return enTitle;
    }

    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    @Basic
    @Column(name = "subject", nullable = true, length = 255)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "column", nullable = true, length = 255)
    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 255)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "zh_keyword", nullable = true, length = 255)
    public String getZhKeyword() {
        return zhKeyword;
    }

    public void setZhKeyword(String zhKeyword) {
        this.zhKeyword = zhKeyword;
    }

    @Basic
    @Column(name = "en_keyword", nullable = true, length = 255)
    public String getEnKeyword() {
        return enKeyword;
    }

    public void setEnKeyword(String enKeyword) {
        this.enKeyword = enKeyword;
    }

    @Basic
    @Column(name = "zh_summary", nullable = true, length = 255)
    public String getZhSummary() {
        return zhSummary;
    }

    public void setZhSummary(String zhSummary) {
        this.zhSummary = zhSummary;
    }

    @Basic
    @Column(name = "en_summary", nullable = true, length = 255)
    public String getEnSummary() {
        return enSummary;
    }

    public void setEnSummary(String enSummary) {
        this.enSummary = enSummary;
    }

    @Basic
    @Column(name = "post_time", nullable = true)
    public Timestamp getPostTime() {
        return postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 255)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "status_time", nullable = true)
    public Timestamp getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Timestamp statusTime) {
        this.statusTime = statusTime;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)//级联保存、更新、删除、刷新;延迟加载
    @JoinColumn(name = "id")//在book表增加一个外键列来实现一对多的单向关联
    public Set<OsAuthor> getOsAuthors() {
        return osAuthors;
    }

    public void setOsAuthors(Set<OsAuthor> osAuthors) {
        this.osAuthors = osAuthors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OsDoc osDoc = (OsDoc) o;

        if (id != osDoc.id) return false;
        if (zhTitle != null ? !zhTitle.equals(osDoc.zhTitle) : osDoc.zhTitle != null) return false;
        if (enTitle != null ? !enTitle.equals(osDoc.enTitle) : osDoc.enTitle != null) return false;
        if (subject != null ? !subject.equals(osDoc.subject) : osDoc.subject != null) return false;
        if (column != null ? !column.equals(osDoc.column) : osDoc.column != null) return false;
        if (type != null ? !type.equals(osDoc.type) : osDoc.type != null) return false;
        if (zhKeyword != null ? !zhKeyword.equals(osDoc.zhKeyword) : osDoc.zhKeyword != null) return false;
        if (enKeyword != null ? !enKeyword.equals(osDoc.enKeyword) : osDoc.enKeyword != null) return false;
        if (zhSummary != null ? !zhSummary.equals(osDoc.zhSummary) : osDoc.zhSummary != null) return false;
        if (enSummary != null ? !enSummary.equals(osDoc.enSummary) : osDoc.enSummary != null) return false;
        if (postTime != null ? !postTime.equals(osDoc.postTime) : osDoc.postTime != null) return false;
        if (status != null ? !status.equals(osDoc.status) : osDoc.status != null) return false;
        if (statusTime != null ? !statusTime.equals(osDoc.statusTime) : osDoc.statusTime != null) return false;
        return userId != null ? userId.equals(osDoc.userId) : osDoc.userId == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (zhTitle != null ? zhTitle.hashCode() : 0);
        result = 31 * result + (enTitle != null ? enTitle.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (column != null ? column.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (zhKeyword != null ? zhKeyword.hashCode() : 0);
        result = 31 * result + (enKeyword != null ? enKeyword.hashCode() : 0);
        result = 31 * result + (zhSummary != null ? zhSummary.hashCode() : 0);
        result = 31 * result + (enSummary != null ? enSummary.hashCode() : 0);
        result = 31 * result + (postTime != null ? postTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (statusTime != null ? statusTime.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }
}

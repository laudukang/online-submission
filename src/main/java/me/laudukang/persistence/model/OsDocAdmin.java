package me.laudukang.persistence.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/30
 * Time: 22:45
 * Version: 1.0
 */
@Entity
@Table(name = "os_doc_admin", schema = "online_submission")
@IdClass(OsDocAdminPK.class)
public class OsDocAdmin {
    private int docId;
    private int adminId;
    private String reviewResult;
    private Timestamp reviewTime;

    @Id
    @Column(name = "doc_id", nullable = false)
    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    @Id
    @Column(name = "admin_id", nullable = false)
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Basic
    @Column(name = "review_result", nullable = true, length = -1)
    public String getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(String reviewResult) {
        this.reviewResult = reviewResult;
    }

    @Basic
    @Column(name = "review_time", nullable = true)
    public Timestamp getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Timestamp reviewTime) {
        this.reviewTime = reviewTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OsDocAdmin that = (OsDocAdmin) o;

        if (docId != that.docId) return false;
        if (adminId != that.adminId) return false;
        if (reviewResult != null ? !reviewResult.equals(that.reviewResult) : that.reviewResult != null) return false;
        return reviewTime != null ? reviewTime.equals(that.reviewTime) : that.reviewTime == null;

    }

    @Override
    public int hashCode() {
        int result = docId;
        result = 31 * result + adminId;
        result = 31 * result + (reviewResult != null ? reviewResult.hashCode() : 0);
        result = 31 * result + (reviewTime != null ? reviewTime.hashCode() : 0);
        return result;
    }
}

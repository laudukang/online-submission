package me.laudukang.persistence.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the os_doc_admin database table.
 */
@Entity
@Table(name = "os_doc_admin")
@NamedQuery(name = "OsDocAdmin.findAll", query = "SELECT o FROM OsDocAdmin o")
public class OsDocAdmin implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OsDocAdminPK id;

    @Lob
    @Column(name = "review_result", columnDefinition = "text")
    private String reviewResult;

    @Column(name = "review_time")
    private Timestamp reviewTime;

    //bi-directional many-to-one association to OsAdmin
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = true, insertable = false, updatable = false)
    private OsAdmin osAdmin;

    //bi-directional many-to-one association to OsDoc
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", nullable = true, insertable = false, updatable = false)
    private OsDoc osDoc;

    public OsDocAdmin() {
    }

    public OsDocAdminPK getId() {
        return this.id;
    }

    public void setId(OsDocAdminPK id) {
        this.id = id;
    }

    public String getReviewResult() {
        return this.reviewResult;
    }

    public void setReviewResult(String reviewResult) {
        this.reviewResult = reviewResult;
    }

    public Timestamp getReviewTime() {
        return this.reviewTime;
    }

    public void setReviewTime(Timestamp reviewTime) {
        this.reviewTime = reviewTime;
    }

    public OsAdmin getOsAdmin() {
        return this.osAdmin;
    }

    public void setOsAdmin(OsAdmin osAdmin) {
        this.osAdmin = osAdmin;
    }

    public OsDoc getOsDoc() {
        return this.osDoc;
    }

    public void setOsDoc(OsDoc osDoc) {
        this.osDoc = osDoc;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }
}
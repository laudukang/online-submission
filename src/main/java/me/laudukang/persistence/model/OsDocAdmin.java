package me.laudukang.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the os_doc_admin database table.
 */
@Entity
@Table(name = "os_doc_admin")
@NamedQuery(name = "OsDocAdmin.findAll", query = "SELECT o FROM OsDocAdmin o")
@EntityListeners({AuditingEntityListener.class})
public class OsDocAdmin implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OsDocAdminPK id;

    @Lob
    @Column(name = "review_result", columnDefinition = "text")
    private String reviewResult;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "review_time")
    private Date reviewTime;


    private String propose;

    //bi-directional many-to-one association to OsAdmin
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", insertable = false, updatable = false)
    private OsAdmin osAdmin;

    //bi-directional many-to-one association to OsDoc
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", insertable = false, updatable = false)
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

    public Date getReviewTime() {
        return this.reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getPropose() {
        return propose;
    }

    public void setPropose(String propose) {
        this.propose = propose;
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
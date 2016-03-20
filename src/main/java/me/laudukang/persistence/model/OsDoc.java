package me.laudukang.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the os_doc database table.
 */
@Entity
@Table(name = "os_doc", schema = "online_submission")
@NamedQuery(name = "OsDoc.findAll", query = "SELECT o FROM OsDoc o")
@EntityListeners({AuditingEntityListener.class})
public class OsDoc implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String classification;

    @Column(name = "en_keyword")
    private String enKeyword;

    @Column(name = "en_summary")
    private String enSummary;

    @Column(name = "en_title")
    private String enTitle;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "post_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postTime;

    private String status;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "status_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusTime;

    private String subject;

    private String type;

    @Column(name = "zh_keyword")
    private String zhKeyword;

    @Column(name = "zh_summary")
    private String zhSummary;

    @Column(name = "zh_title")
    private String zhTitle;

    //bi-directional many-to-one association to OsAuthor
    //@JsonBackReference
    @OneToMany(mappedBy = "osDoc", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<OsAuthor> osAuthors = new ArrayList<>();

    //bi-directional many-to-one association to OsUser
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private OsUser osUser;

    //bi-directional many-to-one association to OsDocAdmin
    @JsonBackReference
    @OneToMany(mappedBy = "osDoc", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<OsDocAdmin> osDocAdmins = new ArrayList<>();

    @Column(name = "path")
    private String path;

    public OsDoc() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getEnKeyword() {
        return this.enKeyword;
    }

    public void setEnKeyword(String enKeyword) {
        this.enKeyword = enKeyword;
    }

    public String getEnSummary() {
        return this.enSummary;
    }

    public void setEnSummary(String enSummary) {
        this.enSummary = enSummary;
    }

    public String getEnTitle() {
        return this.enTitle;
    }

    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    public Date getPostTime() {
        return this.postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusTime() {
        return this.statusTime;
    }

    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZhKeyword() {
        return this.zhKeyword;
    }

    public void setZhKeyword(String zhKeyword) {
        this.zhKeyword = zhKeyword;
    }

    public String getZhSummary() {
        return this.zhSummary;
    }

    public void setZhSummary(String zhSummary) {
        this.zhSummary = zhSummary;
    }

    public String getZhTitle() {
        return this.zhTitle;
    }

    public void setZhTitle(String zhTitle) {
        this.zhTitle = zhTitle;
    }

    public List<OsAuthor> getOsAuthors() {
        return this.osAuthors;
    }

    public void setOsAuthors(List<OsAuthor> osAuthors) {
        this.osAuthors = osAuthors;
    }

    public OsAuthor addOsAuthor(OsAuthor osAuthor) {
        getOsAuthors().add(osAuthor);
        osAuthor.setOsDoc(this);

        return osAuthor;
    }

    public OsAuthor removeOsAuthor(OsAuthor osAuthor) {
        getOsAuthors().remove(osAuthor);
        osAuthor.setOsDoc(null);

        return osAuthor;
    }

    public OsUser getOsUser() {
        return this.osUser;
    }

    public void setOsUser(OsUser osUser) {
        this.osUser = osUser;
    }

    public List<OsDocAdmin> getOsDocAdmins() {
        return this.osDocAdmins;
    }

    public void setOsDocAdmins(List<OsDocAdmin> osDocAdmins) {
        this.osDocAdmins = osDocAdmins;
    }

    public OsDocAdmin addOsDocAdmin(OsDocAdmin osDocAdmin) {
        getOsDocAdmins().add(osDocAdmin);
        osDocAdmin.setOsDoc(this);

        return osDocAdmin;
    }

    public OsDocAdmin removeOsDocAdmin(OsDocAdmin osDocAdmin) {
        getOsDocAdmins().remove(osDocAdmin);
        osDocAdmin.setOsDoc(null);

        return osDocAdmin;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
//    }
}
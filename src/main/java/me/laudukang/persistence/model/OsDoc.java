package me.laudukang.persistence.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the os_doc database table.
 */
@Entity
@Table(name = "os_doc", schema = "online_submission")
@NamedQuery(name = "OsDoc.findAll", query = "SELECT o FROM OsDoc o")
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

    @Column(name = "post_time")
    private Timestamp postTime;

    private String status;

    @Column(name = "status_time")
    private Timestamp statusTime;

    private String subject;

    private String type;

    @Column(name = "zh_keyword")
    private String zhKeyword;

    @Column(name = "zh_summary")
    private String zhSummary;

    @Column(name = "zh_title")
    private String zhTitle;

    //bi-directional many-to-one association to OsAuthor
    @OneToMany(mappedBy = "osDoc", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<OsAuthor> osAuthors = new ArrayList<>();

    //bi-directional many-to-one association to OsUser
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private OsUser osUser;

    //bi-directional many-to-one association to OsDocAdmin
    @OneToMany(mappedBy = "osDoc", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<OsDocAdmin> osDocAdmins = new ArrayList<>();

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

    public Timestamp getPostTime() {
        return this.postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getStatusTime() {
        return this.statusTime;
    }

    public void setStatusTime(Timestamp statusTime) {
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }
}
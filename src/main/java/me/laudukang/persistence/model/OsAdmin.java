package me.laudukang.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the os_admin database table.
 */
@Entity
@Table(name = "os_admin")
@NamedQuery(name = "OsAdmin.findAll", query = "SELECT o FROM OsAdmin o")
public class OsAdmin implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Email
    @NotEmpty(message = "账号不能为空")
    private String account;

    private String address;

    @Temporal(TemporalType.DATE)
    private Date birth;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    private String name;

    @Column(name = "office_phone")
    private String officePhone;

    @NotEmpty(message = "密码不能为空")
    @Column(columnDefinition = "char")
    private String password;

    private String remark;

    @Column(columnDefinition = "char")
    private String sex;

    private String subject;

    private int status;

    @Column(columnDefinition = "char")
    private String reviewer;

    //bi-directional many-to-one association to OsMessage
    @JsonBackReference
    @OneToMany(mappedBy = "osAdmin", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<OsMessage> osMessages = new ArrayList<>();

    //bi-directional many-to-one association to OsDocAdmin
    @JsonBackReference
    @OneToMany(mappedBy = "osAdmin", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<OsDocAdmin> osDocAdmins = new ArrayList<>();

    //bi-directional many-to-many association to OsRole
    @JsonBackReference
    @ManyToMany(mappedBy = "osAdmins")
    private List<OsRole> osRoles = new ArrayList<>();

    public OsAdmin() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirth() {
        return this.birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficePhone() {
        return this.officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public List<OsMessage> getOsMessages() {
        return this.osMessages;
    }

    public void setOsMessages(List<OsMessage> osMessages) {
        this.osMessages = osMessages;
    }

    public OsMessage addOsChat(OsMessage osMessage) {
        getOsMessages().add(osMessage);
        osMessage.setOsAdmin(this);

        return osMessage;
    }

    public OsMessage removeOsChat(OsMessage osMessage) {
        getOsMessages().remove(osMessage);
        osMessage.setOsAdmin(null);

        return osMessage;
    }

    public List<OsDocAdmin> getOsDocAdmins() {
        return this.osDocAdmins;
    }

    public void setOsDocAdmins(List<OsDocAdmin> osDocAdmins) {
        this.osDocAdmins = osDocAdmins;
    }

    public OsDocAdmin addOsDocAdmin(OsDocAdmin osDocAdmin) {
        getOsDocAdmins().add(osDocAdmin);
        osDocAdmin.setOsAdmin(this);

        return osDocAdmin;
    }

    public OsDocAdmin removeOsDocAdmin(OsDocAdmin osDocAdmin) {
        getOsDocAdmins().remove(osDocAdmin);
        osDocAdmin.setOsAdmin(null);

        return osDocAdmin;
    }

    public List<OsRole> getOsRoles() {
        return this.osRoles;
    }

    public void setOsRoles(List<OsRole> osRoles) {
        this.osRoles = osRoles;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }
}
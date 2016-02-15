package me.laudukang.persistence.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    private String account;

    private String address;

    @Temporal(TemporalType.DATE)
    private Date birth;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    private String name;

    @Column(name = "office_phone")
    private String officePhone;

    @Column(columnDefinition = "char")
    private String password;

    private String remark;

    @Column(columnDefinition = "char")
    private String sex;

    private String subject;

    //bi-directional many-to-one association to OsChat
    @OneToMany(mappedBy = "osAdmin", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<OsChat> osChats = new ArrayList<>();

    //bi-directional many-to-one association to OsDocAdmin
    @OneToMany(mappedBy = "osAdmin", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<OsDocAdmin> osDocAdmins = new ArrayList<>();

    //bi-directional many-to-many association to OsRole
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

    public List<OsChat> getOsChats() {
        return this.osChats;
    }

    public void setOsChats(List<OsChat> osChats) {
        this.osChats = osChats;
    }

    public OsChat addOsChat(OsChat osChat) {
        getOsChats().add(osChat);
        osChat.setOsAdmin(this);

        return osChat;
    }

    public OsChat removeOsChat(OsChat osChat) {
        getOsChats().remove(osChat);
        osChat.setOsAdmin(null);

        return osChat;
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
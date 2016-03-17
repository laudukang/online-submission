package me.laudukang.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the os_user database table.
 */
@Entity
@Table(name = "os_user")
@NamedQuery(name = "OsUser.findAll", query = "SELECT o FROM OsUser o")
public class OsUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@NotEmpty(message = "账号不能为空")
    private String account;

    private String address;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date birth;

    private String city;

    private String country;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    private String name;

    @Column(name = "office_phone")
    private String officePhone;

    //@NotEmpty(message = "密码不能为空")
    @Column(columnDefinition = "char")
    private String password;

    private String postcode;

    private String province;

    private String remark;

    @Column(columnDefinition = "char")
    private String sex;

    private String status;

    //bi-directional many-to-one association to OsMessage
    @JsonBackReference
    @OneToMany(mappedBy = "osUser")
    private List<OsMessage> osMessages = new ArrayList<OsMessage>();

    //bi-directional many-to-one association to OsDoc
    @JsonBackReference
    @OneToMany(mappedBy = "osUser")
    private List<OsDoc> osDocs = new ArrayList<OsDoc>();

    public OsUser() {
    }

    public OsUser(int id) {
        this.id = id;
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

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getPostcode() {
        return this.postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OsMessage> getOsMessages() {
        return this.osMessages;
    }

    public void setOsMessages(List<OsMessage> osMessages) {
        this.osMessages = osMessages;
    }

    public OsMessage addOsChat(OsMessage osMessage) {
        getOsMessages().add(osMessage);
        osMessage.setOsUser(this);

        return osMessage;
    }

    public OsMessage removeOsChat(OsMessage osMessage) {
        getOsMessages().remove(osMessage);
        osMessage.setOsUser(null);

        return osMessage;
    }

    public List<OsDoc> getOsDocs() {
        return this.osDocs;
    }

    public void setOsDocs(List<OsDoc> osDocs) {
        this.osDocs = osDocs;
    }

    public OsDoc addOsDoc(OsDoc osDoc) {
        getOsDocs().add(osDoc);
        osDoc.setOsUser(this);

        return osDoc;
    }

    public OsDoc removeOsDoc(OsDoc osDoc) {
        getOsDocs().remove(osDoc);
        osDoc.setOsUser(null);

        return osDoc;
    }

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
//    }
}
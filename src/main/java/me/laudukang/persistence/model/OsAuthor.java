package me.laudukang.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the os_author database table.
 */
@Entity
@Table(name = "os_author", schema = "online_submission")
@NamedQuery(name = "OsAuthor.findAll", query = "SELECT o FROM OsAuthor o")
public class OsAuthor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String address;

    @Temporal(TemporalType.DATE)
    private Date bitrh;

    private String city;

    private String country;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    private String name;

    @Column(name = "office_phone")
    private String officePhone;

    private String postcode;

    private String province;

    private String remark;

    @Column(name = "sex", nullable = true, length = 2, columnDefinition = "char")
    private String sex;

    private String mail;

    //bi-directional many-to-one association to OsDoc
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id")
    private OsDoc osDoc;

    public OsAuthor() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBitrh() {
        return this.bitrh;
    }

    public void setBitrh(Date bitrh) {
        this.bitrh = bitrh;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
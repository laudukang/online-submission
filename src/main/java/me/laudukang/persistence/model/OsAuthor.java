package me.laudukang.persistence.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created with IDEA
 * Author: laudukang
 * Date: 2016/1/30
 * Time: 22:45
 * Version: 1.0
 */
@Entity
@Table(name = "os_author", schema = "online_submission")
public class OsAuthor {
    private int id;
    private String name;
    private String sex;
    private Date bitrh;
    private String country;
    private String province;
    private String city;
    private String postcode;
    private String address;
    private String officePhone;
    private String mobilePhone;
    private Integer docId;
    private String remark;

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
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "sex", nullable = true, length = 2)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "bitrh", nullable = true)
    public Date getBitrh() {
        return bitrh;
    }

    public void setBitrh(Date bitrh) {
        this.bitrh = bitrh;
    }

    @Basic
    @Column(name = "country", nullable = true, length = 255)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "province", nullable = true, length = 255)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "city", nullable = true, length = 255)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "postcode", nullable = true, length = 255)
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "office_phone", nullable = true, length = 255)
    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    @Basic
    @Column(name = "mobile_phone", nullable = true, length = 255)
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Basic
    @Column(name = "doc_id", nullable = true)
    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 255)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OsAuthor osAuthor = (OsAuthor) o;

        if (id != osAuthor.id) return false;
        if (name != null ? !name.equals(osAuthor.name) : osAuthor.name != null) return false;
        if (sex != null ? !sex.equals(osAuthor.sex) : osAuthor.sex != null) return false;
        if (bitrh != null ? !bitrh.equals(osAuthor.bitrh) : osAuthor.bitrh != null) return false;
        if (country != null ? !country.equals(osAuthor.country) : osAuthor.country != null) return false;
        if (province != null ? !province.equals(osAuthor.province) : osAuthor.province != null) return false;
        if (city != null ? !city.equals(osAuthor.city) : osAuthor.city != null) return false;
        if (postcode != null ? !postcode.equals(osAuthor.postcode) : osAuthor.postcode != null) return false;
        if (address != null ? !address.equals(osAuthor.address) : osAuthor.address != null) return false;
        if (officePhone != null ? !officePhone.equals(osAuthor.officePhone) : osAuthor.officePhone != null)
            return false;
        if (mobilePhone != null ? !mobilePhone.equals(osAuthor.mobilePhone) : osAuthor.mobilePhone != null)
            return false;
        if (docId != null ? !docId.equals(osAuthor.docId) : osAuthor.docId != null) return false;
        return remark != null ? remark.equals(osAuthor.remark) : osAuthor.remark == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (bitrh != null ? bitrh.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (officePhone != null ? officePhone.hashCode() : 0);
        result = 31 * result + (mobilePhone != null ? mobilePhone.hashCode() : 0);
        result = 31 * result + (docId != null ? docId.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }
}

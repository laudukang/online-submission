package me.laudukang.persistence.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Date;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 22:45
 * <p>Version: 1.0
 */
@Entity
@Table(name = "os_user", schema = "online_submission")
public class OsUser {
    private int id;
    private String account;
    private String name;
    private String sex;
    private Date birth;
    private String country;
    private String province;
    private String city;
    private String postcode;
    private String address;
    private String officePhone;
    private String mobilePhone;
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
    @Column(name = "account", nullable = false, length = 255)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
    @Column(name = "birth", nullable = true)
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
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

        OsUser osUser = (OsUser) o;

        if (id != osUser.id) return false;
        if (account != null ? !account.equals(osUser.account) : osUser.account != null) return false;
        if (name != null ? !name.equals(osUser.name) : osUser.name != null) return false;
        if (sex != null ? !sex.equals(osUser.sex) : osUser.sex != null) return false;
        if (birth != null ? !birth.equals(osUser.birth) : osUser.birth != null) return false;
        if (country != null ? !country.equals(osUser.country) : osUser.country != null) return false;
        if (province != null ? !province.equals(osUser.province) : osUser.province != null) return false;
        if (city != null ? !city.equals(osUser.city) : osUser.city != null) return false;
        if (postcode != null ? !postcode.equals(osUser.postcode) : osUser.postcode != null) return false;
        if (address != null ? !address.equals(osUser.address) : osUser.address != null) return false;
        if (officePhone != null ? !officePhone.equals(osUser.officePhone) : osUser.officePhone != null) return false;
        if (mobilePhone != null ? !mobilePhone.equals(osUser.mobilePhone) : osUser.mobilePhone != null) return false;
        return remark != null ? remark.equals(osUser.remark) : osUser.remark == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (officePhone != null ? officePhone.hashCode() : 0);
        result = 31 * result + (mobilePhone != null ? mobilePhone.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }
}

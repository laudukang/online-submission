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
@Table(name = "os_admin", schema = "online_submission")
public class OsAdmin {
    private int id;
    private String account;
    private String password;
    private String name;
    private String sex;
    private Date birth;
    private String subject;
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
    @Column(name = "password", nullable = true, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    @Column(name = "subject", nullable = true, length = 255)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

        OsAdmin osAdmin = (OsAdmin) o;

        if (id != osAdmin.id) return false;
        if (account != null ? !account.equals(osAdmin.account) : osAdmin.account != null) return false;
        if (name != null ? !name.equals(osAdmin.name) : osAdmin.name != null) return false;
        if (sex != null ? !sex.equals(osAdmin.sex) : osAdmin.sex != null) return false;
        if (birth != null ? !birth.equals(osAdmin.birth) : osAdmin.birth != null) return false;
        if (subject != null ? !subject.equals(osAdmin.subject) : osAdmin.subject != null) return false;
        if (address != null ? !address.equals(osAdmin.address) : osAdmin.address != null) return false;
        if (officePhone != null ? !officePhone.equals(osAdmin.officePhone) : osAdmin.officePhone != null) return false;
        if (mobilePhone != null ? !mobilePhone.equals(osAdmin.mobilePhone) : osAdmin.mobilePhone != null) return false;
        return remark != null ? remark.equals(osAdmin.remark) : osAdmin.remark == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
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

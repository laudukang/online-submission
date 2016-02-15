package me.laudukang.persistence.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the os_role database table.
 */
@Entity
@Table(name = "os_role")
@NamedQuery(name = "OsRole.findAll", query = "SELECT o FROM OsRole o")
public class OsRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String remark;

    //bi-directional many-to-one association to OsPermission
    @OneToMany(mappedBy = "osRole")
    private List<OsPermission> osPermissions = new ArrayList<>();

    //bi-directional many-to-many association to OsAdmin
    @ManyToMany
    @JoinTable(
            name = "os_admin_role"
            , joinColumns = {
            @JoinColumn(name = "role_id")
    }
            , inverseJoinColumns = {
            @JoinColumn(name = "admin_id")
    }
    )
    private List<OsAdmin> osAdmins;

    public OsRole() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<OsPermission> getOsPermissions() {
        return this.osPermissions;
    }

    public void setOsPermissions(List<OsPermission> osPermissions) {
        this.osPermissions = osPermissions;
    }

    public OsPermission addOsPermission(OsPermission osPermission) {
        getOsPermissions().add(osPermission);
        osPermission.setOsRole(this);

        return osPermission;
    }

    public OsPermission removeOsPermission(OsPermission osPermission) {
        getOsPermissions().remove(osPermission);
        osPermission.setOsRole(null);

        return osPermission;
    }

    public List<OsAdmin> getOsAdmins() {
        return this.osAdmins;
    }

    public void setOsAdmins(List<OsAdmin> osAdmins) {
        this.osAdmins = osAdmins;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }
}
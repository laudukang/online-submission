package me.laudukang.persistence.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the os_permission database table.
 * 
 */
@Entity
@Table(name = "os_permission")
@NamedQuery(name = "OsPermission.findAll", query = "SELECT o FROM OsPermission o")
public class OsPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String remark;

	//bi-directional many-to-one association to OsRole
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = true)
	private OsRole osRole;

	public OsPermission() {
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

	public OsRole getOsRole() {
		return this.osRole;
	}

	public void setOsRole(OsRole osRole) {
		this.osRole = osRole;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
	}
}
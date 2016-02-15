package me.laudukang.persistence.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the os_doc_admin database table.
 * 
 */
@Embeddable
public class OsDocAdminPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "doc_id")
	private int docId;

	@Column(name = "admin_id")
	private int adminId;

	public OsDocAdminPK() {
	}

	public int getDocId() {
		return this.docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public int getAdminId() {
		return this.adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OsDocAdminPK)) {
			return false;
		}
		OsDocAdminPK castOther = (OsDocAdminPK) other;
		return
				(this.docId == castOther.docId)
						&& (this.adminId == castOther.adminId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.docId;
		hash = hash * prime + this.adminId;

		return hash;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
	}
}
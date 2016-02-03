package me.laudukang.persistence.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 22:45
 * <p>Version: 1.0
 */
public class OsDocAdminPK implements Serializable {
    private int docId;
    private int adminId;

    @Column(name = "doc_id", nullable = false)
    @Id
    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    @Column(name = "admin_id", nullable = false)
    @Id
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OsDocAdminPK that = (OsDocAdminPK) o;

        if (docId != that.docId) return false;
        return adminId == that.adminId;

    }

    @Override
    public int hashCode() {
        int result = docId;
        result = 31 * result + adminId;
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }
}

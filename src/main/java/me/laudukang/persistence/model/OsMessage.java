package me.laudukang.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the os_message database table.
 */
@Entity
@Table(name = "os_message")
@NamedQuery(name = "OsMessage.findAll", query = "SELECT o FROM OsMessage o")
@EntityListeners({AuditingEntityListener.class})
public class OsMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @Column(columnDefinition = "text")
    private String content;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "post_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postTime;

    private String title;

    // bi-directional many-to-one association to OsAdmin
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = true)
    private OsAdmin osAdmin;

    // bi-directional many-to-one association to OsUser
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private OsUser osUser;

    public OsMessage() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostTime() {
        return this.postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OsAdmin getOsAdmin() {
        return this.osAdmin;
    }

    public void setOsAdmin(OsAdmin osAdmin) {
        this.osAdmin = osAdmin;
    }

    public OsUser getOsUser() {
        return this.osUser;
    }

    public void setOsUser(OsUser osUser) {
        this.osUser = osUser;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
    }
}
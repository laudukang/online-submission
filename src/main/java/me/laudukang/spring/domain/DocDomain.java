package me.laudukang.spring.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/10
 * <p>Time: 16:56
 * <p>Version: 1.0
 */
public class DocDomain {
    private int id;
    private String classification;
    private String enKeyword;
    private String enSummary;
    private String enTitle;
    private Date postTime;
    private String status;
    private Date statusTime;
    private String subject;
    private String type;
    private String zhKeyword;
    private String zhSummary;
    @NotEmpty
    private String zhTitle;

    private String fromTime;
    private String toTime;
    private int page;
    private int pageSize;
    private String sortDir;
    private String sortCol;

    private int userid;
    private int adminid;

    @NotNull(message = "未选中任何文件")
    private MultipartFile uploadFile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getEnKeyword() {
        return enKeyword;
    }

    public void setEnKeyword(String enKeyword) {
        this.enKeyword = enKeyword;
    }

    public String getEnSummary() {
        return enSummary;
    }

    public void setEnSummary(String enSummary) {
        this.enSummary = enSummary;
    }

    public String getEnTitle() {
        return enTitle;
    }

    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZhKeyword() {
        return zhKeyword;
    }

    public void setZhKeyword(String zhKeyword) {
        this.zhKeyword = zhKeyword;
    }

    public String getZhSummary() {
        return zhSummary;
    }

    public void setZhSummary(String zhSummary) {
        this.zhSummary = zhSummary;
    }

    public String getZhTitle() {
        return zhTitle;
    }

    public void setZhTitle(String zhTitle) {
        this.zhTitle = zhTitle;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page - 1;// page auto decrease 1
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public String getSortCol() {
        return sortCol;
    }

    public void setSortCol(String sortCol) {
        this.sortCol = sortCol;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getAdminid() {
        return adminid;
    }

    public void setAdminid(int adminid) {
        this.adminid = adminid;
    }

    public MultipartFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
    }
}

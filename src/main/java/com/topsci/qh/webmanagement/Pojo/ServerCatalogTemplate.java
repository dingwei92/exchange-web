package com.topsci.qh.webmanagement.Pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class ServerCatalogTemplate {
    @Id
    private int id;
    private String name;
    private String remark;
    private String status;
    private String createUser;
    private String typeuuid;
    private LocalDateTime createTime;
    private LocalDateTime invalidTime;
    private String invalidRemark;
    private String tplcode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeuuid() {
        return typeuuid;
    }

    public void setTypeuuid(String typeuuid) {
        this.typeuuid = typeuuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(LocalDateTime invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getInvalidRemark() {
        return invalidRemark;
    }

    public void setInvalidRemark(String invalidRemark) {
        this.invalidRemark = invalidRemark;
    }

    public String getTplcode() {
        return tplcode;
    }

    public void setTplcode(String tplcode) {
        this.tplcode = tplcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerCatalogTemplate that = (ServerCatalogTemplate) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}

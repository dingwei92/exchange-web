package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created by lzw.
 * 16-7-22
 */
@Document
@ApiModel("交换类别")
public class ServerType {
    @Id
    private int id;
    @ApiModelProperty("交换类别名称")
    private String typeName;
    @ApiModelProperty("是否是基础类型  N 默认给   前端不选择")
    private String isBase;
    private String state;
    private String ord;
    private String updateRole;
    private LocalDateTime updateTime;
    private String deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getIsBase() {
        return isBase;
    }

    public void setIsBase(String isBase) {
        this.isBase = isBase;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrd() {
        return ord;
    }

    public void setOrd(String ord) {
        this.ord = ord;
    }

    public String getUpdateRole() {
        return updateRole;
    }

    public void setUpdateRole(String updateRole) {
        this.updateRole = updateRole;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerType that = (ServerType) o;

        if (id != that.id) return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;
        if (isBase != null ? !isBase.equals(that.isBase) : that.isBase != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (ord != null ? !ord.equals(that.ord) : that.ord != null) return false;
        if (updateRole != null ? !updateRole.equals(that.updateRole) : that.updateRole != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (isBase != null ? isBase.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (ord != null ? ord.hashCode() : 0);
        result = 31 * result + (updateRole != null ? updateRole.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        return result;
    }
}

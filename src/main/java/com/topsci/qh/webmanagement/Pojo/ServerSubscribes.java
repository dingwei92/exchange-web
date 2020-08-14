package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by lzw.
 * 16-6-28
 */
@Document
@ApiModel("订阅服务记录")
public class ServerSubscribes {
    @Id
    private int id;
    @ApiModelProperty("订阅服务id")
    private int serverCatalogId;
    @ApiModelProperty("订阅者ID（业务系统id）")
    private int businessSystemId;
    @ApiModelProperty("审核状态  审核通过N  已删除Y  审核中A  审核未通过F ，添加默认给A")
    private String deleted;
    @ApiModelProperty(hidden = true)
    private String isFull;
    @ApiModelProperty("更新时间")
    private String upddatetime;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty(hidden = true)
    private String startTime;
    @ApiModelProperty(hidden = true)
    private String endTime;
    @ApiModelProperty("订阅参数 默认为空")
    private String parameter;
    @ApiModelProperty("启用禁用 添加默认给Y， Y N")
    private String enables;
    @ApiModelProperty(hidden = true)
    private String picuuid;

    public String getEnables() {
        return enables;
    }

    public void setEnables(String enables) {
        this.enables = enables;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getBusinessSystemId() {
        return businessSystemId;
    }

    public void setBusinessSystemId(int businessSystemId) {
        this.businessSystemId = businessSystemId;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServerCatalogId() {
        return serverCatalogId;
    }

    public void setServerCatalogId(int serverCatalogId) {
        this.serverCatalogId = serverCatalogId;
    }

    public String getIsFull() {
        return isFull;
    }

    public void setIsFull(String isFull) {
        this.isFull = isFull;
    }
    public String getUpddatetime() {
        return upddatetime;
    }

    public void setUpddatetime(String upddatetime) {
        this.upddatetime = upddatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

    public String getPicuuid() {
        return picuuid;
    }

    public void setPicuuid(String picuuid) {
        this.picuuid = picuuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerSubscribes that = (ServerSubscribes) o;

        if (id != that.id) return false;
        if (serverCatalogId != that.serverCatalogId) return false;
        if (isFull != null ? !isFull.equals(that.isFull) : that.isFull != null) return false;
        if (upddatetime != null ? !upddatetime.equals(that.upddatetime) : that.upddatetime != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (parameter != null ? !parameter.equals(that.parameter) : that.parameter != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + serverCatalogId;
        result = 31 * result + (isFull != null ? isFull.hashCode() : 0);
        result = 31 * result + (upddatetime != null ? upddatetime.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (parameter != null ? parameter.hashCode() : 0);
        return result;
    }
}

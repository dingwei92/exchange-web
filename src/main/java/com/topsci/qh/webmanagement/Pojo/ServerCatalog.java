package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by lzw.
 * 16-6-27
 */
@Document
@ApiModel("订阅接口")
public class ServerCatalog {
    @Id
    private int id;
    @ApiModelProperty("服务名称")
    private String serverName;
    @ApiModelProperty("例子")
    private String examples;
    @ApiModelProperty("是否指定系统 N  Y")
    private String specifySystem;
    @ApiModelProperty("指定系统ID")
    private String specifySystemIdList;
    @ApiModelProperty("更新时间")
    private String upddatetime;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("服务简称")
    private String serverShort;
    @ApiModelProperty("删除 N 否 Y是")
    private String deleted;
    @ApiModelProperty("业务系统ID")
    private int businessSystemId;
    private String docPath;
    @ApiModelProperty("模板id")
    private String templateId;
    @ApiModelProperty("推送类型 接口 1 ")
    private String publishType;
    @ApiModelProperty(hidden = false)
    private String dbId;

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public int getBusinessSystemId() {
        return businessSystemId;
    }

    public void setBusinessSystemId(int businessSystemId) {
        this.businessSystemId = businessSystemId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public String getSpecifySystem() {
        return specifySystem;
    }

    public void setSpecifySystem(String specifySystem) {
        this.specifySystem = specifySystem;
    }

    public String getSpecifySystemIdList() {
        return specifySystemIdList;
    }

    public void setSpecifySystemIdList(String specifySystemIdList) {
        this.specifySystemIdList = specifySystemIdList;
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

    public String getServerShort() {
        return serverShort;
    }

    public void setServerShort(String serverShort) {
        this.serverShort = serverShort;
    }

    public String getPublishType() {
        return publishType;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerCatalog that = (ServerCatalog) o;

        if (id != that.id) return false;
        if (serverName != null ? !serverName.equals(that.serverName) : that.serverName != null) return false;
        if (examples != null ? !examples.equals(that.examples) : that.examples != null) return false;
        if (specifySystem != null ? !specifySystem.equals(that.specifySystem) : that.specifySystem != null)
            return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null)
            return false;
        if (specifySystemIdList != null ? !specifySystemIdList.equals(that.specifySystemIdList) : that.specifySystemIdList != null)
            return false;
        if (upddatetime != null ? !upddatetime.equals(that.upddatetime) : that.upddatetime != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (serverShort != null ? !serverShort.equals(that.serverShort) : that.serverShort != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (serverName != null ? serverName.hashCode() : 0);
        result = 31 * result + (examples != null ? examples.hashCode() : 0);
        result = 31 * result + (specifySystem != null ? specifySystem.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (specifySystemIdList != null ? specifySystemIdList.hashCode() : 0);
        result = 31 * result + (upddatetime != null ? upddatetime.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (serverShort != null ? serverShort.hashCode() : 0);
        return result;
    }
}

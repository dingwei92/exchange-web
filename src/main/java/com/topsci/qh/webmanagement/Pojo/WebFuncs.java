package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by lzw.
 * 16-6-20
 */
@Document
@ApiModel("功能")
public class WebFuncs {
    @Id
    @ApiModelProperty("id")
    private String uuid;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("路径")
    private String path;
    @ApiModelProperty("是否为父功能  0：否 ；1：是")
    private String isParent;
    @ApiModelProperty("是否为标题  Y是  N不是")
    private String isCaption;
    @ApiModelProperty("父功能UUID，默认为0，表示顶级菜单")
    private String parentUuid;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("Y：已删除  N：未删除")
    private String deleted;
    @ApiModelProperty("顺序值")
    private int sort;
    @ApiModelProperty("Y：隐藏  N：不隐藏" )
    private String hide;
    @ApiModelProperty(hidden = true )
    private String newPage;

    public String getNewPage() {
        return newPage;
    }

    public void setNewPage(String newPage) {
        this.newPage = newPage;
    }

    public String getHide() {
        return hide;
    }

    public void setHide(String hide) {
        this.hide = hide;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIsCaption() {
        return isCaption;
    }

    public void setIsCaption(String isCaption) {
        this.isCaption = isCaption;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}

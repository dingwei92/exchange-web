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
@ApiModel("订阅字段")
public class ServerSubscribesList {
    @Id
    private int id;
    @ApiModelProperty("订阅服务id")
    private int serverSubscribesId;
    @ApiModelProperty("订阅字段id")
    private int serverCatalogListId;
    @ApiModelProperty("过滤类型 0 无过滤条件   1 有1个条件（=） 2 2个条件（范围的）")
    private int conType;
    @ApiModelProperty("值 多个会 ， 隔开")
    private String conValue;
    @ApiModelProperty("更新时间")
    private String upddatetime;

    public int getServerSubscribesId() {
        return serverSubscribesId;
    }

    public void setServerSubscribesId(int serverSubscribesId) {
        this.serverSubscribesId = serverSubscribesId;
    }

    public int getServerCatalogListId() {
        return serverCatalogListId;
    }

    public void setServerCatalogListId(int serverCatalogListId) {
        this.serverCatalogListId = serverCatalogListId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpddatetime() {
        return upddatetime;
    }

    public void setUpddatetime(String upddatetime) {
        this.upddatetime = upddatetime;
    }

    public int getConType() {
        return conType;
    }

    public void setConType(int conType) {
        this.conType = conType;
    }

    public String getConValue() {
        return conValue;
    }

    public void setConValue(String conValue) {
        this.conValue = conValue;
    }

}

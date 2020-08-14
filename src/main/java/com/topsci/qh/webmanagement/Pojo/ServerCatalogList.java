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
@ApiModel("可订阅字段")
public class ServerCatalogList {
    @Id
    private int id;
    @ApiModelProperty("元素名称")
    private String serverElementName;
    @ApiModelProperty("元素描述")
    private String serverElementDescribe;
    @ApiModelProperty("元素类型")
    private String elementDescribe;
    @ApiModelProperty(hidden = true)
    private String upddatetime;
    @ApiModelProperty("订阅服务ID")
    private int serverCatalogId;
    private int keycol;
    private int dbkey;

    public int getServerCatalogId() {
        return serverCatalogId;
    }

    public void setServerCatalogId(int serverCatalogId) {
        this.serverCatalogId = serverCatalogId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServerElementName() {
        return serverElementName;
    }

    public void setServerElementName(String serverElementName) {
        this.serverElementName = serverElementName;
    }

    public String getServerElementDescribe() {
        return serverElementDescribe;
    }

    public void setServerElementDescribe(String serverElementDescribe) {
        this.serverElementDescribe = serverElementDescribe;
    }

    public String getElementDescribe() {
        return elementDescribe;
    }

    public void setElementDescribe(String elementDescribe) {
        this.elementDescribe = elementDescribe;
    }

    public String getUpddatetime() {
        return upddatetime;
    }

    public void setUpddatetime(String upddatetime) {
        this.upddatetime = upddatetime;
    }

    public int getKeycol() {
        return keycol;
    }

    public void setKeycol(int keycol) {
        this.keycol = keycol;
    }
    public int getDbkey() {
        return dbkey;
    }

    public void setDbkey(int dbkey) {
        this.dbkey = dbkey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerCatalogList that = (ServerCatalogList) o;

        if (id != that.id) return false;
        if (serverElementName != null ? !serverElementName.equals(that.serverElementName) : that.serverElementName != null)
            return false;
        if (serverElementDescribe != null ? !serverElementDescribe.equals(that.serverElementDescribe) : that.serverElementDescribe != null)
            return false;
        if (elementDescribe != null ? !elementDescribe.equals(that.elementDescribe) : that.elementDescribe != null)
            return false;
        if (upddatetime != null ? !upddatetime.equals(that.upddatetime) : that.upddatetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (serverElementName != null ? serverElementName.hashCode() : 0);
        result = 31 * result + (serverElementDescribe != null ? serverElementDescribe.hashCode() : 0);
        result = 31 * result + (elementDescribe != null ? elementDescribe.hashCode() : 0);
        result = 31 * result + (upddatetime != null ? upddatetime.hashCode() : 0);
        return result;
    }
}

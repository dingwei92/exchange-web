package com.topsci.qh.webmanagement.Pojo;

import java.util.List;

/**
 * Created by lzw.
 * 16-7-19
 */
public class WebFuncsList {
    public String uuid;
    public String parentid;
    public String name;
    public String url;
    public String isCaption;
    public String hide;
    public int sort;
    public String newPage;
    public List<WebFuncsList> children;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsCaption() {
        return isCaption;
    }

    public void setIsCaption(String isCaption) {
        this.isCaption = isCaption;
    }

    public List<WebFuncsList> getChildren() {
        return children;
    }

    public void setChildren(List<WebFuncsList> children) {
        this.children = children;
    }

}

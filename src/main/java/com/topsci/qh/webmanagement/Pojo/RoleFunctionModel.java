package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by zlli on 2020/6/18.
 * Desc:
 * _        _     _
 * | |      | |   | |
 * ___| |_ __ _| |__ | | ___
 * / __| __/ _` | '_ \| |/ _ \
 * \__ \ || (_| | |_) | |  __/
 * |___/\__\__,_|_.__/|_|\___|
 */
/*[
        {
        title: '系统管理',
        key: '5ee70350955fcc26c86672d0',
        isParent:''
        children: [
        {
        title: '发布审核',
        key: '5ee1d78534eecc7d503a43b7',
        }
        ]
]*/
@ApiModel("角色权限")
public class RoleFunctionModel {
    private String title;
    private String key;
    private String isParent;
    private boolean checked;
    private List<RoleFunctionModel> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<RoleFunctionModel> getChildren() {
        return children;
    }

    public void setChildren(List<RoleFunctionModel> children) {
        this.children = children;
    }
}

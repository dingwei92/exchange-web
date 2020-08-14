package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by zlli on 2020/6/17.
 * Desc:
 * _        _     _
 * | |      | |   | |
 * ___| |_ __ _| |__ | | ___
 * / __| __/ _` | '_ \| |/ _ \
 * \__ \ || (_| | |_) | |  __/
 * |___/\__\__,_|_.__/|_|\___|
 */
@ApiModel("角色 功能对应")
public class RolesFunsModel {
    @ApiModelProperty("角色")
    private WebRoles webRoles;
    @ApiModelProperty("功能ID")
    private List<String> funIds;

    public WebRoles getWebRoles() {
        return webRoles;
    }

    public void setWebRoles(WebRoles webRoles) {
        this.webRoles = webRoles;
    }

    public List<String> getFunIds() {
        return funIds;
    }

    public void setFunIds(List<String> funIds) {
        this.funIds = funIds;
    }
}

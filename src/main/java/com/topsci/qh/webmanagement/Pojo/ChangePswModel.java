package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zlli on 2020/6/12.
 * Desc:
 * _        _     _
 * | |      | |   | |
 * ___| |_ __ _| |__ | | ___
 * / __| __/ _` | '_ \| |/ _ \
 * \__ \ || (_| | |_) | |  __/
 * |___/\__\__,_|_.__/|_|\___|
 */
@ApiModel("用户")
public class ChangePswModel {
    @ApiModelProperty("uuid")
    private String uuid;
    @ApiModelProperty("psw1")
    private String psw1;
    @ApiModelProperty("psw2")
    private String psw2;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPsw1() {
        return psw1;
    }

    public void setPsw1(String psw1) {
        this.psw1 = psw1;
    }

    public String getPsw2() {
        return psw2;
    }

    public void setPsw2(String psw2) {
        this.psw2 = psw2;
    }
}

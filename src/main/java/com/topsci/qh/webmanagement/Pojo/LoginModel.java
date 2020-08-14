package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zlli on 2020/6/24.
 * Desc:
 * _        _     _
 * | |      | |   | |
 * ___| |_ __ _| |__ | | ___
 * / __| __/ _` | '_ \| |/ _ \
 * \__ \ || (_| | |_) | |  __/
 * |___/\__\__,_|_.__/|_|\___|
 */
@ApiModel("登录")
public class LoginModel {
    @ApiModelProperty("用户名")
    private String name;
    @ApiModelProperty("密码")
    private String psw;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }
}

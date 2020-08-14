package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
@ApiModel("订阅详情")
public class SubDetail {
    @ApiModelProperty("id")
    private int id;
    @ApiModelProperty("业务详情ID")
    private int bsid;
    @ApiModelProperty("订阅者")
    private String subName;
    @ApiModelProperty("服务名称")
    private String serviceName;
    @ApiModelProperty("审核状态  审核通过N  已删除Y  审核中A  审核未通过F")
    private String status;
    @ApiModelProperty("启用禁用 Y N")
    private String enable;
    @ApiModelProperty("申请人")
    private String subUser;
    @ApiModelProperty("申请人ID")
    private int subUserId;
    @ApiModelProperty("联系方式")
    private String tel;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("备注")
    private String remark;


    public int getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(int subUserId) {
        this.subUserId = subUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBsid() {
        return bsid;
    }

    public void setBsid(int bsid) {
        this.bsid = bsid;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getSubUser() {
        return subUser;
    }

    public void setSubUser(String subUser) {
        this.subUser = subUser;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

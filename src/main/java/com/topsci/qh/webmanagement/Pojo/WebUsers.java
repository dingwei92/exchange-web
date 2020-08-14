package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created by lzw.
 * 16-6-20
 */
@Document
@ApiModel("用户")
public class WebUsers {
    @Id
    @ApiModelProperty("id")
    private String uuid;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("登录名")
    private String loginName;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("状态1 启用 0 禁用")
    private String statu;
    @ApiModelProperty("密码")
    private String passwd;
    @ApiModelProperty("电话")
    private String phoneNum;
    @ApiModelProperty(hidden = true)
    private String organizationId;
    @ApiModelProperty("最近登录时间")
    private LocalDateTime recentLogin;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("业务名称")
    private String businessName;
    @ApiModelProperty("业务id")
    private int businessId;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty(hidden = true)
    private String ssoId;
    @ApiModelProperty("删除 Y N")
    private String deleted;
    @ApiModelProperty(hidden = true)
    private String userid;
    @ApiModelProperty(hidden = true)
    private String uname;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String uid) {
        this.userid = uid;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public LocalDateTime getRecentLogin() {
        return recentLogin;
    }

    public void setRecentLogin(LocalDateTime recentLogin) {
        this.recentLogin = recentLogin;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}

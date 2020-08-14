package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by lzw.
 * 16-7-23
 */
@Document
public class ServerSubscribeApply {
    @Id
    private int id;
    @ApiModelProperty("订阅服务记录ID")
    private int sid;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty(hidden = true)
    private String userUuid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

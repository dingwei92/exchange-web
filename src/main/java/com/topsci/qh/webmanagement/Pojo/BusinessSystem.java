package com.topsci.qh.webmanagement.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created by lzw.
 * 16-6-25
 */
@Document
@ApiModel("业务系统")
public class BusinessSystem {
    @Id
    private int id;
    @ApiModelProperty("服务名称")
    private String systemName;
    @ApiModelProperty("业务id")
    private String systemShort;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("删除 N Y")
    private String deleted;
    @ApiModelProperty("业务类别")
    private int typeId;
    @ApiModelProperty(hidden = false)
    private int kafkaTopicId;
    @ApiModelProperty("最后一次连接")
    private LocalDateTime lastConnect;
    @ApiModelProperty("用户")
    private String adminName;
    @ApiModelProperty("邮箱")
    private String adminEmail;
    @ApiModelProperty("手机")
    private String adminMobile;
    @ApiModelProperty("token")
    private String token;

    public int getKafkaTopicId() {
        return kafkaTopicId;
    }

    public void setKafkaTopicId(int kafkaTopicId) {
        this.kafkaTopicId = kafkaTopicId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getSystemShort() {
        return systemShort;
    }

    public void setSystemShort(String systemShort) {
        this.systemShort = systemShort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getLastConnect() {
        return lastConnect;
    }

    public void setLastConnect(LocalDateTime lastConnect) {
        this.lastConnect = lastConnect;
    }
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminMobile() {
        return adminMobile;
    }

    public void setAdminMobile(String adminMobile) {
        this.adminMobile = adminMobile;
    }
    
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}

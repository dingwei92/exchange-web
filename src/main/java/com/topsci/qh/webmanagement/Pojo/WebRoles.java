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
@ApiModel("角色")
public class WebRoles {
    @Id
    private String uuid;
    @ApiModelProperty("名称")
    private String roleName;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty(hidden = true)
    private LocalDateTime createTime;
    @ApiModelProperty("删除 Y N")
    private String deleted;
    @ApiModelProperty("是否为超级用户  Y是  N不是")
    private String superUser;
    @ApiModelProperty("是否为正式用户，Y为是，N为否")
    private String normalStatus;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getSuperUser() {
        return superUser;
    }

    public void setSuperUser(String superUser) {
        this.superUser = superUser;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getNormalStatus() {
        return normalStatus;
    }

    public void setNormalStatus(String normalStatus) {
        this.normalStatus = normalStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebRoles webRoles = (WebRoles) o;

        if (uuid != null ? !uuid.equals(webRoles.uuid) : webRoles.uuid != null) return false;
        if (roleName != null ? !roleName.equals(webRoles.roleName) : webRoles.roleName != null) return false;
        if (remark != null ? !remark.equals(webRoles.remark) : webRoles.remark != null) return false;
        if (createTime != null ? !createTime.equals(webRoles.createTime) : webRoles.createTime != null) return false;
        if (deleted != null ? !deleted.equals(webRoles.deleted) : webRoles.deleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        return result;
    }
}

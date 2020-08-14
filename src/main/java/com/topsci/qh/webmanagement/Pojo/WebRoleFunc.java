package com.topsci.qh.webmanagement.Pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

/**
 * Created by lzw.
 * 16-6-20
 */
@Document
public class WebRoleFunc {
    @Id
    private String uuid;
    private String roleUuid;
    private String funcUuid;
    private LocalDateTime updatedTime;
    private String deleted;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRoleUuid() {
        return roleUuid;
    }

    public void setRoleUuid(String roleUuid) {
        this.roleUuid = roleUuid;
    }

    public String getFuncUuid() {
        return funcUuid;
    }

    public void setFuncUuid(String funcUuid) {
        this.funcUuid = funcUuid;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebRoleFunc that = (WebRoleFunc) o;

        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (roleUuid != null ? !roleUuid.equals(that.roleUuid) : that.roleUuid != null) return false;
        if (funcUuid != null ? !funcUuid.equals(that.funcUuid) : that.funcUuid != null) return false;
        if (updatedTime != null ? !updatedTime.equals(that.updatedTime) : that.updatedTime != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (roleUuid != null ? roleUuid.hashCode() : 0);
        result = 31 * result + (funcUuid != null ? funcUuid.hashCode() : 0);
        result = 31 * result + (updatedTime != null ? updatedTime.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        return result;
    }
}

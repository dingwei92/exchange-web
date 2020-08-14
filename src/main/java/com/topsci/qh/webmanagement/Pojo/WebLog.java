package com.topsci.qh.webmanagement.Pojo;

import java.sql.Timestamp;

/**
 * Created by lzw.
 * 16-6-20
 */
public class WebLog {
    private String uuid;
    private String loginName;
    private String dataNew;
    private String dataOld;
    private Timestamp operationTime;
    private String operationContent;
    private String userUuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getDataNew() {
        return dataNew;
    }

    public void setDataNew(String dataNew) {
        this.dataNew = dataNew;
    }

    public String getDataOld() {
        return dataOld;
    }

    public void setDataOld(String dataOld) {
        this.dataOld = dataOld;
    }
    public Timestamp getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Timestamp operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent;
    }
    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebLog webLog = (WebLog) o;

        if (uuid != null ? !uuid.equals(webLog.uuid) : webLog.uuid != null) return false;
        if (loginName != null ? !loginName.equals(webLog.loginName) : webLog.loginName != null) return false;
        if (dataNew != null ? !dataNew.equals(webLog.dataNew) : webLog.dataNew != null) return false;
        if (dataOld != null ? !dataOld.equals(webLog.dataOld) : webLog.dataOld != null) return false;
        if (operationTime != null ? !operationTime.equals(webLog.operationTime) : webLog.operationTime != null)
            return false;
        if (operationContent != null ? !operationContent.equals(webLog.operationContent) : webLog.operationContent != null)
            return false;
        if (userUuid != null ? !userUuid.equals(webLog.userUuid) : webLog.userUuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (dataNew != null ? dataNew.hashCode() : 0);
        result = 31 * result + (dataOld != null ? dataOld.hashCode() : 0);
        result = 31 * result + (operationTime != null ? operationTime.hashCode() : 0);
        result = 31 * result + (operationContent != null ? operationContent.hashCode() : 0);
        result = 31 * result + (userUuid != null ? userUuid.hashCode() : 0);
        return result;
    }
}

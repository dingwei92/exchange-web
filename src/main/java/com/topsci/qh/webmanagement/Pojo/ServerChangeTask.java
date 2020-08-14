package com.topsci.qh.webmanagement.Pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by lzw.
 * 16-6-28
 */
@Document
public class ServerChangeTask {
    @Id
    private int id;
    private String changeType;
    private int serverChangeId;
    private String dealState;
    private String lastdate;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public int getServerChangeId() {
        return serverChangeId;
    }

    public void setServerChangeId(int serverChangeId) {
        this.serverChangeId = serverChangeId;
    }

    public String getDealState() {
        return dealState;
    }

    public void setDealState(String dealState) {
        this.dealState = dealState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerChangeTask that = (ServerChangeTask) o;

        if (id != that.id) return false;
        if (serverChangeId != that.serverChangeId) return false;
        if (changeType != null ? !changeType.equals(that.changeType) : that.changeType != null) return false;
        if (dealState != null ? !dealState.equals(that.dealState) : that.dealState != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (changeType != null ? changeType.hashCode() : 0);
        result = 31 * result + serverChangeId;
        result = 31 * result + (dealState != null ? dealState.hashCode() : 0);
        return result;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }
}

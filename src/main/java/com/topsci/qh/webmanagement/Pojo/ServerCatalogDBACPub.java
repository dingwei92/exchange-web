package com.topsci.qh.webmanagement.Pojo;

import java.sql.Timestamp;

public class ServerCatalogDBACPub {
    private String uuid;
    private String catalogid;
    private Timestamp starttime;
    private Timestamp endtime;
    private String status;
    private Timestamp createtime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getCatalogid() {
        return catalogid;
    }

    public void setCatalogid(String catalogid) {
        this.catalogid = catalogid;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerCatalogDBACPub that = (ServerCatalogDBACPub) o;

        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (catalogid != null ? !catalogid.equals(that.catalogid) : that.catalogid != null) return false;
        if (starttime != null ? !starttime.equals(that.starttime) : that.starttime != null) return false;
        if (endtime != null ? !endtime.equals(that.endtime) : that.endtime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (catalogid != null ? catalogid.hashCode() : 0);
        result = 31 * result + (starttime != null ? starttime.hashCode() : 0);
        result = 31 * result + (endtime != null ? endtime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        return result;
    }
}

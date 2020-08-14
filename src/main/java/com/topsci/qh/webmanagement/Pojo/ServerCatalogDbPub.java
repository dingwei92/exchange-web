package com.topsci.qh.webmanagement.Pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document
public class ServerCatalogDbPub {
    @Id
    private String uuid;
    private String dbId;
    private String tableName;
    private String tableWhere;
    private String selectType;
    private String selectSql;
    private String priority;
    private String dateCol;
    private String dateColType;
    private String cols;
    private Integer lastCount;
    private Integer allCount;
    private Timestamp lastRun;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getLastCount() {
        return lastCount;
    }

    public void setLastCount(Integer lastCount) {
        this.lastCount = lastCount;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableWhere() {
        return tableWhere;
    }

    public void setTableWhere(String tableWhere) {
        this.tableWhere = tableWhere;
    }

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    public String getSelectSql() {
        return selectSql;
    }

    public void setSelectSql(String selectSql) {
        this.selectSql = selectSql;
    }

    public String getDateCol() {
        return dateCol;
    }

    public void setDateCol(String dateCol) {
        this.dateCol = dateCol;
    }

    public String getDateColType() {
        return dateColType;
    }

    public void setDateColType(String dateColType) {
        this.dateColType = dateColType;
    }
    public String getCols() {
        return cols;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }

    public Timestamp getLastRun() {
        return lastRun;
    }

    public void setLastRun(Timestamp lastRun) {
        this.lastRun = lastRun;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerCatalogDbPub that = (ServerCatalogDbPub) o;

        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (dbId != null ? !dbId.equals(that.dbId) : that.dbId != null) return false;
        if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) return false;
        if (tableWhere != null ? !tableWhere.equals(that.tableWhere) : that.tableWhere != null) return false;
        if (selectType != null ? !selectType.equals(that.selectType) : that.selectType != null) return false;
        if (selectSql != null ? !selectSql.equals(that.selectSql) : that.selectSql != null) return false;
        if (dateCol != null ? !dateCol.equals(that.dateCol) : that.dateCol != null) return false;
        if (cols != null ? !cols.equals(that.cols) : that.cols != null) return false;
        if (lastRun != null ? !lastRun.equals(that.lastRun) : that.lastRun != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (dbId != null ? dbId.hashCode() : 0);
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        result = 31 * result + (tableWhere != null ? tableWhere.hashCode() : 0);
        result = 31 * result + (selectType != null ? selectType.hashCode() : 0);
        result = 31 * result + (selectSql != null ? selectSql.hashCode() : 0);
        result = 31 * result + (dateCol != null ? dateCol.hashCode() : 0);
        result = 31 * result + (cols != null ? cols.hashCode() : 0);
        result = 31 * result + (lastRun != null ? lastRun.hashCode() : 0);
        return result;
    }
}

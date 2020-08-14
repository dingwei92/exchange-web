package com.topsci.qh.webmanagement.Pojo;

import java.sql.Timestamp;

public class ServerCatalogDBinfo {
    private String uuid;
    private String sourcename;
    private String dbtype;
    private String dbip;
    private String dbport;
    private String dbname;
    private String username;
    private String pwd;
    private String status;
    private Timestamp createTime;

    private String factory;
    private String factoryContact;
    private String factoryContactInformation;

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    public String getDbip() {
        return dbip;
    }

    public void setDbip(String dbip) {
        this.dbip = dbip;
    }

    public String getDbport() {
        return dbport;
    }

    public void setDbport(String dbport) {
        this.dbport = dbport;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getFactoryContact() {
        return factoryContact;
    }

    public void setFactoryContact(String factoryContact) {
        this.factoryContact = factoryContact;
    }
    public String getFactoryContactInformation() {
        return factoryContactInformation;
    }

    public void setFactoryContactInformation(String factoryContactInformation) {
        this.factoryContactInformation = factoryContactInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerCatalogDBinfo that = (ServerCatalogDBinfo) o;

        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (dbtype != null ? !dbtype.equals(that.dbtype) : that.dbtype != null) return false;
        if (dbip != null ? !dbip.equals(that.dbip) : that.dbip != null) return false;
        if (dbport != null ? !dbport.equals(that.dbport) : that.dbport != null) return false;
        if (dbname != null ? !dbname.equals(that.dbname) : that.dbname != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (pwd != null ? !pwd.equals(that.pwd) : that.pwd != null) return false;
        if (factory != null ? !factory.equals(that.factory) : that.factory != null) return false;
        if (factoryContact != null ? !factoryContact.equals(that.factoryContact) : that.factoryContact != null) return false;
        if (factoryContactInformation != null ? !factoryContactInformation.equals(that.factoryContactInformation) : that.factoryContactInformation != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (dbtype != null ? dbtype.hashCode() : 0);
        result = 31 * result + (dbip != null ? dbip.hashCode() : 0);
        result = 31 * result + (dbport != null ? dbport.hashCode() : 0);
        result = 31 * result + (dbname != null ? dbname.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (pwd != null ? pwd.hashCode() : 0);
        result = 31 * result + (factory != null ? factory.hashCode() : 0);
        result = 31 * result + (factoryContact != null ? factoryContact.hashCode() : 0);
        result = 31 * result + (factoryContactInformation != null ? factoryContactInformation.hashCode() : 0);
        return result;
    }
}

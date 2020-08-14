package com.topsci.qh.webmanagement.Pojo;

/**
 * Created by lzw.
 * 16-7-27
 */
public class ServerMessageDetail {
    private String id;
    private String msgsn;
    private String msgsenttime;
    private String formsysid;
    private String tosysid;
    private String msgoid;
    private String msgotime;
    private String msgtype;
    private int msgnum;
    private int msgcount;
    private String lastdate;
    private String msgdata;
    private String sessionid;
    private String msgcode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgsn() {
        return msgsn;
    }

    public void setMsgsn(String msgsn) {
        this.msgsn = msgsn;
    }

    public String getMsgsenttime() {
        return msgsenttime;
    }

    public void setMsgsenttime(String msgsenttime) {
        this.msgsenttime = msgsenttime;
    }

    public String getFormsysid() {
        return formsysid;
    }

    public void setFormsysid(String formsysid) {
        this.formsysid = formsysid;
    }

    public String getTosysid() {
        return tosysid;
    }

    public void setTosysid(String tosysid) {
        this.tosysid = tosysid;
    }

    public String getMsgoid() {
        return msgoid;
    }

    public void setMsgoid(String msgoid) {
        this.msgoid = msgoid;
    }

    public String getMsgotime() {
        return msgotime;
    }

    public void setMsgotime(String msgotime) {
        this.msgotime = msgotime;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public int getMsgnum() {
        return msgnum;
    }

    public void setMsgnum(int msgnum) {
        this.msgnum = msgnum;
    }

    public int getMsgcount() {
        return msgcount;
    }

    public void setMsgcount(int msgcount) {
        this.msgcount = msgcount;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public String getMsgdata() {
        return msgdata;
    }

    public void setMsgdata(String msgdata) {
        this.msgdata = msgdata;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(String msgcode) {
        this.msgcode = msgcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerMessageDetail that = (ServerMessageDetail) o;

        if (id != that.id) return false;
        if (msgnum != that.msgnum) return false;
        if (msgcount != that.msgcount) return false;
        if (msgsn != null ? !msgsn.equals(that.msgsn) : that.msgsn != null) return false;
        if (msgsenttime != null ? !msgsenttime.equals(that.msgsenttime) : that.msgsenttime != null) return false;
        if (formsysid != null ? !formsysid.equals(that.formsysid) : that.formsysid != null) return false;
        if (tosysid != null ? !tosysid.equals(that.tosysid) : that.tosysid != null) return false;
        if (msgoid != null ? !msgoid.equals(that.msgoid) : that.msgoid != null) return false;
        if (msgotime != null ? !msgotime.equals(that.msgotime) : that.msgotime != null) return false;
        if (msgtype != null ? !msgtype.equals(that.msgtype) : that.msgtype != null) return false;
        if (lastdate != null ? !lastdate.equals(that.lastdate) : that.lastdate != null) return false;
        if (msgdata != null ? !msgdata.equals(that.msgdata) : that.msgdata != null) return false;
        if (sessionid != null ? !sessionid.equals(that.sessionid) : that.sessionid != null) return false;
        if (msgcode != null ? !msgcode.equals(that.msgcode) : that.msgcode != null) return false;

        return true;
    }

}

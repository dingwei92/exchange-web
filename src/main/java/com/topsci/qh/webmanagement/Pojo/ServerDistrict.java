package com.topsci.qh.webmanagement.Pojo;

/**
 * Created by lzwei on 2017/3/12.
 */
public class ServerDistrict {
    private int id;
    private String bh;
    private String mc;
    private String dj;
    private String sj;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getSj() {
        return sj;
    }

    public void setSj(String sj) {
        this.sj = sj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerDistrict that = (ServerDistrict) o;

        if (bh != null ? !bh.equals(that.bh) : that.bh != null) return false;
        if (mc != null ? !mc.equals(that.mc) : that.mc != null) return false;
        if (dj != null ? !dj.equals(that.dj) : that.dj != null) return false;
        if (sj != null ? !sj.equals(that.sj) : that.sj != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result =  0;
        result = 31 * result + (bh != null ? bh.hashCode() : 0);
        result = 31 * result + (mc != null ? mc.hashCode() : 0);
        result = 31 * result + (dj != null ? dj.hashCode() : 0);
        result = 31 * result + (sj != null ? sj.hashCode() : 0);
        return result;
    }
}

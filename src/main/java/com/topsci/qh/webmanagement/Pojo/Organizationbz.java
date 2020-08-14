package com.topsci.qh.webmanagement.Pojo;

import java.sql.Timestamp;

/**
 * Created by lzw on 2017/4/18.
 */
public class Organizationbz {
    private int indexId;
    private String orgId;
    private String orgName;
    private String parentId;
    private String tel;
    private String upid0;
    private String upid1;
    private String upid2;
    private String upid3;
    private String deptCode;
    private String deptTypeCode;
    private String ecoTypeCode;
    private String saniTypeCode;
    private String address;
    private String govRelation;
    private String areaCode;
    private String countyAreaCode;
    private String communityAreaCode;
	private Timestamp updateTime;
	private String source;
	private Integer sourceType;
	private String areaId;

    public int getIndexId() {
        return indexId;
    }

    public void setIndexId(int indexId) {
        this.indexId = indexId;
    }
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUpid0() {
        return upid0;
    }

    public void setUpid0(String upid0) {
        this.upid0 = upid0;
    }

    public String getUpid1() {
        return upid1;
    }

    public void setUpid1(String upid1) {
        this.upid1 = upid1;
    }

    public String getUpid2() {
        return upid2;
    }

    public void setUpid2(String upid2) {
        this.upid2 = upid2;
    }

    public String getUpid3() {
        return upid3;
    }

    public void setUpid3(String upid3) {
        this.upid3 = upid3;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptTypeCode() {
        return deptTypeCode;
    }

    public void setDeptTypeCode(String deptTypeCode) {
        this.deptTypeCode = deptTypeCode;
    }
    public String getEcoTypeCode() {
        return ecoTypeCode;
    }

    public void setEcoTypeCode(String ecoTypeCode) {
        this.ecoTypeCode = ecoTypeCode;
    }

    public String getSaniTypeCode() {
        return saniTypeCode;
    }

    public void setSaniTypeCode(String saniTypeCode) {
        this.saniTypeCode = saniTypeCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGovRelation() {
        return govRelation;
    }

    public void setGovRelation(String govRelation) {
        this.govRelation = govRelation;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    public String getCountyAreaCode() {
        return countyAreaCode;
    }

    public void setCountyAreaCode(String countyAreaCode) {
        this.countyAreaCode = countyAreaCode;
    }

    public String getCommunityAreaCode() {
        return communityAreaCode;
    }

    public void setCommunityAreaCode(String communityAreaCode) {
        this.communityAreaCode = communityAreaCode;
    }

    
    public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organizationbz that = (Organizationbz) o;

        if (indexId != that.indexId) return false;
        if (orgId != null ? !orgId.equals(that.orgId) : that.orgId != null) return false;
        if (orgName != null ? !orgName.equals(that.orgName) : that.orgName != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (tel != null ? !tel.equals(that.tel) : that.tel != null) return false;
        if (upid0 != null ? !upid0.equals(that.upid0) : that.upid0 != null) return false;
        if (upid1 != null ? !upid1.equals(that.upid1) : that.upid1 != null) return false;
        if (upid2 != null ? !upid2.equals(that.upid2) : that.upid2 != null) return false;
        if (upid3 != null ? !upid3.equals(that.upid3) : that.upid3 != null) return false;
        if (deptCode != null ? !deptCode.equals(that.deptCode) : that.deptCode != null) return false;
        if (deptTypeCode != null ? !deptTypeCode.equals(that.deptTypeCode) : that.deptTypeCode != null) return false;
        if (ecoTypeCode != null ? !ecoTypeCode.equals(that.ecoTypeCode) : that.ecoTypeCode != null) return false;
        if (saniTypeCode != null ? !saniTypeCode.equals(that.saniTypeCode) : that.saniTypeCode != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (govRelation != null ? !govRelation.equals(that.govRelation) : that.govRelation != null) return false;
        if (areaCode != null ? !areaCode.equals(that.areaCode) : that.areaCode != null) return false;
        if (countyAreaCode != null ? !countyAreaCode.equals(that.countyAreaCode) : that.countyAreaCode != null)
            return false;
        if (communityAreaCode != null ? !communityAreaCode.equals(that.communityAreaCode) : that.communityAreaCode != null)
            return false;
        

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (indexId ^ (indexId >>> 32));
        result = 31 * result + (orgId != null ? orgId.hashCode() : 0);
        result = 31 * result + (orgName != null ? orgName.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (upid0 != null ? upid0.hashCode() : 0);
        result = 31 * result + (upid1 != null ? upid1.hashCode() : 0);
        result = 31 * result + (upid2 != null ? upid2.hashCode() : 0);
        result = 31 * result + (upid3 != null ? upid3.hashCode() : 0);
        result = 31 * result + (deptCode != null ? deptCode.hashCode() : 0);
        result = 31 * result + (deptTypeCode != null ? deptTypeCode.hashCode() : 0);
        result = 31 * result + (ecoTypeCode != null ? ecoTypeCode.hashCode() : 0);
        result = 31 * result + (saniTypeCode != null ? saniTypeCode.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (govRelation != null ? govRelation.hashCode() : 0);
        result = 31 * result + (areaCode != null ? areaCode.hashCode() : 0);
        result = 31 * result + (countyAreaCode != null ? countyAreaCode.hashCode() : 0);
        result = 31 * result + (communityAreaCode != null ? communityAreaCode.hashCode() : 0);
        return result;
    }
}

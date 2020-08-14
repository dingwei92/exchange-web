package com.topsci.qh.webmanagement.Pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class ORGANIZATIONBZREG implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2892585561133520581L;

	private Long indexId;
	
	private String orgId;
	
	private String orgName;
	
//	@Column(name = "PARENT_ID", nullable = true, insertable = true, updatable = true )
//	private String parentId;
	
	private ORGANIZATIONBZREG parent;
	
	private String tel;
	
	private String deptCode;
	
	private String deptTypeCode;
	
	private String ecoTypeCode;
	
	private String saniTypeCode;
	
	private String address;
	
	private String govRelation;
	
	private String areaCode;
	
	private String countyAreaCode;
	
	private String communityAreaCode;
	
	private String source;
	
	private Timestamp insertDate;
	
	private Integer reviewStatus;
	
	private Integer sourceType;
	
	private Timestamp reviewTime;
	
	private String areaId;

	public Long getIndexId() {
		return indexId;
	}

	public void setIndexId(Long indexId) {
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

	public ORGANIZATIONBZREG getParent() {
		return parent;
	}

	public void setParent(ORGANIZATIONBZREG parent) {
		this.parent = parent;
	}

	public void setSaniTypeCode(String saniTypeCode) {
		this.saniTypeCode = saniTypeCode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public void setSanitTypeCode(String saniTypeCode) {
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Timestamp getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public Timestamp getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Timestamp reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	
	
}

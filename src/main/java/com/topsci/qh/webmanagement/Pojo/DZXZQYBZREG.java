package com.topsci.qh.webmanagement.Pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class DZXZQYBZREG implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3136823244616733001L;

	private Long id;
	
	private String bh;
	
	private String mc;
	
	private String dj;
	
	private DZXZQYBZREG sj;
	
	private String source;
	
	private Timestamp insertDate;
	
	private Integer reviewStatus;

	private Integer sourceType;
	
	private Timestamp reviewTime;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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


	public DZXZQYBZREG getSj() {
		return sj;
	}

	public void setSj(DZXZQYBZREG sj) {
		this.sj = sj;
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
	
}

package com.topsci.qh.webmanagement.Pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class DZXZQYBZ implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3136823244616733001L;

	private Long id;
	
	private String bh;
	
	private String mc;
	
	private String dj;
	
	private DZXZQYBZ sj;
	
	private Timestamp updateTime;
	
	private String source;
	
	private Integer sourceType;
	
	
	public DZXZQYBZ() {
		super();
	}

	public DZXZQYBZ(Long id, String bh, String mc, String dj) {
		super();
		this.id = id;
		this.bh = bh;
		this.mc = mc;
		this.dj = dj;
	}

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

	public DZXZQYBZ getSj() {
		return sj;
	}

	public void setSj(DZXZQYBZ sj) {
		this.sj = sj;
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


	
}

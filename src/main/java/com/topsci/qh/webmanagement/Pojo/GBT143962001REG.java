package com.topsci.qh.webmanagement.Pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class GBT143962001REG implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -390030642818630524L;

	private Long id;
	
	private String name;
	
	private String code;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

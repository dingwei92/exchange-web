package com.topsci.qh.webmanagement.Pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class GBICD9 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7971227617774723715L;

	private Long id;
	
	private String name;
	
	private String code;
	
	private String description;
	
	private Timestamp updateTime;
	
	private String source;
	
	private Integer sourceType;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
	
}

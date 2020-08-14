package com.topsci.qh.webmanagement.Pojo;

import java.io.Serializable;

/**
 * 经济类型
 * @author Administrator
 *
 */
public class GBT12402 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 684873322837440363L;

	private Long id;
	
	private String name;
	
	private String code;
	
	private String description;

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
	
	
	
}

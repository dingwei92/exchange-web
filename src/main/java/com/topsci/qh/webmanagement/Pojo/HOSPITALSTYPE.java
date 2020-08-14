package com.topsci.qh.webmanagement.Pojo;

import java.io.Serializable;

/**
 * 医疗机构类别
 * @author Administrator
 *
 */
public class HOSPITALSTYPE implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 684873322837440363L;

	private Long id;
	
	private String name;
	
	private String code;
	
	private String remark;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

package com.topsci.qh.webmanagement.Tools;

import java.util.HashMap;
import java.util.Map;

public class ReslutParent {
	/**
	 * 没有设置回复数据
	 */
	private final static String NO_DATA = "not set data error!";
	private final static String SUCCESS = "1";
	private final static String ERROR = "0";
	private final static String NO_AUTH = "-1";

	/**
	 * 返回实际内容
	 */
	private Object content;
	
	/**
	 * 错误内容
	 */
	private Object  error;
	
	/**
	 * 权限内容
	 */
	private Object  auth;

	public Object  getError() {
		return error;
	}

	public void setError(Object  error) {
		this.error = error;
	}

	public void setAuth(Object  auth) {
		this.auth = auth;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
	public Map<String,Object> toBack(){
		Map<String,Object> map = new HashMap<String, Object>();
		if(this.auth != null){
			map.put("c", this.auth);
			map.put("f", NO_AUTH);
			return map;
		}

		if(this.error != null){
			map.put("c", this.error);
			map.put("f", ERROR);
			return map;
		}

		map.put("c", this.getContent());
		map.put("f", SUCCESS);
		return map;
	}

}

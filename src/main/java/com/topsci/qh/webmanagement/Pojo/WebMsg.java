package com.topsci.qh.webmanagement.Pojo;

import java.sql.Timestamp;

/**
 * @ClassName: WebMsg  
 * @Description: TODO
 * @author tgeng
 * @date 2017-1-10 下午1:45:08  
 *
 */
public class WebMsg {
    private int id;
    private String theme;
    private String content;
    private Timestamp createTime;
    private String createUserId;
    private String status;

    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public String getTheme() {
    	return theme;
    }
    
    public void setTheme(String theme) {
    	this.theme = theme;
    }
    public String getContent() {
    	return content;
    }
    
    public void setContent(String content) {
    	this.content = content;
    }
    
    public Timestamp getCreateTime() {
    	return createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
    	this.createTime = createTime;
    }
    
    public String getCreateUserId() {
    	return createUserId;
    }
    
    public void setCreateUserId(String createUserId) {
    	this.createUserId = createUserId;
    }
    
    public String getStatus() {
    	return status;
    }
    
    public void setStatus(String status) {
    	this.status = status;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebMsg that = (WebMsg) o;

        if (id != that.id) return false;
        if (theme != null ? !theme.equals(that.theme) : that.theme != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (createUserId != null ? !createUserId.equals(that.createUserId) : that.createUserId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }


	@Override
    public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (theme != null ? theme.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createUserId != null ? createUserId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}

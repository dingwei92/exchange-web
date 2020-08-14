package com.topsci.qh.webmanagement.Pojo;

import java.sql.Timestamp;

/**
 * @ClassName: WebMsg  
 * @Description: TODO
 * @author tgeng
 * @date 2017-1-10 下午1:45:08  
 *
 */
public class WebMsgDeal {
    private long id;
    private String frontUserId;
    private long msgId;
    private Timestamp dealTime;

    public long getId() {
    	return id;
    }
    
    public void setId(long id) {
    	this.id = id;
    }
    
    public String getFrontUserId() {
		return frontUserId;
	}

	public void setFrontUserId(String frontUserId) {
		this.frontUserId = frontUserId;
	}
	
	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public Timestamp getDealTime() {
		return dealTime;
	}

	public void setDealTime(Timestamp dealTime) {
		this.dealTime = dealTime;
	}

    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebMsgDeal that = (WebMsgDeal) o;

        if (id != that.id) return false;
        if (frontUserId != null ? !frontUserId.equals(that.frontUserId) : that.frontUserId != null) return false;
        if (msgId != that.msgId) return false;
        if (dealTime != null ? !dealTime.equals(that.dealTime) : that.dealTime != null) return false;

        return true;
    }


	
	@Override
    public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (frontUserId != null ? frontUserId.hashCode() : 0);
        result = 31 * result + (int) (msgId ^ (msgId >>> 32));
        result = 31 * result + (dealTime != null ? dealTime.hashCode() : 0);
        return result;
    }
}

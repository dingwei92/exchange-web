package com.topsci.qh.webmanagement.Pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * Created by wwhao.
 * 16-6-25
 */
 @Document
public class RelUserBusiness {
	@Id
    private int id;
    private String userId;
    private int businessId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getBusinessId() {
		return businessId;
	}

	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelUserBusiness other = (RelUserBusiness) obj;
		if (businessId != other.businessId)
			return false;
		if (id != other.id)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + businessId;
		result = prime * result + id;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
}

package com.topsci.qh.webmanagement.Pojo;

/**
 * Created by lzw.
 * 16-6-20
 */
public class ServerHistoryApply {
    private int id;
    private String webUserId;
    private String serverSubscribesId;
    private String parameter;
    private String createTime;
    private String status;
    private String startDate;
    private String endDate;
	private String areacode;
	private String organcode;
	private String idcard;
	private String healthid;
	private String phrid;

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getOrgancode() {
		return organcode;
	}

	public void setOrgancode(String organcode) {
		this.organcode = organcode;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getHealthid() {
		return healthid;
	}

	public void setHealthid(String healthid) {
		this.healthid = healthid;
	}

	public String getPhrid() {
		return phrid;
	}

	public void setPhrid(String phrid) {
		this.phrid = phrid;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWebUserId() {
		return webUserId;
	}

	public void setWebUserId(String webUserId) {
		this.webUserId = webUserId;
	}

	public String getServerSubscribesId() {
		return serverSubscribesId;
	}

	public void setServerSubscribesId(String serverSubscribesId) {
		this.serverSubscribesId = serverSubscribesId;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((parameter == null) ? 0 : parameter.hashCode());
		result = prime
				* result
				+ ((serverSubscribesId == null) ? 0 : serverSubscribesId
						.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((webUserId == null) ? 0 : webUserId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerHistoryApply other = (ServerHistoryApply) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id != other.id)
			return false;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		if (serverSubscribesId == null) {
			if (other.serverSubscribesId != null)
				return false;
		} else if (!serverSubscribesId.equals(other.serverSubscribesId))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (webUserId == null) {
			if (other.webUserId != null)
				return false;
		} else if (!webUserId.equals(other.webUserId))
			return false;
		return true;
	}
}

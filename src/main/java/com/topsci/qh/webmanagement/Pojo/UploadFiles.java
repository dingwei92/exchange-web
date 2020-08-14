package com.topsci.qh.webmanagement.Pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class UploadFiles {
    @Id
    private String uuid;
    private LocalDateTime uploadTime;
    private String uploadUserId;
    private String description;
    private String docPath;
    private String docName;
    private String fileType;//1文档  2凭据
    public static String Protocol = "1";
    public static String Pics = "2";

    public String getUuid() {
    	return uuid;
    }
    
    public void setUuid(String uuid) {
    	this.uuid = uuid;
    }
    public LocalDateTime getUploadTime() {
    	return uploadTime;
    }
    
    public void setUploadTime(LocalDateTime uploadTime) {
    	this.uploadTime = uploadTime;
    }
    
    public String getUploadUserId() {
    	return uploadUserId;
    }
    
    public void setUploadUserId(String uploadUserId) {
    	this.uploadUserId = uploadUserId;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
    
    public String getDocPath() {
    	return docPath;
    }
    
    public void setDocPath(String docPath) {
    	this.docPath = docPath;
    }
    
    public String getDocName() {
    	return docName;
    }


    public void setDocName(String docName) {
    	this.docName = docName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UploadFiles that = (UploadFiles) o;

        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (uploadTime != null ? !uploadTime.equals(that.uploadTime) : that.uploadTime != null) return false;
        if (uploadUserId != null ? !uploadUserId.equals(that.uploadUserId) : that.uploadUserId != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (docPath != null ? !docPath.equals(that.docPath) : that.docPath != null) return false;
        if (docName != null ? !docName.equals(that.docName) : that.docName != null) return false;

        return true;
    }


	@Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (uploadTime != null ? uploadTime.hashCode() : 0);
        result = 31 * result + (uploadUserId != null ? uploadUserId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (docPath != null ? docPath.hashCode() : 0);
        result = 31 * result + (docName != null ? docName.hashCode() : 0);
        return result;
    }
}

package com.oneplatform.common.dao.entity;

import com.jeesuite.mybatis.core.BaseEntity;
import java.util.Date;
import javax.persistence.*;

@Table(name = "upload_files")
public class UploadFileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "app_id")
    private String appId;

    private String group;
    
    private String provider;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_location")
    private String fileLocation;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return app_id
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }
    

    public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	/**
     * @return provider
     */
    public String getProvider() {
        return provider;
    }

    /**
     * @param provider
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * @return file_name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return file_url
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * @param fileUrl
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    /**
     * @return file_location
     */
    public String getFileLocation() {
        return fileLocation;
    }

    /**
     * @param fileLocation
     */
    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    /**
     * @return mime_type
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @param mimeType
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * @return created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return created_by
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
}
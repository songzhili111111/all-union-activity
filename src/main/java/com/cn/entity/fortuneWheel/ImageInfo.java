package com.cn.entity.fortuneWheel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * 转盘图片表
 * @author songzhili
 * 2016年7月5日下午1:29:46
 */
@Entity
@Table(name="IMAGEINFO")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ImageInfo implements Serializable{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -3810366788975820307L;
	/**主键**/
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "IMAGE_ID" ,length=32)
    private String imageId;
    /**创建人**/
    @Column(name = "IMAGE_CREATE" ,length=50)
    private String imageCreate;
    /**创建时间**/
    @Column(name = "IMAGE_CREATED" ,length=32)
    private Date imageCreated;
    /**图片地址 主机**/
    @Column(name = "IMAGE_HOST" ,length=100)
    private String imageHost;
    /**图片名称**/
    @Column(name = "IMAGE_NAME" ,length=50)
    private String imageName;
    /**图片地址**/
    @Column(name = "IMAGE_PATH" ,length=100)
    private String imagePath;
    /**图片对应的 表的主键**/
    @Column(name = "IMAGE_RECORD" ,length=32)
    private String imageRecord;
    /**图片类型**/
    @Column(name = "IMAGE_TYPE" ,length=10)
    private String imageType;
    /**更新人**/
    @Column(name = "IMAGE_UPDATE" ,length=50)
    private String imageUpdate;
    /**更新时间**/
    @Column(name = "IMAGE_UPDATED" ,length=32)
    private Date imageUpdated;
    
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageCreate() {
		return imageCreate;
	}

	public void setImageCreate(String imageCreate) {
		this.imageCreate = imageCreate;
	}

	public Date getImageCreated() {
		return imageCreated;
	}

	public void setImageCreated(Date imageCreated) {
		this.imageCreated = imageCreated;
	}

	public String getImageHost() {
		return imageHost;
	}

	public void setImageHost(String imageHost) {
		this.imageHost = imageHost;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageRecord() {
		return imageRecord;
	}

	public void setImageRecord(String imageRecord) {
		this.imageRecord = imageRecord;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getImageUpdate() {
		return imageUpdate;
	}

	public void setImageUpdate(String imageUpdate) {
		this.imageUpdate = imageUpdate;
	}

	public Date getImageUpdated() {
		return imageUpdated;
	}

	public void setImageUpdated(Date imageUpdated) {
		this.imageUpdated = imageUpdated;
	}

	public ImageInfo(){
	    	
	 }

	public ImageInfo(String imageId, String imageCreate, Date imageCreated,
			String imageHost, String imageName, String imagePath,
			String imageRecord, String imageType, String imageUpdate,
			Date imageUpdated) {
		super();
		this.imageId = imageId;
		this.imageCreate = imageCreate;
		this.imageCreated = imageCreated;
		this.imageHost = imageHost;
		this.imageName = imageName;
		this.imagePath = imagePath;
		this.imageRecord = imageRecord;
		this.imageType = imageType;
		this.imageUpdate = imageUpdate;
		this.imageUpdated = imageUpdated;
	}

	@Override
	public String toString() {
		return "ImageInfo [imageId=" + imageId + ", imageCreate=" + imageCreate
				+ ", imageCreated=" + imageCreated + ", imageHost=" + imageHost
				+ ", imageName=" + imageName + ", imagePath=" + imagePath
				+ ", imageRecord=" + imageRecord + ", imageType=" + imageType
				+ ", imageUpdate=" + imageUpdate + ", imageUpdated="
				+ imageUpdated + "]";
	}
}

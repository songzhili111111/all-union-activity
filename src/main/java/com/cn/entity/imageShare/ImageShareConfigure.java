package com.cn.entity.imageShare;

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
 * 长图分享配置映射实体
 * @author songzhili
 * 2016年8月11日上午9:02:48
 */
@Entity
@Table(name="PLUS_HB_IMAGESHARECONFIGURE")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ImageShareConfigure implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 908883360044476584L;
   
	/** 主键 **/
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "SHARE_ID", length = 32)
	private String shareId;
	/**名称**/
	@Column(name = "SHARE_NAME", length = 500)
	private String shareName;
	/**图片位置说明**/
	@Column(name = "SHARE_EXPLAIN", length = 2000)
	private String shareExplain;
	/**二维码文本**/
	@Column(name = "QR_CODE", length = 3000)
	private String qrCode;
	/**二维码描述**/
	@Column(name = "QR_CODE_EXPLAIN", length = 3000)
	private String qrCodeExplain;
	/**扩展字段1**/
	@Column(name = "EXPAND_PARAM1",length = 2000)
	private String expandParamOne;
	/**扩展字段2**/
	@Column(name = "EXPAND_PARAM2",length = 2000)
	private String expandParamTwo;
	/** 创建时间 **/
    @Column(name = "CREATE_TIME", length = 30)
    private Date createTime;
    /**图片分享活动说明**/
    @Column(name = "ACTIVITY_EXPLAIN", length = 2000)
	private String activityExplain;
    
	public String getShareId() {
		return shareId;
	}
	public void setShareId(String shareId) {
		this.shareId = shareId;
	}
	public String getShareName() {
		return shareName;
	}
	public void setShareName(String shareName) {
		this.shareName = shareName;
	}
	public String getShareExplain() {
		return shareExplain;
	}
	public void setShareExplain(String shareExplain) {
		this.shareExplain = shareExplain;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getQrCodeExplain() {
		return qrCodeExplain;
	}
	public void setQrCodeExplain(String qrCodeExplain) {
		this.qrCodeExplain = qrCodeExplain;
	}
	public String getExpandParamOne() {
		return expandParamOne;
	}
	public void setExpandParamOne(String expandParamOne) {
		this.expandParamOne = expandParamOne;
	}
	public String getExpandParamTwo() {
		return expandParamTwo;
	}
	public void setExpandParamTwo(String expandParamTwo) {
		this.expandParamTwo = expandParamTwo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getActivityExplain() {
		return activityExplain;
	}
	public void setActivityExplain(String activityExplain) {
		this.activityExplain = activityExplain;
	}
	public ImageShareConfigure(){
		
	}
	public ImageShareConfigure(String shareId, String shareName,
			String shareExplain, String qrCode, String qrCodeExplain,
			String expandParamOne, String expandParamTwo, Date createTime,
			String activityExplain) {
		super();
		this.shareId = shareId;
		this.shareName = shareName;
		this.shareExplain = shareExplain;
		this.qrCode = qrCode;
		this.qrCodeExplain = qrCodeExplain;
		this.expandParamOne = expandParamOne;
		this.expandParamTwo = expandParamTwo;
		this.createTime = createTime;
		this.activityExplain = activityExplain;
	}
	@Override
	public String toString() {
		return "ImageShareConfigure [shareId=" + shareId + ", shareName="
				+ shareName + ", shareExplain=" + shareExplain + ", qrCode="
				+ qrCode + ", qrCodeExplain=" + qrCodeExplain
				+ ", expandParamOne=" + expandParamOne + ", expandParamTwo="
				+ expandParamTwo + ", createTime=" + createTime
				+ ", activityExplain=" + activityExplain + "]";
	}
}

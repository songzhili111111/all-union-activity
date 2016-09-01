package com.cn.entity.fortuneWheel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
/**
 * 幸运盘活动配置表
 * @author songzhili
 * 2016年6月30日下午2:26:14
 */
@Entity
@Table(name="PLUS_HB_WHEELCONFIGURE")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FortuneWheelConfigure implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1574292531500942613L;
   
    /**活动id**/
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "ACTIVITY_ID" ,length=32)
    private String activityId;
    
    /**活动名称**/
    @Column(name = "ACTIVITY_NAME" ,length=100)
    private String activityName;
    
    /**奖项设置**/
    @Column(name = "AWARD_SETUP" ,length=1000)
    private String awardSetup;

    /**活动说明**/
    @Column(name = "ACTIVITY_EXPLAIN" ,length=1000)
    private String activityExplain;

    /**活动限制**/
    @Column(name = "ACTIVITY_LIMIT" ,length=1000)
    private String activityLimit;
    /**创建时间**/
    @Column(name = "CREATE_TIME" ,length=30)
    private Date createTime;
    /**第三方渠道Id**/
    @Column(name = "THIRD_PARTYID" ,length=100)
    private String thirdPartyId;
    /**转盘图片地址**/
    @Transient
    private String imageUrl;
    /**转盘图片名称**/
    @Transient
    private String imageName;
    
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityExplain() {
		return activityExplain;
	}

	public void setActivityExplain(String activityExplain) {
		this.activityExplain = activityExplain;
	}

	public String getActivityLimit() {
		return activityLimit;
	}

	public void setActivityLimit(String activityLimit) {
		this.activityLimit = activityLimit;
	}
    
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
    
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
     
	public String getAwardSetup() {
		return awardSetup;
	}

	public void setAwardSetup(String awardSetup) {
		this.awardSetup = awardSetup;
	}
    
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public FortuneWheelConfigure(){
		
	}
	public String getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}
    
	public FortuneWheelConfigure(String activityId, String activityName,
			String awardSetup, String activityExplain, String activityLimit,
			Date createTime, String thirdPartyId, String imageUrl,
			String imageName) {
		super();
		this.activityId = activityId;
		this.activityName = activityName;
		this.awardSetup = awardSetup;
		this.activityExplain = activityExplain;
		this.activityLimit = activityLimit;
		this.createTime = createTime;
		this.thirdPartyId = thirdPartyId;
		this.imageUrl = imageUrl;
		this.imageName = imageName;
	}

	@Override
	public String toString() {
		return "FortuneWheelConfigure [activityId=" + activityId
				+ ", activityName=" + activityName + ", awardSetup="
				+ awardSetup + ", activityExplain=" + activityExplain
				+ ", activityLimit=" + activityLimit + ", createTime="
				+ createTime + ", thirdPartyId=" + thirdPartyId + ", imageUrl="
				+ imageUrl + ", imageName=" + imageName + "]";
	}
}

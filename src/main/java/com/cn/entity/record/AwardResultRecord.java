package com.cn.entity.record;

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
 * 用户中奖结果记录表
 * @author songzhili
 * 2016年7月21日上午9:18:53
 */
@Entity
@Table(name="PLUS_HB_AWARDRESULTRECORD")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AwardResultRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 628598414508916707L;
   
	/** 主键 **/
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "RESULT_ID", length = 32)
	private String resultId;
	/**用户微信id**/
	@Column(name = "OPEN_ID", length = 100)
	private String openId;
	/**应用的ID**/
	@Column(name = "APP_ID", length = 100)
	private String appId;
	/**转盘活动主键id**/
	@Column(name = "ACTIVITY_ID", length = 40)
	private String activityId;
	/**中奖主键Id**/
	@Column(name = "AWARD_ID",length = 40)
    private String awardId;
    /** 创建时间 **/
    @Column(name = "CREATE_TIME", length = 30)
    private Date createTime;
	public String getResultId() {
		return resultId;
	}
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getAwardId() {
		return awardId;
	}
	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public AwardResultRecord() {
		super();
	}
	public AwardResultRecord(String resultId, String openId, String appId,
			String activityId, String awardId, Date createTime) {
		super();
		this.resultId = resultId;
		this.openId = openId;
		this.appId = appId;
		this.activityId = activityId;
		this.awardId = awardId;
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "AwardResultRecord [resultId=" + resultId + ", openId=" + openId
				+ ", appId=" + appId + ", activityId=" + activityId
				+ ", awardId=" + awardId + ", createTime=" + createTime + "]";
	}
}

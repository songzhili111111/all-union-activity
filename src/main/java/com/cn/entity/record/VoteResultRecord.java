package com.cn.entity.record;

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
 * 公共结果记录表
 * 如:存储投票的结果。
 * @author songzhili
 * 2016年7月18日下午3:02:25
 */
@Entity
@Table(name="PLUS_HB_VOTERESULTRECORD")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VoteResultRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1456744308680964410L;
   
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
	/**投票选项主键id**/
	@Column(name = "CHOICE_ID", length = 40)
	private String choiceId;
	/** 创建时间 **/
	@Column(name = "CREATE_TIME", length = 30)
	private Date createTime;
	/**主题主键ID**/
	@Column(name = "THEME_ID", length = 40)
	private String themeId;
	/**活动Id**/
	@Transient
	private String activityId;
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
	public String getChoiceId() {
		return choiceId;
	}
	public void setChoiceId(String choiceId) {
		this.choiceId = choiceId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getThemeId() {
		return themeId;
	}
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public VoteResultRecord(){
		
	}
	public VoteResultRecord(String resultId, String openId, String appId,
			String choiceId, Date createTime, String themeId, String activityId) {
		super();
		this.resultId = resultId;
		this.openId = openId;
		this.appId = appId;
		this.choiceId = choiceId;
		this.createTime = createTime;
		this.themeId = themeId;
		this.activityId = activityId;
	}
	@Override
	public String toString() {
		return "VoteResultRecord [resultId=" + resultId + ", openId=" + openId
				+ ", appId=" + appId + ", choiceId=" + choiceId
				+ ", createTime=" + createTime + ", themeId=" + themeId
				+ ", activityId=" + activityId + "]";
	}
}










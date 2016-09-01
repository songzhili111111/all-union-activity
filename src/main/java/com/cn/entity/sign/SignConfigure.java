package com.cn.entity.sign;

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
 * 签到配置表
 * @author songzhili
 * 2016年7月18日下午3:14:24
 */
@Entity
@Table(name="PLUS_HB_SIGNCONFIGURE")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SignConfigure implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6562312690215283177L;
  
	
	/** 主键 **/
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "SIGN_ID", length = 32)
	private String signId;
	/**用户微信id**/
	@Column(name = "OPEN_ID", length = 100)
	private String openId;
	/**应用的ID**/
	@Column(name = "APP_ID", length = 100)
	private String appId;
	/**签到日期**/
	@Column(name = "SIGN_TIME", length = 100)
	private String signTime;
	/** 创建时间 **/
	@Column(name = "CREATE_TIME", length = 30)
	private Date createTime;
	/**连续签到三天赠送奖品 标记
	 * 获取某用户的签到列表,如果存在该字段非空
	 * ,说明已经赠送过奖品.
	 * **/
	@Column(name = "GIVE_AWAY", length = 40)
	private String giveAway;
	public String getSignId() {
		return signId;
	}
	public void setSignId(String signId) {
		this.signId = signId;
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
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getGiveAway() {
		return giveAway;
	}
	public void setGiveAway(String giveAway) {
		this.giveAway = giveAway;
	}
	public SignConfigure(){
		
	}
	public SignConfigure(String signId, String openId, String appId,
			String signTime, Date createTime, String giveAway) {
		super();
		this.signId = signId;
		this.openId = openId;
		this.appId = appId;
		this.signTime = signTime;
		this.createTime = createTime;
		this.giveAway = giveAway;
	}
	@Override
	public String toString() {
		return "SignConfigure [signId=" + signId + ", openId=" + openId
				+ ", appId=" + appId + ", signTime=" + signTime
				+ ", createTime=" + createTime + ", giveAway=" + giveAway + "]";
	}
	
}










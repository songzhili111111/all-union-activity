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
 * 幸运盘活动奖项表
 * @author songzhili
 * 2016年6月30日下午2:35:08
 */
@Entity
@Table(name="PLUS_HB_WHEELAWARD")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FortuneWheelAward implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6845397297762412248L;
   
	@Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "AWARD_ID",length = 32)
    private String awardId;
	/**奖项活动的id 主表的中活动的id**/
    @Column(name = "ACTIVITY_ID" ,length=100)
    private String activityId;
    /**奖项名称**/
    @Column(name = "AWARD_NAME" ,length=100)
    private String awardName;
    /**奖项说明**/
    @Column(name = "AWARD_EXPLAIN" ,length=1000)
    private String awardExplain;
    /**中间概率**/
    @Column(name = "AWARD_CHANCE" ,length=100)
    private String awardChance;
    /**扩展字段**/
    @Column(name = "EXPAND_PARAM" ,length=1000)
    private String expandParam;
    /**创建时间**/
    @Column(name = "CREATE_TIME" ,length=30)
    private Date createTime;
    /**奖项在活动中的排序**/
    @Column(name = "AWARD_TRUN" ,length=30)
    private String awardTurn;
    
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public String getAwardExplain() {
		return awardExplain;
	}
	public void setAwardExplain(String awardExplain) {
		this.awardExplain = awardExplain;
	}
	public String getAwardChance() {
		return awardChance;
	}
	public void setAwardChance(String awardChance) {
		this.awardChance = awardChance;
	}
	public String getExpandParam() {
		return expandParam;
	}
	public void setExpandParam(String expandParam) {
		this.expandParam = expandParam;
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
	public String getAwardTurn() {
		return awardTurn;
	}
	public void setAwardTurn(String awardTurn) {
		this.awardTurn = awardTurn;
	}
	public FortuneWheelAward(){
		
	}
	public FortuneWheelAward(String awardId, String activityId,
			String awardName, String awardExplain, String awardChance,
			String expandParam, Date createTime, String awardTurn) {
		super();
		this.awardId = awardId;
		this.activityId = activityId;
		this.awardName = awardName;
		this.awardExplain = awardExplain;
		this.awardChance = awardChance;
		this.expandParam = expandParam;
		this.createTime = createTime;
		this.awardTurn = awardTurn;
	}
	@Override
	public String toString() {
		return "FortuneWheelAward [awardId=" + awardId + ", activityId="
				+ activityId + ", awardName=" + awardName + ", awardExplain="
				+ awardExplain + ", awardChance=" + awardChance
				+ ", expandParam=" + expandParam + ", createTime=" + createTime
				+ ", awardTurn=" + awardTurn + "]";
	}
}

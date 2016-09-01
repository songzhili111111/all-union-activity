package com.cn.entity.voteTools;

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
 * 投票主题表
 * @author songzhili
 * 2016年7月1日下午1:53:59
 */
@Entity
@Table(name="PLUS_HB_VOTETHEME")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VoteTheme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6178391643584955023L;
  
	@Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "THEME_ID",length = 32)
    private String themeId;
	/**主题名称**/
    @Column(name = "THEME_NAME" ,length=100)
    private String themeName;
    /**人数限制**/
    @Column(name = "NUMBER_LIMIT" ,length=100)
    private String numberLimit;
    /**开始时间**/
    @Column(name = "START_TIME" ,length=50)
    private String startTime;
    /**开始时间**/
    @Column(name = "END_TIME" ,length=50)
    private String endTime;
    /**单选/多选 **/
    @Column(name = "SINGLE_OR_MULTI" ,length=10)
    private String singleOrMulti;
    /**主题说明**/
    @Column(name = "THEME_EXPLAIN" ,length=2000)
    private String themeExplain;
    /**创建时间**/
    @Column(name = "CREATE_TIME" ,length=30)
    private Date createTime;
    /** 第三方渠道Id **/
	@Column(name = "THIRD_PARTYID", length = 100)
	private String thirdPartyId;
	public String getThemeId() {
		return themeId;
	}
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	public String getThemeName() {
		return themeName;
	}
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
	public String getNumberLimit() {
		return numberLimit;
	}
	public void setNumberLimit(String numberLimit) {
		this.numberLimit = numberLimit;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSingleOrMulti() {
		return singleOrMulti;
	}
	public void setSingleOrMulti(String singleOrMulti) {
		this.singleOrMulti = singleOrMulti;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getThirdPartyId() {
		return thirdPartyId;
	}
	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}
	public String getThemeExplain() {
		return themeExplain;
	}
	public void setThemeExplain(String themeExplain) {
		this.themeExplain = themeExplain;
	}
	public VoteTheme(){
		
	}
	public VoteTheme(String themeId, String themeName, String numberLimit,
			String startTime, String endTime, String singleOrMulti,
			String themeExplain, Date createTime, String thirdPartyId) {
		super();
		this.themeId = themeId;
		this.themeName = themeName;
		this.numberLimit = numberLimit;
		this.startTime = startTime;
		this.endTime = endTime;
		this.singleOrMulti = singleOrMulti;
		this.themeExplain = themeExplain;
		this.createTime = createTime;
		this.thirdPartyId = thirdPartyId;
	}
	@Override
	public String toString() {
		return "VoteTheme [themeId=" + themeId + ", themeName=" + themeName
				+ ", numberLimit=" + numberLimit + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", singleOrMulti=" + singleOrMulti
				+ ", themeExplain=" + themeExplain + ", createTime="
				+ createTime + ", thirdPartyId=" + thirdPartyId + "]";
	}
}

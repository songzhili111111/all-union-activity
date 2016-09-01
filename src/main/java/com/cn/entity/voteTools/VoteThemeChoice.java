package com.cn.entity.voteTools;

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
 * 投票主题活动选项表
 * @author songzhili
 * 2016年7月1日下午2:06:38
 */
@Entity
@Table(name="PLUS_HB_VOTETHEMECHOICE")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VoteThemeChoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1781178960229674103L;
    
	/**主键**/
	@Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "CHOICE_ID",length = 32)
    private String choiceId;
	/**活动主题ID**/
	@Column(name = "THEME_ID",length = 32)
	private String themeId;
	/**选项名称**/
	@Column(name = "CHOICE_NAME",length = 200)
	private String choiceName;
	/**选项值**/
	@Column(name = "CHOICE_VALUE",length = 100)
	private String choiceValue;
	/**选项类型**/
	@Column(name = "CHOICE_TYPE",length = 1000)
	private String choiceType;
	/**扩展字段1**/
	@Column(name = "EXPAND_PARAM1",length = 2000)
	private String expandParamOne;
	/**扩展字段2**/
	@Column(name = "EXPAND_PARAM2",length = 2000)
	private String expandParamTwo;
	/**创建时间**/
    @Column(name = "CREATE_TIME" ,length=30)
    private Date createTime;
    /**已投票数**/
    @Column(name = "CHOICE_NUMBER" ,length=8)
    private Integer choiceNumber;
    /**图片的相对路径**/
    @Transient
    private String imageUrl;
    /**图片名称**/
    @Transient
    private String imageName;
	public String getChoiceId() {
		return choiceId;
	}
	public void setChoiceId(String choiceId) {
		this.choiceId = choiceId;
	}
	public String getThemeId() {
		return themeId;
	}
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	public String getChoiceName() {
		return choiceName;
	}
	public void setChoiceName(String choiceName) {
		this.choiceName = choiceName;
	}
	public String getChoiceValue() {
		return choiceValue;
	}
	public void setChoiceValue(String choiceValue) {
		this.choiceValue = choiceValue;
	}
	public String getChoiceType() {
		return choiceType;
	}
	public void setChoiceType(String choiceType) {
		this.choiceType = choiceType;
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
	public VoteThemeChoice(){
		
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
	public Integer getChoiceNumber() {
		return choiceNumber;
	}
	public void setChoiceNumber(Integer choiceNumber) {
		this.choiceNumber = choiceNumber;
	}
	@Override
	public String toString() {
		return "VoteThemeChoice [choiceId=" + choiceId + ", themeId=" + themeId
				+ ", choiceName=" + choiceName + ", choiceValue=" + choiceValue
				+ ", choiceType=" + choiceType + ", expandParamOne="
				+ expandParamOne + ", expandParamTwo=" + expandParamTwo
				+ ", createTime=" + createTime + ", choiceNumber="
				+ choiceNumber + ", imageUrl=" + imageUrl + ", imageName="
				+ imageName + "]";
	}
	public VoteThemeChoice(String choiceId, String themeId, String choiceName,
			String choiceValue, String choiceType, String expandParamOne,
			String expandParamTwo, Date createTime, Integer choiceNumber,
			String imageUrl, String imageName) {
		super();
		this.choiceId = choiceId;
		this.themeId = themeId;
		this.choiceName = choiceName;
		this.choiceValue = choiceValue;
		this.choiceType = choiceType;
		this.expandParamOne = expandParamOne;
		this.expandParamTwo = expandParamTwo;
		this.createTime = createTime;
		this.choiceNumber = choiceNumber;
		this.imageUrl = imageUrl;
		this.imageName = imageName;
	}
}

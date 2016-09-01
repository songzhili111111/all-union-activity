package com.test;

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

@Entity
@Table(name="PLUS_HB_TEST")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PlusTest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4373195011475552218L;
    
	@Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "ID" ,length=32)
    private String id;
	
	@Column(name = "CREATE_DATE" ,length=32)
	private Date createDate;
	/****/
	@Column(name = "NAME" ,length=32)
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public PlusTest(String id, Date createDate, String name) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.name = name;
	}

	@Override
	public String toString() {
		return "PlusTest [id=" + id + ", createDate=" + createDate + ", name="
				+ name + "]";
	}

	public PlusTest(){
		
	}
}

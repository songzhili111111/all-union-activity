package com.cn.dao.interf;

import java.util.List;

import com.cn.entity.record.VoteResultRecord;

/**
 * 公共结果记录表DAO
 * @author songzhili
 * 2016年7月18日下午3:22:00
 */
public interface VoteResultRecordDao {
  
	public boolean add(VoteResultRecord result)throws Exception;
	/****/
	public boolean delete()throws Exception;
	/****/
	public boolean deleteByOpenId(String openId)throws Exception;
	/****/
	public boolean update(VoteResultRecord result)throws Exception;
	/****/
	public VoteResultRecord obtain(String id)throws Exception;
	/****/
	public <T> List<T> obtainList(Class<T> clazz,Integer currentPage,Integer pageSize)throws Exception;
	/****/
	public int count()throws Exception;
	/****/
	public int countByCriteria(String openId,String themeId)throws Exception;
}

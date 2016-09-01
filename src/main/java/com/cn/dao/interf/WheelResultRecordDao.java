package com.cn.dao.interf;

import java.util.List;

import com.cn.entity.record.WheelResultRecord;

/**
 * 幸运盘结果记录DAO
 * @author songzhili
 * 2016年7月19日上午9:46:42
 */
public interface WheelResultRecordDao {

	
	public boolean add(WheelResultRecord result)throws Exception;
	/****/
	public boolean delete(String id)throws Exception;
	/****/
	public boolean update(WheelResultRecord result)throws Exception;
	/****/
	public boolean updateRemainNumber(String openId,Integer remainNumber)throws Exception;
	/****/
	public WheelResultRecord obtain(String id)throws Exception;
	/****/
	public <T> List<T> obtainList(Class<T> clazz,Integer currentPage,Integer pageSize)throws Exception;
	/****/
	public <T> List<T> obtainListByOpenId(Class<T> clazz,String openId,String activityId,String appId)throws Exception;
	/****/
	public boolean updateRemainNumberById(String resultId,boolean plus)throws Exception;
}

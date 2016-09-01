package com.cn.dao.interf;

import java.util.List;

import com.cn.entity.record.AwardResultRecord;

/**
 * 用户中奖结果记录表DAO
 * @author songzhili
 * 2016年7月21日上午9:28:35
 */
public interface AwardResultRecordDao {
  
	public boolean add(AwardResultRecord result) throws Exception;
	/****/
	public boolean delete(String id) throws Exception;
	/****/
	public boolean update(AwardResultRecord result)throws Exception;
	/****/
	public AwardResultRecord obtain(String id)throws Exception;
	/****/
	public <T> List<T> obtainList(Class<T> clazz,Integer currentPage,Integer pageSize)throws Exception;
}

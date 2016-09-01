package com.cn.dao.interf;

import java.util.List;

import com.cn.entity.fortuneWheel.FortuneWheelConfigure;

/**
 * 幸运盘活动配置表dao层
 * @author songzhili
 * 2016年6月30日下午2:42:48
 */
public interface FortuneWheelConfigureDao {
   
	public boolean add(FortuneWheelConfigure configure) throws Exception;
	/****/
	@SuppressWarnings("rawtypes")
	public List getConfigureList(Integer currentPage,Integer pageSize)throws Exception;
	/****/
	@SuppressWarnings("rawtypes")
	public List getConfigureListByName(String activityName,Integer currentPage,Integer pageSize)throws Exception;
	/****/
	public boolean deleteById(String id) throws Exception;
	/****/
	public boolean update(FortuneWheelConfigure configure) throws Exception;
	/****/
	public FortuneWheelConfigure getById(String id) throws Exception;
	/****/
	public boolean updateFileds(FortuneWheelConfigure configure) throws Exception;
	/****/
	public int count()throws Exception;
	/****/
	public int countByCriteria(String activityName)throws Exception;
}

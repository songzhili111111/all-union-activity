package com.cn.dao.interf;

import java.util.List;

import com.cn.entity.fortuneWheel.FortuneWheelAward;

/**
 * 奖项说明表Dao
 * @author songzhili
 * 2016年6月30日下午6:09:38
 */
public interface FortuneWheelAwardDao {

	public boolean add(FortuneWheelAward award) throws Exception;
	/****/
	@SuppressWarnings("rawtypes")
	public List getConfigureList()throws Exception;
	/****/
	public <T> List<T> getAwardListByActivityId(String activityId)throws Exception;
	/****/
	public boolean deleteById(String id) throws Exception;
	/****/
	public boolean update(FortuneWheelAward award) throws Exception;
	/****/
	public FortuneWheelAward getById(String id) throws Exception;
	/****/
	public int count()throws Exception;
	/****/
	public int countByCriteria(String activityId)throws Exception;
}

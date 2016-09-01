package com.cn.dao.interf;

import java.util.List;

import com.cn.entity.sign.SignConfigure;

/**
 * 签到DAO
 * @author songzhili
 * 2016年7月18日下午3:32:39
 */
public interface SignDao {
  
	public boolean add(SignConfigure sign)throws Exception;
	/****/
	public boolean delete(String id)throws Exception;
	/****/
	public boolean deleteByOpenId(String openId,String signTime)throws Exception;
	/****/
	public boolean update(SignConfigure sign)throws Exception;
	/****/
	public boolean updateGiveAwayBySignId(String giveAway,String signId) throws Exception;
	/****/
	public SignConfigure obtain(String id)throws Exception;
	/****/
	public <T> List<T> obtainList(Class<T> clazz,Integer currentPage,Integer pageSize)throws Exception;
	/****/
	public int count()throws Exception;
	/****/
	public <T> List<T> obtainListByOpenId(Class<T> clazz,String openId,String appId,Integer currentPage,Integer pageSize)throws Exception;
	/****/
	public <T> List<T> obtainListByGiveAway(Class<T> clazz,String openId)throws Exception;
}

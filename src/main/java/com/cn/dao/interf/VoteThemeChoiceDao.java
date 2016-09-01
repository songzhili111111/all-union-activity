package com.cn.dao.interf;

import java.util.List;

import com.cn.entity.voteTools.VoteThemeChoice;
/**
 * 投票主题选项DAO
 * @author songzhili
 * 2016年7月1日下午2:33:42
 */
public interface VoteThemeChoiceDao {
  
	public boolean add(VoteThemeChoice choice)throws Exception;
	/****/
	public boolean deleteById(String id)throws Exception;
	/****/
	public boolean update(VoteThemeChoice choice)throws Exception;
	/****/
	public VoteThemeChoice getById(String id) throws Exception;
	/****/
	@SuppressWarnings("rawtypes")
	public List getList()throws Exception;
	/****/
	@SuppressWarnings("rawtypes")
	public List getChoiceListByThemeId(String themeId) throws Exception;
    /****/
    public int count()throws Exception;
	/****/
    public int countByCriteria(String themeId)throws Exception;
    /****/
    public boolean updateByChoiceId(String choiceId) throws Exception;
}

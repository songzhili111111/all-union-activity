package com.cn.dao.interf;

import java.util.List;

import com.cn.entity.voteTools.VoteTheme;

/**
 * 投票活动主题
 * @author songzhili
 * 2016年7月1日下午2:21:10
 */
public interface VoteThemeDao {
   
   public boolean add(VoteTheme theme) throws Exception;
   /****/
   public boolean deleteById(String id)throws Exception;
   /****/
   public boolean updateById(String id) throws Exception;
   /****/
   public boolean update(VoteTheme theme) throws Exception;
   /****/
   public VoteTheme getObjectById(String id) throws Exception;
   /****/
   @SuppressWarnings("rawtypes")
   public List getList(Integer currentPage,Integer pageSize) throws Exception;
   /****/
   @SuppressWarnings("rawtypes")
   public List getVoteThemeByThemeName(String themeName,Integer currentPage,Integer pageSize) throws Exception;
   /****/
   public int count()throws Exception;
	/****/
   public int countByCriteria(String themeName)throws Exception;
}

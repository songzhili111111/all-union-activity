package com.cn.dao.interfImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.common.Utils;
import com.cn.dao.interf.VoteThemeDao;
import com.cn.entity.voteTools.VoteTheme;
/**
 * 投票主题DAO
 * @author songzhili
 * 2016年7月1日下午2:25:20
 */
@Repository("voteThemeDao")
@Transactional
public class VoteThemeDaoImpl implements VoteThemeDao {
   
	@Autowired
	private SessionFactory sessionFactory;
	/****/
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public boolean add(VoteTheme theme) throws Exception {
		if(theme == null){
			return false;
		}
		getSession().save(theme);
		return true;
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		if(Utils.isEmpty(id)){
			return false;
		}
		VoteTheme theme = getObjectById(id);
		getSession().delete(theme);
		return true;
	}

	@Override
	public boolean updateById(String id) throws Exception {
		return false;
	}
	
	@Override
	public boolean update(VoteTheme theme) throws Exception {
		getSession().update(theme);
		return true;
	}
	
	@Override
	public VoteTheme getObjectById(String id) throws Exception {
		if(Utils.isEmpty(id)){
			return new VoteTheme();
		}
		VoteTheme theme = (VoteTheme)getSession().get(VoteTheme.class, id);
		return theme;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getList(Integer currentPage,Integer pageSize) throws Exception {
		Criteria criteria = getSession().createCriteria(VoteTheme.class);
		criteria.setFirstResult(currentPage*pageSize);
		criteria.setMaxResults(pageSize);
		criteria.addOrder(Order.desc("createTime"));
		criteria.setCacheable(false);
		List list = criteria.list();
		return list;
	}
    
	@SuppressWarnings("rawtypes")
	@Override
	public List getVoteThemeByThemeName(String themeName,Integer currentPage,Integer pageSize) throws Exception {
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("themeName", themeName, MatchMode.ANYWHERE));
		Criteria criteria = getSession().createCriteria(VoteTheme.class);
		criteria.setFirstResult(currentPage*pageSize);
		criteria.setMaxResults(pageSize);
		criteria.setCacheable(false);
		criteria.add(conjunction);
		criteria.addOrder(Order.desc("createTime"));
		List list = criteria.list();
		return list;
	}

	@Override
	public int count() throws Exception {
		Criteria criteria = getSession().createCriteria(VoteTheme.class);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}

	@Override
	public int countByCriteria(String themeName) throws Exception {
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("themeName", themeName, MatchMode.ANYWHERE));
		Criteria criteria = getSession().createCriteria(VoteTheme.class);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		criteria.add(conjunction);
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}

}

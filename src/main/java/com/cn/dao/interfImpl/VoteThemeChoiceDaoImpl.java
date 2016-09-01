package com.cn.dao.interfImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
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
import com.cn.dao.interf.VoteThemeChoiceDao;
import com.cn.entity.voteTools.VoteThemeChoice;
/**
 * 投票主题选项DAO
 * @author songzhili
 * 2016年7月1日下午2:37:17
 */
@Repository("voteThemeChoiceDao")
@Transactional
public class VoteThemeChoiceDaoImpl implements VoteThemeChoiceDao {
    
	@Autowired
	private SessionFactory sessionFactory;
	/****/
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	@Override
	public boolean add(VoteThemeChoice choice) throws Exception {
		if(choice == null){
			return false;
		}
		getSession().save(choice);
		return true;
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		if(Utils.isEmpty(id)){
			return false;
		}
		VoteThemeChoice choice = getById(id);
		getSession().delete(choice);
		return true;
	}

	@Override
	public boolean update(VoteThemeChoice choice) throws Exception {
		getSession().update(choice);
		return true;
	}

	@Override
	public VoteThemeChoice getById(String id) throws Exception {
		if(Utils.isEmpty(id)){
			return new VoteThemeChoice();
		}
		VoteThemeChoice choice = (VoteThemeChoice)getSession().get(VoteThemeChoice.class, id);
		return choice;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getList() throws Exception {
		Criteria criteria = getSession().createCriteria(VoteThemeChoice.class);
		List list = criteria.list();
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getChoiceListByThemeId(String themeId) throws Exception {
		
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("themeId", themeId));
		Criteria criteria = getSession().createCriteria(VoteThemeChoice.class);
		criteria.setCacheable(false);
		criteria.add(conjunction);
		criteria.addOrder(Order.desc("createTime"));
		List list = criteria.list();
		return list;
	}
	
	@Override
	public int count() throws Exception {
		Criteria criteria = getSession().createCriteria(VoteThemeChoice.class);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}

	@Override
	public int countByCriteria(String themeId) throws Exception {
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("themeId", themeId, MatchMode.ANYWHERE));
		Criteria criteria = getSession().createCriteria(VoteThemeChoice.class);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		criteria.add(conjunction);
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}
	
	/**
	 * PLUS_HB_VOTETHEMECHOICE
	 */
	@Override
	public boolean updateByChoiceId(String choiceId) throws Exception {
		
		StringBuilder together = new StringBuilder("update PLUS_HB_VOTETHEMECHOICE ");
		together.append("set choice_number = choice_number+1 ");
		together.append("where choice_id = ");
		together.append("'").append(choiceId).append("'");
		SQLQuery query = getSession().createSQLQuery(together.toString());
		query.executeUpdate();
		return true;
	}
    
}

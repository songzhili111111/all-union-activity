package com.test;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("plusTestImpl")
@Transactional
public class PlusTestImpl {

	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("rawtypes")
	public List getList(Integer currentPage,Integer pageSize,String name){
		
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		Criteria criteria = getSession().createCriteria(PlusTest.class);
		criteria.setFirstResult(currentPage*pageSize);
		criteria.setMaxResults(pageSize);
		criteria.setCacheable(false);
		criteria.addOrder(Order.desc("createDate"));
		criteria.add(conjunction);
		List list = criteria.list();
		return list;
	}
	
	public boolean add(PlusTest test){
		getSession().save(test);
		return true;
	}
}

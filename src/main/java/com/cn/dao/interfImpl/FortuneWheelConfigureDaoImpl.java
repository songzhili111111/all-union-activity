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
import com.cn.dao.interf.FortuneWheelConfigureDao;
import com.cn.entity.fortuneWheel.FortuneWheelConfigure;
/**
 * 幸运盘活动配置表dao层
 * @author songzhili
 * 2016年6月30日下午2:49:02
 */
@Repository("fortuneWheelConfigureDao")
@Transactional
public class FortuneWheelConfigureDaoImpl implements FortuneWheelConfigureDao {
   
	@Autowired
	private SessionFactory sessionFactory;
	/****/
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public boolean add(FortuneWheelConfigure configure) throws Exception {
		if(configure == null){
			return false;
		}
		getSession().save(configure);
		return true;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List getConfigureList(Integer currentPage,Integer pageSize) throws Exception {
		Criteria criteria = getSession().createCriteria(FortuneWheelConfigure.class);
		criteria.setFirstResult(currentPage*pageSize);
		criteria.setMaxResults(pageSize);
		criteria.setCacheable(false);
		criteria.addOrder(Order.desc("createTime"));
		List list = criteria.list();
		return list;
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		if(Utils.isEmpty(id)){
			return false;
		}
		FortuneWheelConfigure configure = getById(id);
		getSession().delete(configure);
		return true;
	}

	@Override
	public boolean update(FortuneWheelConfigure configure) throws Exception {
		getSession().update(configure);
		return true;
	}

	@Override
	public boolean updateFileds(FortuneWheelConfigure configure)
			throws Exception {
		
		return true;
	}

	@Override
	public FortuneWheelConfigure getById(String id) throws Exception {
		if(Utils.isEmpty(id)){
			return new FortuneWheelConfigure();
 		}
		FortuneWheelConfigure fortune = 
				(FortuneWheelConfigure)getSession().get(FortuneWheelConfigure.class, id);
		return fortune;
	}
   
	@SuppressWarnings("rawtypes")
	@Override
	public List getConfigureListByName(String activityName,Integer currentPage,Integer pageSize) throws Exception {
		
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("activityName", activityName, MatchMode.ANYWHERE));
		Criteria criteria = getSession().createCriteria(FortuneWheelConfigure.class);
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
		Criteria criteria = getSession().createCriteria(FortuneWheelConfigure.class);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}

	@Override
	public int countByCriteria(String activityName) throws Exception {
		
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("activityName", activityName, MatchMode.ANYWHERE));
		Criteria criteria = getSession().createCriteria(FortuneWheelConfigure.class);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		criteria.add(conjunction);
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}
}

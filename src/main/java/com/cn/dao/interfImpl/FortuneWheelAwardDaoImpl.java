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
import com.cn.dao.interf.FortuneWheelAwardDao;
import com.cn.entity.fortuneWheel.FortuneWheelAward;
/**
 * 奖项说明表Dao
 * @author songzhili
 * 2016年6月30日下午6:10:57
 */
@Repository("fortuneWheelAwardDao")
@Transactional
public class FortuneWheelAwardDaoImpl implements FortuneWheelAwardDao {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	@Override
	public boolean add(FortuneWheelAward award) throws Exception {
		if(award == null){
			return false;
		}
		getSession().save(award);
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getConfigureList() throws Exception {
		Criteria criteria = getSession().createCriteria(FortuneWheelAward.class);
		List list = criteria.list();
		return list;
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		if(Utils.isEmpty(id)){
			return false;
		}
		Object object = getById(id);
		getSession().delete(object);
		return false;
	}

	@Override
	public boolean update(FortuneWheelAward award) throws Exception {
		if(award == null){
			return false;
		}
		getSession().update(award);
		return true;
	}

	@Override
	public FortuneWheelAward getById(String id) throws Exception {
		if(Utils.isEmpty(id)){
			return new FortuneWheelAward();
		}
		FortuneWheelAward award = 
				(FortuneWheelAward)getSession().get(FortuneWheelAward.class, id);
		return award;
	}
	
	@Override
	public <T> List<T> getAwardListByActivityId(String activityId) throws Exception {
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("activityId", activityId, MatchMode.ANYWHERE));
		Criteria criteria = getSession().createCriteria(FortuneWheelAward.class);
		criteria.setCacheable(false);
		criteria.add(conjunction);
		criteria.addOrder(Order.desc("createTime"));
		@SuppressWarnings("unchecked")
		List<T> list = criteria.list();
		return list;
	}
	@Override
	public int count() throws Exception {
		Criteria criteria = getSession().createCriteria(FortuneWheelAward.class);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}
	@Override
	public int countByCriteria(String activityId) throws Exception {
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("activityId", activityId, MatchMode.ANYWHERE));
		Criteria criteria = getSession().createCriteria(FortuneWheelAward.class);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		criteria.add(conjunction);
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}

}

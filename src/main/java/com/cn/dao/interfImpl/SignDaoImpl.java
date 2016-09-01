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

import com.cn.dao.interf.SignDao;
import com.cn.entity.sign.SignConfigure;
/**
 * 签到DAO
 * @author songzhili
 * 2016年7月18日下午3:36:29
 */
@Repository("signDao")
@Transactional
public class SignDaoImpl implements SignDao {
    
	@Autowired
	public SessionFactory sessionFactory;
	
	/****/
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	
	@Override
	public boolean add(SignConfigure sign) throws Exception {
		if(sign == null){
			return false;
		}
		getSession().save(sign);
		return true;
	}

	@Override
	public boolean delete(String id) throws Exception {
		return false;
	}

	@Override
	public boolean deleteByOpenId(String openId,String signTime) throws Exception {
		
		StringBuilder together = new StringBuilder("delete from PLUS_HB_SIGNCONFIGURE");
		together.append(" where OPEN_ID = '").append(openId).append("'");
		together.append(" and SIGN_TIME = '").append(signTime).append("'");
		SQLQuery query = getSession().createSQLQuery(together.toString());
		query.executeUpdate();
		return true;
	}
	
	@Override
	public boolean update(SignConfigure sign) throws Exception {
		return false;
	}

	@Override
	public SignConfigure obtain(String id) throws Exception {
		return null;
	}

	@Override
	public <T> List<T> obtainList(Class<T> clazz,Integer currentPage,Integer pageSize) throws Exception {
		Criteria criteria = getSession().createCriteria(clazz);
		criteria.setFirstResult(currentPage*pageSize);
		criteria.setMaxResults(pageSize);
		criteria.addOrder(Order.desc("createTime"));
		criteria.setCacheable(false);
		@SuppressWarnings("unchecked")
		List<T> list = criteria.list();
		return list;
	}

	@Override
	public int count() throws Exception {
		Criteria criteria = getSession().createCriteria(SignConfigure.class);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}


	@Override
	public <T> List<T> obtainListByOpenId(Class<T> clazz, String openId,
			String appId,Integer currentPage,Integer pageSize) throws Exception {
		
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("openId", openId, MatchMode.ANYWHERE));
		conjunction.add(Restrictions.like("appId", appId, MatchMode.ANYWHERE));
		Criteria criteria = getSession().createCriteria(clazz);
		criteria.setFirstResult(currentPage*pageSize);
		criteria.setMaxResults(pageSize);
		criteria.setCacheable(false);
		criteria.addOrder(Order.desc("createTime"));
		criteria.add(conjunction);
		@SuppressWarnings("unchecked")
		List<T> list = criteria.list();
		return list;
	}

	@Override
	public <T> List<T> obtainListByGiveAway(Class<T> clazz,String openId) throws Exception {
		
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.isNotNull("giveAway"));
		conjunction.add(Restrictions.like("openId", openId, MatchMode.ANYWHERE));
		Criteria criteria = getSession().createCriteria(clazz);
		criteria.setCacheable(false);
		criteria.addOrder(Order.desc("createTime"));
		criteria.add(conjunction);
		@SuppressWarnings("unchecked")
		List<T> list = criteria.list();
		return list;
	}

	@Override
	public boolean updateGiveAwayBySignId(String giveAway,String signId) throws Exception {
		
		StringBuilder together = new StringBuilder("update PLUS_HB_SIGNCONFIGURE ");
		together.append("set GIVE_AWAY = '").append(giveAway).append("' ");
		together.append("where SIGN_ID = '").append(signId).append("'");
		SQLQuery query = getSession().createSQLQuery(together.toString());
		query.executeUpdate();
		return true;
	}

}

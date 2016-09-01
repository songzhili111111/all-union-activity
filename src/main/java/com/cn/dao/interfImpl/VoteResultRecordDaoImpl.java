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
import com.cn.dao.interf.VoteResultRecordDao;
import com.cn.entity.record.VoteResultRecord;
/**
 * 
 * @author songzhili
 * 2016年7月18日下午3:25:25
 */
@Repository("voteResultRecordDao")
@Transactional
public class VoteResultRecordDaoImpl implements VoteResultRecordDao {

	@Autowired
	public SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public boolean add(VoteResultRecord result) throws Exception {
		if(result == null){
			return false;
		}
		getSession().save(result);
		return false;
	}

	@Override
	public boolean delete() throws Exception {
		return false;
	}

	@Override
	public boolean update(VoteResultRecord result) throws Exception {
		return false;
	}
    
	
	@Override
	public VoteResultRecord obtain(String id) throws Exception {
		
		if(Utils.isEmpty(id)){
			return new VoteResultRecord();
		}
		VoteResultRecord result = (VoteResultRecord)getSession().get(VoteResultRecord.class, id);
		return result;
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
		Criteria criteria = getSession().createCriteria(VoteResultRecord.class);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}

	@Override
	public int countByCriteria(String openId,String themeId) throws Exception {
		
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("openId", openId, MatchMode.ANYWHERE));
		conjunction.add(Restrictions.like("themeId", themeId, MatchMode.ANYWHERE));
		Criteria criteria = getSession().createCriteria(VoteResultRecord.class);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		criteria.add(conjunction);
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}

	@Override
	public boolean deleteByOpenId(String openId) throws Exception {
		
		StringBuilder together = new StringBuilder("delete from PLUS_HB_VOTERESULTRECORD");
		together.append(" where OPEN_ID = '").append(openId).append("'");
		SQLQuery query = getSession().createSQLQuery(together.toString());
		query.executeUpdate();
		return true;
	}
}

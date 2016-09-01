package com.cn.dao.interfImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.common.Utils;
import com.cn.dao.interf.WheelResultRecordDao;
import com.cn.entity.record.WheelResultRecord;
/**
 * 幸运盘结果记录DAO
 * @author songzhili
 * 2016年7月19日上午9:51:03
 */
@Repository("wheelResultRecordDao")
@Transactional
public class WheelResultRecordDaoImpl implements WheelResultRecordDao {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public boolean add(WheelResultRecord result) throws Exception {
		if(result == null){
			return false;
		}
		getSession().save(result);
		return true;
	}

	@Override
	public boolean delete(String id) throws Exception {
		if(Utils.isEmpty(id)){
			return false;
		}
		StringBuilder together = new StringBuilder("delete from PLUS_HB_WHEELRESULTRECORD ");
		together.append("where result_id = '").append(id).append("'");
		SQLQuery query = getSession().createSQLQuery(together.toString());
		query.executeUpdate();
		return true;
	}

	@Override
	public boolean update(WheelResultRecord result) throws Exception {
		
		if(result == null){
			return false;
		}
		getSession().update(result);
		return true;
	}

	@Override
	public WheelResultRecord obtain(String id) throws Exception {
		
		if(Utils.isEmpty(id)){
			return new WheelResultRecord();
		}
		WheelResultRecord result = (WheelResultRecord)
				getSession().get(WheelResultRecord.class, id);
		return result;
	}

	@Override
	public <T> List<T> obtainList(Class<T> clazz, Integer currentPage,
			Integer pageSize) throws Exception {
		
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
	public <T> List<T> obtainListByOpenId(Class<T> clazz, String openId,
			String activityId,String appId) throws Exception {
		
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("openId", openId, MatchMode.ANYWHERE));
		if(!Utils.isEmpty(activityId)){
			conjunction.add(Restrictions.like("activityId", activityId, MatchMode.ANYWHERE));
		}
		if(!Utils.isEmpty(appId)){
			conjunction.add(Restrictions.like("appId", appId, MatchMode.ANYWHERE));
		}
		Criteria criteria = getSession().createCriteria(clazz);
		criteria.add(conjunction);
		@SuppressWarnings("unchecked")
		List<T> list = criteria.list();
		return list;
	}

	@Override
	public boolean updateRemainNumberById(String resultId,boolean plus) throws Exception {
		StringBuilder together = new StringBuilder("update PLUS_HB_WHEELRESULTRECORD ");
		if(plus){
			together.append("set REMAIND_NUMBER = REMAIND_NUMBER+1 ");
		}else{
			together.append("set REMAIND_NUMBER = REMAIND_NUMBER-1 ");
		}
		together.append("where RESULT_ID ='").append(resultId).append("'");
		SQLQuery query = getSession().createSQLQuery(together.toString());
		query.executeUpdate();
		return true;
	}

	@Override
	public boolean updateRemainNumber(String openId, Integer remainNumber)
			throws Exception {
		
		StringBuilder together = new StringBuilder("update PLUS_HB_WHEELRESULTRECORD ");
		together.append("set REMAIND_NUMBER = ").append(remainNumber);
		together.append(" where OPEN_ID = '").append(openId).append("'");
		SQLQuery query = getSession().createSQLQuery(together.toString());
		query.executeUpdate();
		return true;
	}
}

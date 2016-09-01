package com.cn.dao.interfImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.dao.interf.AwardResultRecordDao;
import com.cn.entity.record.AwardResultRecord;
/**
 * 用户中奖结果记录表DAO
 * @author songzhili
 * 2016年7月21日上午9:28:35
 */
@Transactional
@Repository("awardResultRecordDao")
public class AwardResultRecordDaoImpl implements AwardResultRecordDao {
   
	@Autowired
	private SessionFactory sessionFactory;
	/****/
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public boolean add(AwardResultRecord result) throws Exception {
		if(result == null){
			return false;
		}
		getSession().save(result);
		return true;
	}

	@Override
	public boolean delete(String id) throws Exception {
		return false;
	}

	@Override
	public boolean update(AwardResultRecord result) throws Exception {
		return false;
	}

	@Override
	public AwardResultRecord obtain(String id) throws Exception {
		return null;
	}

	@Override
	public <T> List<T> obtainList(Class<T> clazz, Integer currentPage,
			Integer pageSize) throws Exception {
		return null;
	}

}

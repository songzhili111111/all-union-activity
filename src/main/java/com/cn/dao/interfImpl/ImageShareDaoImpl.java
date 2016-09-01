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
import com.cn.dao.interf.ImageShareDao;
import com.cn.entity.imageShare.ImageShareConfigure;
/**
 * 图片分享DAO实现
 * @author songzhili
 * 2016年8月11日上午9:14:21
 */
@Transactional
@Repository("imageShareDao")
public class ImageShareDaoImpl implements ImageShareDao{
    
	@Autowired
	private SessionFactory sessionFactory;
	/****/
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public boolean add(ImageShareConfigure configure) throws Exception {
		getSession().save(configure);
		return true;
	}

	@Override
	public boolean delete(String shareId) throws Exception {
		if (Utils.isEmpty(shareId)) {
			return false;
		}
		ImageShareConfigure configure = obtain(shareId);
		getSession().delete(configure);
		return true;
	}

	@Override
	public boolean update(ImageShareConfigure configure) throws Exception {
		getSession().update(configure);
		return true;
	}

	@Override
	public ImageShareConfigure obtain(String shareId) throws Exception {
		
		if(Utils.isEmpty(shareId)){
			new ImageShareConfigure();
		}
		ImageShareConfigure configure = 
				(ImageShareConfigure)getSession().get(ImageShareConfigure.class, shareId);
		return configure;
	}

	@Override
	public <T> List<T> obtainList(Class<T> clazz,Integer page,Integer pageSize) throws Exception {
		
		Criteria criteria = getSession().createCriteria(clazz);
		criteria.setFirstResult(page*pageSize);
		criteria.setMaxResults(pageSize);
		criteria.addOrder(Order.desc("createTime"));
		criteria.setCacheable(false);
		@SuppressWarnings("unchecked")
		List<T> list = criteria.list();
		return list;
	}

	@Override
	public int count() throws Exception {
		Criteria criteria = getSession().createCriteria(ImageShareConfigure.class);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}

	@Override
	public <T> List<T> obtainListByShareName(String shareName, Class<T> clazz,
			Integer page, Integer pageSize) throws Exception {
		
		Criteria criteria = getSession().createCriteria(clazz);
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("shareName", shareName, MatchMode.ANYWHERE));
		criteria.setFirstResult(page*pageSize);
		criteria.setMaxResults(pageSize);
		criteria.setCacheable(false);
		criteria.addOrder(Order.desc("createTime"));
		criteria.add(conjunction);
		@SuppressWarnings("unchecked")
		List<T> list = criteria.list();
		return list;
	}

	@Override
	public int countByShareName(String shareName) throws Exception {
		
		Criteria criteria = getSession().createCriteria(ImageShareConfigure.class);
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("shareName", shareName, MatchMode.ANYWHERE));
		criteria.add(conjunction);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}
}

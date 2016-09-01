package com.cn.dao.interfImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.common.Utils;
import com.cn.dao.interf.ImageInfoDao;
import com.cn.entity.fortuneWheel.ImageInfo;
/**
 * 转盘图片表DAO
 * @author songzhili
 * 2016年7月5日下午1:46:17
 */
@Repository("imageInfoDao")
@Transactional
public class ImageInfoDaoImpl implements ImageInfoDao {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	@Override
	public boolean addImageInfo(ImageInfo info) throws Exception {
		if(info == null){
			return false;
		}
		getSession().save(info);
		return true;
	}

	@Override
	public boolean deleteImageInfo(String id) throws Exception {
		if(Utils.isEmpty(id)){
			return false;
		}
		ImageInfo info = obtainImageInfo(id);
		if(info == null){
			return false;
		}
		getSession().update(info);
		return true;
	}

	@Override
	public boolean updateImageInfo(ImageInfo info) throws Exception {
		if(info == null){
			return false;
		}
		getSession().update(info);
		return true;
	}

	@Override
	public ImageInfo obtainImageInfo(String id) throws Exception {
		if(Utils.isEmpty(id)){
			return null;
		}
		ImageInfo info = (ImageInfo)getSession().get(ImageInfo.class, id);
		return info;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List obtainImageInfoList() throws Exception {
		Criteria criteria = getSession().createCriteria(ImageInfo.class);
		List list = criteria.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> obtainImageInfoListByImageRecord(Class<T> clazz,String imageRecord)throws Exception {
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("imageRecord", imageRecord, MatchMode.ANYWHERE));
		Criteria criteria = getSession().createCriteria(clazz);
		criteria.setCacheable(false);
		criteria.add(conjunction);
		List<T> list = criteria.list();
		return list;
	}

}

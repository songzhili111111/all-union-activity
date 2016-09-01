package com.cn.dao.interf;

import java.util.List;

import com.cn.entity.imageShare.ImageShareConfigure;

/**
 * 图片分享DAO
 * @author songzhili
 * 2016年8月11日上午9:10:40
 */
public interface ImageShareDao {
    
	public boolean add(ImageShareConfigure configure) throws Exception;
	/****/
	public boolean delete(String shareId)throws Exception;
	/****/
	public boolean update(ImageShareConfigure configure)throws Exception;
	/****/
	public ImageShareConfigure obtain(String shareId)throws Exception;
	/****/
	public <T> List<T> obtainList(Class<T> clazz,Integer page,Integer pageSize) throws Exception;
	/****/
	public <T> List<T> obtainListByShareName(String shareName,Class<T> clazz,Integer page,Integer pageSize) throws Exception;
	/****/
	public int countByShareName(String shareName)throws Exception;
	/****/
	public int count() throws Exception;
}

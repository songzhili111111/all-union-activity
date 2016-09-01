package com.cn.dao.interf;

import java.util.List;

import com.cn.entity.fortuneWheel.ImageInfo;

/**
 * 转盘图片表DAO
 * @author songzhili
 * 2016年7月5日下午1:42:22
 */
public interface ImageInfoDao {
  
	
	public boolean addImageInfo(ImageInfo info) throws Exception;
	/****/
	public boolean deleteImageInfo(String id)throws Exception;
	/****/
	public boolean updateImageInfo(ImageInfo info)throws Exception;
	/****/
	public ImageInfo obtainImageInfo(String id)throws Exception;
	/****/
	@SuppressWarnings("rawtypes")
	public List obtainImageInfoList() throws Exception;
	/****/
	public <T> List<T> obtainImageInfoListByImageRecord(Class<T> clazz,String imageRecord)throws Exception;
}

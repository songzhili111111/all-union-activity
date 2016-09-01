package com.cn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.entity.fortuneWheel.FortuneWheelConfigure;

/**
 * 
 * @author songzhili
 * 2016年7月5日上午9:58:56
 */	
public class Test {
   
	public static void main(String[] args) {
		 
		Map<String, String> mapOne = new HashMap<String, String>();
		mapOne.put("activityId", "activityId100");
		mapOne.put("activityName", "activityName");
		mapOne.put("awardSetup", "awardSetup");
		mapOne.put("activityExplain", "activityExplain");
		mapOne.put("imageUrl", "imageUrl");
		mapOne.put("activityLimit", "activityLimit");
		mapOne.put("imageName", "imageName");
		Map<String, String> mapTwo = new HashMap<String, String>();
		mapTwo.put("activityId", "12212");
		mapTwo.put("activityName", "activityName");
		mapTwo.put("awardSetup", "awardSetup");
		mapTwo.put("activityExplain", "activityExplain");
		mapTwo.put("imageUrl", "imageUrl");
		mapTwo.put("activityLimit", "activityLimit");
		mapTwo.put("imageName", "imageName");
		List<Map<String, String>> listMap = new ArrayList<Map<String,String>>();
		listMap.add(mapTwo);
		listMap.add(mapOne);
		Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String,String>>>();
		map.put("data", listMap);
		List<FortuneWheelConfigure> list =  converseMapToObject(FortuneWheelConfigure.class, map);
		for(FortuneWheelConfigure cc : list){
			System.out.println(cc.getActivityId());
			System.out.println(cc.getActivityExplain());
			System.out.println(cc.getActivityLimit());
			System.out.println(cc.getActivityName());
			System.out.println(cc.getAwardSetup());
			System.out.println(cc.getImageName());
			System.out.println(cc.getImageUrl());
			System.out.println("-------------------------------------------");
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> converseMapToObject(Class<T> clazz,Map<String, ? extends Object> map){
		
		Object object = map.get("data");
		List<T> listObject = new ArrayList<T>();
		try {
			if(object instanceof List){
				List<Object> objectOne = (List<Object>)object;
				Field [] fields = clazz.getDeclaredFields();
				for(Object objectTwo : objectOne){
					if(objectTwo instanceof Map){
						Map<String,Object> objectThree = (Map<String,Object>)objectTwo;
						T objectT = clazz.newInstance();
						for(Field field : fields){
							Object objectFour = objectThree.get(field.getName());
							if(objectFour != null){
								field.setAccessible(true);
								field.set(objectT, objectFour);
							}
						}
						listObject.add(objectT);
					}
				}
			}
		} catch (InstantiationException ex) {
			
		} catch (IllegalAccessException ex) {
		}
		return listObject;
	}
}

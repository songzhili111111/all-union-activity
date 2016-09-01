package com.cn.function.fortuneWheel;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.common.MessageAndCode;
import com.cn.common.Utils;
import com.cn.dao.interfImpl.FortuneWheelConfigureDaoImpl;
import com.cn.dao.interfImpl.ImageInfoDaoImpl;
import com.cn.entity.fortuneWheel.FortuneWheelConfigure;
import com.cn.entity.fortuneWheel.ImageInfo;

/**
 * @RestController 可以取代@Controller注解，可以省略返回值前面的@ResponseBody注解，但参数前面的@RequestBody注解无法省略
 * 幸运转盘
 * @author songzhili
 * 2016年6月30日下午1:36:30
 */
@Controller
@RequestMapping("/fortuneWheelConfigure")
public class FortuneWheelConfigureController {
   
	@Autowired
	private FortuneWheelConfigureDaoImpl fortuneWheelConfigureDao;
	/****/
	@Autowired
	private ImageInfoDaoImpl imageInfoDao;
	 
	@RequestMapping(value="/create",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String createFortuneWheelConfigureTwo(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		List<FortuneWheelConfigure> configures = Utils.converseMapToObject(FortuneWheelConfigure.class, source);
		if(configures != null && !configures.isEmpty()){
			try {
				for(FortuneWheelConfigure configure : configures){
					Date date = new Date();
					configure.setCreateTime(date);
					fortuneWheelConfigureDao.add(configure);
					/**添加转盘图片信息**/
					ImageInfo image = new ImageInfo();
					image.setImageRecord(configure.getActivityId());
					image.setImagePath(configure.getImageUrl());
					image.setImageCreated(date);
					image.setImageName(configure.getImageName());
					imageInfoDao.addImageInfo(image);
				}
			} catch (Exception ex) {
				node.put(MessageAndCode.RESPONSECODE, "001");
				node.put(MessageAndCode.RESPONSEMESSAGE, ex.getMessage());
				return node.toString();
			}
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deleteFortuneWheelConfigure(
			 @RequestParam(value="activityId",required=true) String activityId){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			fortuneWheelConfigureDao.deleteById(activityId);
		} catch (Exception ex) {
			node.put("message", "error");
			return node.toString();
		}
		node.put("message", "success");
		return node.toString();
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateFortuneWheelConfigure(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		List<FortuneWheelConfigure> configures = Utils.converseMapToObject(FortuneWheelConfigure.class, source);
		if(configures != null && !configures.isEmpty()){
			FortuneWheelConfigure configure = configures.get(0);
			String activityId = configure.getActivityId();
			if(Utils.isEmpty(activityId)){
				node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
				node.put(MessageAndCode.RESPONSEMESSAGE, "activityId是必填参数,不能为空,请检查你的参数!!!!!");
				return node.toString();
			}
			try {
				FortuneWheelConfigure configureOne = fortuneWheelConfigureDao.getById(activityId);
				if(configureOne != null){
					if(!Utils.isEmpty(configure.getActivityExplain())){
						configureOne.setActivityExplain(configure.getActivityExplain());
					}
					if(!Utils.isEmpty(configure.getActivityLimit())){
						configureOne.setActivityLimit(configure.getActivityLimit());
					}
					if(!Utils.isEmpty(configure.getActivityName())){
						configureOne.setActivityName(configure.getActivityName());
					}
					if(!Utils.isEmpty(configure.getAwardSetup())){
						configureOne.setAwardSetup(configure.getAwardSetup());
					}
					if(Utils.isEmpty(configure.getThirdPartyId())){
						configureOne.setThirdPartyId(configure.getThirdPartyId());
					}
					fortuneWheelConfigureDao.update(configureOne);
					List<ImageInfo> list = imageInfoDao.obtainImageInfoListByImageRecord(ImageInfo.class,activityId);
					if(list != null && !list.isEmpty()){
						ImageInfo info = list.get(0);
						if(!Utils.isEmpty(configure.getImageUrl())){
							info.setImagePath(configure.getImageUrl());
						}
						if(!Utils.isEmpty(configure.getImageName())){
							info.setImageName(configure.getImageName());
						}
						imageInfoDao.updateImageInfo(info);
					}
				}
			} catch (Exception ex) {
				ObjectNode nodeOne = mapper.createObjectNode();
				nodeOne.put(MessageAndCode.RESPONSECODE, "001");
				nodeOne.put(MessageAndCode.RESPONSEMESSAGE, ex.getMessage());
				return nodeOne.toString();
			}
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	
	@RequestMapping(value="/get",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String obtainFortuneWheelConfigure(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		String activityId = Utils.obtainValue("activityId", source);
		if(Utils.isEmpty(activityId)){
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			node.put(MessageAndCode.RESPONSEMESSAGE, "activityId是必填参数,不能为空,请检查你的参数!!!!!");
			return node.toString();
		}
		try {
			FortuneWheelConfigure configure = fortuneWheelConfigureDao.getById(activityId);
			if(configure != null){
				node.put("activityId", configure.getActivityId());
				node.put("activityName", configure.getActivityName());
				node.put("activityExplain", configure.getActivityExplain());
				node.put("activityLimit", configure.getActivityLimit());
				node.put("awardSetup", configure.getAwardSetup());
				node.put("thirdPartyId", configure.getThirdPartyId());
				List<ImageInfo> list = imageInfoDao.obtainImageInfoListByImageRecord(ImageInfo.class,activityId);
				if(list != null && !list.isEmpty()){
					ImageInfo info = list.get(0);
					node.put("imageUrl", info.getImagePath());
					node.put("imageName", info.getImageName());
				}
			}
		} catch (Exception ex) {
			ObjectNode nodeOne = mapper.createObjectNode();
			nodeOne.put(MessageAndCode.RESPONSECODE, "001");
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, ex.getMessage());
			return nodeOne.toString();
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	
	
	@RequestMapping(value="/list",produces="application/json;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public String obtainFortuneWheelConfigureList(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode array = mapper.createArrayNode();
		List<String> requestParams = Utils.obtainParams("activityName");
		Map<String, String> requestMap = Utils.obtianValues(source, requestParams);
		String activityName = requestMap.get("activityName");
		Integer currentPage = Integer.parseInt(requestMap.get("curragePage"));
		Integer pageSize = Integer.parseInt(requestMap.get("pageSize"));
		Integer totalNumber = 0;
		try {
			List list = null;
			if(!Utils.isEmpty(activityName)){
				list = fortuneWheelConfigureDao.getConfigureListByName(activityName,currentPage-1,pageSize);
				totalNumber = fortuneWheelConfigureDao.countByCriteria(activityName);
			}else{
				list = fortuneWheelConfigureDao.getConfigureList(currentPage-1,pageSize);
				totalNumber = fortuneWheelConfigureDao.count();
			}
			if(list != null && !list.isEmpty()){
				for(int index=0;index<list.size();index++){
					Object object = list.get(index);
					if(object instanceof FortuneWheelConfigure){
						FortuneWheelConfigure configure = (FortuneWheelConfigure)object;
					    ObjectNode node = mapper.createObjectNode();
					    node.put("activityExplain", configure.getActivityExplain());
					    node.put("activityId", configure.getActivityId());
					    node.put("activityLimit", configure.getActivityLimit());
					    node.put("activityName", configure.getActivityName());
					    node.put("awardSetup", configure.getAwardSetup());
					    node.put("thirdPartyId", configure.getThirdPartyId());
					    array.add(node);
					}
				}
			}
		} catch (Exception ex) {
			ObjectNode nodeTwo = mapper.createObjectNode();
			nodeTwo.put(MessageAndCode.RESPONSECODE, "001");
			nodeTwo.put(MessageAndCode.RESPONSEMESSAGE, ex.getMessage());
			return nodeTwo.toString();
		}
		ObjectNode nodeOne = mapper.createObjectNode();
		nodeOne.put("lists", array);
		nodeOne.put("total", totalNumber.toString());
		nodeOne.put("curragePage", requestMap.get("curragePage"));
		nodeOne.put("pageSize", requestMap.get("pageSize"));
		nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		nodeOne.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return nodeOne.toString();
	}
	
	
}

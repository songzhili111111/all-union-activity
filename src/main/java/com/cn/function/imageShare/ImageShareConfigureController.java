package com.cn.function.imageShare;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.common.JsonReader;
import com.cn.common.MessageAndCode;
import com.cn.common.Utils;
import com.cn.dao.interf.ImageShareDao;
import com.cn.entity.imageShare.ImageShareConfigure;

/**
 * 长图分享配置controller
 * @author songzhili
 * 2016年8月11日上午9:00:52
 */
@Controller
@RequestMapping("/imageShare")
public class ImageShareConfigureController {
	  
	@Autowired
	ImageShareDao imageShareDao;
	
	@RequestMapping(value="/create",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String add(@RequestBody Map<String, Object> source){
	     
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		List<ImageShareConfigure> configures = Utils.converseMapToObject(ImageShareConfigure.class, source);
		try {
			if (configures != null && !configures.isEmpty()) {
				for (ImageShareConfigure configure : configures) {
					configure.setCreateTime(new Date());
					imageShareDao.add(configure);
				}
			}
		} catch (Exception ex) {
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.EXCEPTIONCODE);
			node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.EXCEPTIONMESSAGE);
			return node.toString();
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	
	@RequestMapping(value="/delete",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delete(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		String shareId = Utils.obtainValue("shareId", source);
		if(Utils.isEmpty(shareId)){
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			node.put(MessageAndCode.RESPONSEMESSAGE, "shareId是必填参数,不能为空,请检查你的参数!!!!!");
			return node.toString();
		}
		try {
			imageShareDao.delete(shareId);
		} catch (Exception ex) {
			ObjectNode nodeOne = mapper.createObjectNode();
			nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.EXCEPTIONCODE);
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.EXCEPTIONMESSAGE);
			return nodeOne.toString();
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	
	@RequestMapping(value="/update",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String update(@RequestBody Map<String, Object> source) {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		List<ImageShareConfigure> configures = Utils.converseMapToObject(
				ImageShareConfigure.class, source);
		try {
			if (configures != null && !configures.isEmpty()) {
				for (ImageShareConfigure configure : configures) {
					String shareId = configure.getShareId();
					if(Utils.isEmpty(shareId)){
						node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
						node.put(MessageAndCode.RESPONSEMESSAGE, "shareId是必填参数,不能为空,请检查你的参数!!!!!");
						return node.toString();
					}
					ImageShareConfigure configureOne = imageShareDao.obtain(shareId);
					boolean update = false;
					if(!Utils.isEmpty(configure.getExpandParamOne())){
						configureOne.setExpandParamOne(configure.getExpandParamOne());
						update = true;
					}
					if(!Utils.isEmpty(configure.getExpandParamTwo())){
						configureOne.setExpandParamTwo(configure.getExpandParamTwo());
						update = true;
					}
					if(!Utils.isEmpty(configure.getQrCode())){
						configureOne.setQrCode(configure.getQrCode());
						update = true;
					}
					if(!Utils.isEmpty(configure.getQrCodeExplain())){
						configureOne.setQrCodeExplain(configure.getQrCodeExplain());
						update = true;
					}
					if(!Utils.isEmpty(configure.getShareName())){
						configureOne.setShareName(configure.getShareName());
						update = true;
					}
					if(!Utils.isEmpty(configure.getShareExplain())){
						configureOne.setShareExplain(configure.getShareExplain());
						update = true;
					}
					if(!Utils.isEmpty(configure.getActivityExplain())){
						configureOne.setActivityExplain(configure.getActivityExplain());
						update = true;
					}
					if(update){
						imageShareDao.update(configureOne);
					}
				}
			}
		} catch (Exception ex) {
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.EXCEPTIONCODE);
			node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.EXCEPTIONMESSAGE);
			return node.toString();
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	
	@RequestMapping(value="/get",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String obtain(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		String shareId = Utils.obtainValue("shareId", source);
		if(Utils.isEmpty(shareId)){
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			node.put(MessageAndCode.RESPONSEMESSAGE, "shareId是必填参数,不能为空,请检查你的参数!!!!!");
			return node.toString();
		}
		try {
			ImageShareConfigure configure = imageShareDao.obtain(shareId);
			if(configure != null){
				node.put("shareId", configure.getShareId());
				node.put("shareName", configure.getShareName());
				node.put("shareExplain", fromStringToArrayNode(configure.getShareExplain()));
				node.put("qrCode", configure.getQrCode());
				node.put("qrCodeExplain", configure.getQrCodeExplain());
				node.put("expandParamOne", configure.getExpandParamOne());
				node.put("expandParamTwo", configure.getExpandParamTwo());
				node.put("activityExplain", configure.getActivityExplain());
			}
		} catch (Exception ex) {
			ObjectNode nodeOne = mapper.createObjectNode();
			nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.EXCEPTIONCODE);
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.EXCEPTIONMESSAGE);
			return nodeOne.toString();
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	@RequestMapping(value="/list",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String obtianList(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode array = mapper.createArrayNode();
		ObjectNode nodeOne = mapper.createObjectNode();
		List<String> requestParams = Utils.obtainParams("shareName");
		Map<String, String> requestMap = Utils.obtianValues(source, requestParams);
		String currentPageOne = requestMap.get("curragePage");
		String pageSizeOne = requestMap.get("pageSize");
		String shareName = requestMap.get("shareName");
		if(Utils.isEmpty(currentPageOne) 
				|| Utils.isEmpty(pageSizeOne)){
			nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, "curragePage或pageSize是必填参数,不能为空,请检查你的参数!!!!!");
			return nodeOne.toString();
		}
		Integer currentPage = Integer.parseInt(currentPageOne);
		Integer pageSize = Integer.parseInt(pageSizeOne);
		Integer totalNumber = 0;
		try {
			List<ImageShareConfigure> configures = null;
			if(!Utils.isEmpty(shareName)){
				totalNumber = imageShareDao.countByShareName(shareName);
				configures = imageShareDao.obtainListByShareName(shareName,ImageShareConfigure.class, currentPage-1, pageSize);
			}else{
				totalNumber = imageShareDao.count();
				configures = imageShareDao.obtainList(ImageShareConfigure.class, currentPage-1, pageSize);
			}
			if(configures != null && !configures.isEmpty()){
				for(ImageShareConfigure configure : configures){
					ObjectNode node = mapper.createObjectNode();
					node.put("shareId", configure.getShareId());
					node.put("shareName", configure.getShareName());
					node.put("qrCode", configure.getQrCode());
					node.put("qrCodeExplain", configure.getQrCodeExplain());
					node.put("expandParamOne", configure.getExpandParamOne());
					node.put("expandParamTwo", configure.getExpandParamTwo());
					node.put("activityExplain", configure.getActivityExplain());
					array.add(node);
				}
			}
		} catch (Exception ex) {
			nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.EXCEPTIONCODE);
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.EXCEPTIONMESSAGE);
			return nodeOne.toString();
		}
		nodeOne.put("lists", array);
		nodeOne.put("total", totalNumber.toString());
		nodeOne.put("curragePage", currentPageOne);
		nodeOne.put("pageSize", pageSizeOne);
		nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		nodeOne.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return nodeOne.toString();
	}
	
	private ArrayNode fromStringToArrayNode(String source)
			throws JsonProcessingException, IOException{
		if(Utils.isEmpty(source)){
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode nodes = mapper.createArrayNode();
		JsonReader reader = new JsonReader();
		Object objectOne = reader.read(source);
		if(objectOne instanceof List){
			List<?> objectTwo = (List<?>)objectOne; 
			for(Object objectThree : objectTwo){
				if(objectThree instanceof Map){
					Map<?,?> objectFour = (Map<?,?>)objectThree;
					ObjectNode node = mapper.createObjectNode();
					for(Map.Entry<?, ?> entry: objectFour.entrySet()){
						if(entry.getKey() != null && entry.getValue() != null){
							node.put(entry.getKey().toString(), entry.getValue().toString());
						}
					}
					nodes.add(node);
				}
			}
		}
		return nodes;
	}
}

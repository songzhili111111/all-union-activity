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
import com.cn.dao.interf.FortuneWheelAwardDao;
import com.cn.entity.fortuneWheel.FortuneWheelAward;

/**
 * 幸运盘奖项controller
 * @author songzhili
 * 2016年7月1日上午10:49:20
 */
@Controller
@RequestMapping("/fortuneWheelAward")
public class FortuneWheelAwardController {
  
	@Autowired
	private FortuneWheelAwardDao fortuneWheelAwardDao;
	
	
	@RequestMapping(value="/create",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String createFortuneWheelAward(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		List<FortuneWheelAward> awards = Utils.converseMapToObject(FortuneWheelAward.class, source);
		try {
			if(awards != null && !awards.isEmpty()){
				for(FortuneWheelAward award : awards){
					award.setCreateTime(new Date());
					fortuneWheelAwardDao.add(award);
				}
			}
		} catch (Exception ex) {
			node.put(MessageAndCode.RESPONSECODE, "001");
			node.put(MessageAndCode.RESPONSEMESSAGE, ex.getMessage());
			return node.toString();
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	
	@RequestMapping(value="/delete",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deleteFortuneWheelAward(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		String awardId = Utils.obtainValue("awardId", source);
		if(Utils.isEmpty(awardId)){
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			node.put(MessageAndCode.RESPONSEMESSAGE, "awardId是必填参数,不能为空,请检查你的参数!!!!!");
			return node.toString();
		}
		try {
			fortuneWheelAwardDao.deleteById(awardId);
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
	public String updateFortuneWheelAward(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		List<FortuneWheelAward> awards = Utils.converseMapToObject(FortuneWheelAward.class, source);
		try {
		    if(awards != null && !awards.isEmpty()){
		    	for(FortuneWheelAward award : awards){
					String awardId = award.getAwardId();
					if(Utils.isEmpty(awardId)){
						node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
						node.put(MessageAndCode.RESPONSEMESSAGE, "awardId是必填参数,不能为空,请检查你的参数!!!!!");
						return node.toString();
					}
					FortuneWheelAward awardOne = fortuneWheelAwardDao.getById(awardId);
					boolean awardUpdate = false;
					if(!Utils.isEmpty(award.getAwardChance())){
						awardOne.setAwardChance(award.getAwardChance());
						awardUpdate = true;
					}
					if(!Utils.isEmpty(award.getAwardExplain())){
						awardOne.setAwardExplain(award.getAwardExplain());
						awardUpdate = true;
					}
					if(!Utils.isEmpty(award.getAwardName())){
						awardOne.setAwardName(award.getAwardName());
						awardUpdate = true;
					}
					if(!Utils.isEmpty(award.getExpandParam())){
						awardOne.setExpandParam(award.getExpandParam());
						awardUpdate = true;
					}
					if(!Utils.isEmpty(award.getAwardTurn())){
						awardOne.setAwardTurn(award.getAwardTurn());
						awardUpdate = true;
					}
					if(awardUpdate){
						fortuneWheelAwardDao.update(awardOne);
					}
				}
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
	
	@RequestMapping(value="/get",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String obtainFortuneWheelAward(
			@RequestParam(value="awardId",required=true) String awardId){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		FortuneWheelAward awardOne = null;
		try {
			awardOne = fortuneWheelAwardDao.getById(awardId);
		} catch (Exception ex) {
			node.put("message", "error");
			return node.toString();
		}
		if(awardOne != null){
			node.put("awardId", awardOne.getAwardId());
			node.put("awardName", awardOne.getAwardName());
			node.put("awardChance", awardOne.getAwardChance());
			node.put("awardExplain", awardOne.getAwardExplain());
			node.put("expandParam", awardOne.getExpandParam());
			node.put("activityId", awardOne.getActivityId());
		}else{
			node.put("message", "error");
		}
		return node.toString();
	}
	
	@RequestMapping(value="/list",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String obtainFortuneWheelAwardList(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode array = mapper.createArrayNode();
		Integer totalNumber = 0;
		String activityId = Utils.obtainValue("activityId", source);
		if(Utils.isEmpty(activityId)){
			ObjectNode nodeOne = mapper.createObjectNode();
			nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, "activityId是必填参数,不能为空,请检查你的参数!!!!!");
			return nodeOne.toString();
		}
		try {
			if(!Utils.isEmpty(activityId)){
				@SuppressWarnings("rawtypes")
				List list = fortuneWheelAwardDao.getAwardListByActivityId(activityId);
				totalNumber = fortuneWheelAwardDao.countByCriteria(activityId);
				if(list != null && !list.isEmpty()){
					for(int index=0;index<list.size();index++){
						Object object = list.get(index);
						if(object instanceof FortuneWheelAward){
							FortuneWheelAward awardOne = (FortuneWheelAward)object;
							ObjectNode node = mapper.createObjectNode();
							node.put("awardId", awardOne.getAwardId());
							node.put("awardName", awardOne.getAwardName());
							node.put("awardChance", awardOne.getAwardChance());
							node.put("awardExplain", awardOne.getAwardExplain());
							node.put("expandParam", awardOne.getExpandParam());
							node.put("activityId", awardOne.getActivityId());
							node.put("awardTurn", awardOne.getAwardTurn());
							array.add(node);
						}
					}
				}
			}
		} catch (Exception ex) {
			ObjectNode nodeOne = mapper.createObjectNode();
			nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.EXCEPTIONCODE);
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.EXCEPTIONMESSAGE);
			return nodeOne.toString();
		}
		ObjectNode nodeTwo = mapper.createObjectNode();
		nodeTwo.put("lists", array);
		nodeTwo.put("total", totalNumber.toString());
		nodeTwo.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		nodeTwo.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return nodeTwo.toString();
	}
}

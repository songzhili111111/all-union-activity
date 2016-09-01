package com.cn.function.fortuneWheel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.common.MessageAndCode;
import com.cn.common.Utils;
import com.cn.dao.interf.AwardResultRecordDao;
import com.cn.dao.interf.FortuneWheelAwardDao;
import com.cn.dao.interf.WheelResultRecordDao;
import com.cn.entity.fortuneWheel.FortuneWheelAward;
import com.cn.entity.record.AwardResultRecord;
import com.cn.entity.record.WheelResultRecord;

/**
 * 转盘后台控制controller
 * @author songzhili
 * 2016年7月19日下午4:14:53
 */
@Controller
@RequestMapping("/fortuneWheel")
public class FortuneWheelController {

	@Autowired
	private FortuneWheelAwardDao fortuneWheelAwardDao;
	@Autowired
	private WheelResultRecordDao wheelResultRecordDao;
	@Autowired
	private AwardResultRecordDao awardResultRecordDao;
	/****/
	private static final Logger logger = Logger.getLogger(FortuneWheelController.class);
	
	@RequestMapping(value="/controll")
	@ResponseBody
	public String controll(@RequestBody Map<String, Object> source){
		logger.debug(source);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		String activityId = Utils.obtainValue("activityId", source);
		String openId = Utils.obtainValue("openId", source);
		String appId = Utils.obtainValue("appId", source);
		if(Utils.isEmpty(appId) || Utils.isEmpty(openId)
				|| Utils.isEmpty(activityId)){
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			node.put(MessageAndCode.RESPONSEMESSAGE, "activityId或者appId或者openId是必填参数,不能为空,请检查你的参数!!!!!");
			return node.toString();
		}
		try {
			/**获取转盘奖项信息**/
			List<FortuneWheelAward> awardlist = fortuneWheelAwardDao.getAwardListByActivityId(activityId);
			if(awardlist == null || awardlist.isEmpty()){
				node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
				node.put(MessageAndCode.RESPONSEMESSAGE, "根据activityId:"+activityId+"查询数据库,没有对应的信息,请联系管理员!!!!");
				return node.toString();
			}
			Date date = new Date();
			List<WheelResultRecord> resultsOne = 
					wheelResultRecordDao.obtainListByOpenId(WheelResultRecord.class,openId, activityId,null);
			if(resultsOne == null || resultsOne.isEmpty()){//第一次
				WheelResultRecord resultOne = new WheelResultRecord();
				resultOne.setActivityId(activityId);
				resultOne.setAppId(appId);
				resultOne.setOpenId(openId);
				resultOne.setRemaindNumber(2);
				resultOne.setCreateTime(date);
				wheelResultRecordDao.add(resultOne);
				node = doFortuneWheel(awardlist,mapper);
				node.put("remainNumber", 3);
			}else{
				WheelResultRecord resultTwo = resultsOne.get(0);
				int remainNumber = resultTwo.getRemaindNumber();
				if(remainNumber > 0){//还有机会
					node = doFortuneWheel(awardlist,mapper);
					node.put("remainNumber", remainNumber);
					wheelResultRecordDao.updateRemainNumberById(resultTwo.getResultId(),false);
				}else{//没有机会了
					node.put("remainNumber", 0);
				}
			}
			JsonNode jsonNode = node.get("awardId");
			if(jsonNode != null){
				String awardId = jsonNode.getTextValue();
				if(!"0".equals(awardId)){//中奖
					AwardResultRecord awardResult = new AwardResultRecord();
					awardResult.setActivityId(activityId);
					awardResult.setAppId(appId);
					awardResult.setAwardId(awardId);
					awardResult.setOpenId(openId);
					awardResult.setCreateTime(date);
					awardResultRecordDao.add(awardResult);
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
		logger.debug(node.toString());
		return node.toString();
	}
	
	@RequestMapping(value="/updateRemainNumberByOpenId",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateRemainNumber(@RequestParam(value="openId",required=true) String openId
			,@RequestParam(value="remainNumber",required=true) String remainNumber){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			wheelResultRecordDao.updateRemainNumber(openId, Integer.parseInt(remainNumber));
		}catch (Exception e) {
			ObjectNode nodeOne = mapper.createObjectNode();
			nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.EXCEPTIONCODE);
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.EXCEPTIONMESSAGE);
			return nodeOne.toString();
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	
	@RequestMapping(value="/get",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String obtainWheelResultRecord(@RequestBody Map<String, Object> source){
		
		logger.debug(source);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		String activityId = Utils.obtainValue("activityId", source);
		String openId = Utils.obtainValue("openId", source);
		String appId = Utils.obtainValue("appId", source);
		if(Utils.isEmpty(appId) || Utils.isEmpty(openId)
				|| Utils.isEmpty(activityId)){
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			node.put(MessageAndCode.RESPONSEMESSAGE, "activityId或者appId或者openId是必填参数,不能为空,请检查你的参数!!!!!");
			return node.toString();
		}
		try {
			List<WheelResultRecord> resultsOne = 
					wheelResultRecordDao.obtainListByOpenId(WheelResultRecord.class,openId, activityId,null);
			if(resultsOne != null && !resultsOne.isEmpty()){
				Integer remainNumber = resultsOne.get(0).getRemaindNumber();
				if(remainNumber != null){
					node.put("remainNumber", remainNumber.toString());
				}else{
					node.put("remainNumber", "0");
				}
			}else{
				node.put("remainNumber", "3");
			}
		} catch (Exception ex) {
			ObjectNode nodeOne = mapper.createObjectNode();
			nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.EXCEPTIONCODE);
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.EXCEPTIONMESSAGE);
			return nodeOne.toString();
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		logger.debug(node.toString());
		return node.toString();
	}
	/**
	 * 返回详细的信息
	 * @param activityId
	 * @param mapper
	 * @return
	 * @throws Exception
	 */
	private ObjectNode doFortuneWheel(List<FortuneWheelAward> awardlist,ObjectMapper mapper) throws Exception{
		
		ObjectNode node = mapper.createObjectNode();
		awardlist = suppleAwards(awardlist);
		// 概率数组
		int size = awardlist.size();
		Integer[] awardChance = new Integer[size];
		for (int index = 0; index < size; index++) {
			awardChance[index] = Integer.parseInt(awardlist.get(index).getAwardChance());
		}
        Integer indexOne = obtainAwardIndex(awardChance);
        Integer angle = 360-(indexOne*30); 
        node.put("angle", Integer.toString(angle));
        FortuneWheelAward awardOne = awardlist.get(indexOne);
        node.put("awardId", awardOne.getAwardId());
        node.put("awardExplain", awardOne.getAwardExplain());
        node.put("expandParam", awardOne.getExpandParam());
		return node;
	}
	/**
	 * 填充其他非奖项内容
	 * @param awards
	 * @return
	 */
	private List<FortuneWheelAward> suppleAwards(List<FortuneWheelAward> awards){
		
		List<FortuneWheelAward> awardList = new ArrayList<FortuneWheelAward>();
		for(int index=0;index < 12; index++){
			FortuneWheelAward awardOne = new FortuneWheelAward();
			awardOne.setAwardChance("10");
			awardOne.setAwardId("0");
			awardOne.setExpandParam("什么都没有!!!!");
			awardOne.setAwardExplain("什么都没有!!!!");
			awardList.add(awardOne);
		}
		for(FortuneWheelAward award : awards){
			awardList.set(Integer.parseInt(award.getAwardTurn())-1, award);
		}
		return awardList;
	}
	/**
	 * 根据概率获取数组下标
	 * @param awardChance
	 * @return
	 * @throws ExceptionresultMap
	 */
	private Integer obtainAwardIndex(Integer[] awardChance) throws Exception{
		Integer result = 0;
		int sum = 0;// 概率数组的总概率精度
		for (int index = 0; index < awardChance.length; index++) {
			sum += awardChance[index];
		}
		Random random = new Random();
		for (int suffix = 0; suffix < awardChance.length; suffix++) {// 概率数组循环
			int randomNum = random.nextInt(sum);// 随机生成0到sum的整数
			if(randomNum < awardChance[suffix]){// 中奖
				result = suffix;
				break;
			} else {
				sum -= awardChance[suffix];
			}
		}
		return result;
	}
}
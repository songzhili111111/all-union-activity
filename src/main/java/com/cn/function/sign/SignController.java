package com.cn.function.sign;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.common.MessageAndCode;
import com.cn.common.Utils;
import com.cn.dao.interf.SignDao;
import com.cn.dao.interf.WheelResultRecordDao;
import com.cn.entity.record.WheelResultRecord;
import com.cn.entity.sign.SignConfigure;

/**
 * 签名controller
 * @author songzhili
 * 2016年7月18日下午4:01:20
 */
@Controller
@RequestMapping("/sign")
public class SignController {
  
	@Autowired
	public SignDao signDao;
	@Autowired
	private WheelResultRecordDao wheelResultRecordDao;
	/****/
	private static final Logger logger = Logger.getLogger(SignController.class);
	
	@ResponseBody
	@RequestMapping(value="/create",produces="application/json;charset=UTF-8")
	public String add(@RequestBody Map<String, Object> source){
		logger.debug(source);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		List<SignConfigure> signList = Utils.converseMapToObject(SignConfigure.class, source);
		try {
			if(signList != null && !signList.isEmpty()){
				DateFormat format = new SimpleDateFormat("yyyyMMdd");
				Date date = new Date();
				String currentDate = format.format(date);
				for(SignConfigure sign : signList){
					String openId = sign.getOpenId();
					String appId = sign.getAppId();
					if(Utils.isEmpty(appId) || Utils.isEmpty(openId)){
						node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
						node.put(MessageAndCode.RESPONSEMESSAGE, "appId或者openId是必填参数,不能为空,请检查你的参数!!!!!");
						return node.toString();
					}
					int repeat = 1;
					List<SignConfigure> signListOne = 
							signDao.obtainListByOpenId(SignConfigure.class, openId, appId, 0, 30);
					if(signListOne == null || signListOne.isEmpty()){//第一次签到
						node.put("signDays", obtainSignedDayList(null, true, currentDate, mapper));
					}else{
						SignConfigure signOne = signListOne.get(0);
						repeat = Utils.obtainSpaceDays(signOne.getSignTime(), currentDate);
						if(repeat == 0){//一天之内不能重复签到
							node.put("repeat", "1");
							if(!Utils.isEmpty(signOne.getGiveAway())){
								node.put("signSeven", "7");
							}
						}else{
							if(signListOne.size() == 6){//前六天是连续签到
								boolean giveAway = checkHasGiveAwayGift(openId, currentDate);
								if(!giveAway){//未赠送过礼物
									sign.setGiveAway("1");
									node.put("signSeven", "7");
								}
							}
						}
						/**签到日期列表**/
						if(repeat == 0){
							node.put("signDays", obtainSignedDayList(signListOne, false, null, mapper));
						}else{
							node.put("signDays", obtainSignedDayList(signListOne, false, currentDate, mapper));
						}
					}
					if(repeat > 0){//数据库记录插入
						sign.setSignTime(currentDate);
						sign.setCreateTime(date);
						signDao.add(sign);
						signGiveAwayFortuneWheelOpportunity(openId);
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
		logger.debug(node);
		return node.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteByOpenId",produces="application/json;charset=UTF-8")
	public String deleteByOpenId(@RequestParam(value="openId",required=true) String openId
			,@RequestParam(value="signTime",required=true) String signTime){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			signDao.deleteByOpenId(openId,signTime);
		} catch (Exception e) {
			ObjectNode nodeOne = mapper.createObjectNode();
			nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.EXCEPTIONCODE);
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.EXCEPTIONMESSAGE);
			return nodeOne.toString();
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	/**
	 * 获取用户的签到日期列表
	 * @param signListOne
	 * @param firstDay
	 * @param currentDate
	 * @param mapper
	 * @return
	 */
	private ArrayNode obtainSignedDayList(List<SignConfigure> signListOne,boolean firstDay,String currentDate,ObjectMapper mapper){
		
		ArrayNode array = mapper.createArrayNode();
		if(firstDay){
			array.add(currentDate);
			return array;
		}else{
			if(signListOne != null && !signListOne.isEmpty()){
				for(SignConfigure sign : signListOne){
					array.add(sign.getSignTime());
				}
			}
			if(currentDate != null){
				array.add(currentDate);
			}
			return array;
		}
	}
	/**
	 * 检查是否已经送过该用户奖品
	 * @param openId
	 * @return
	 * @throws Exception 
	 */
	private boolean checkHasGiveAwayGift(String openId,String currentDate) throws Exception{
		List<SignConfigure> signList = signDao.obtainListByGiveAway(SignConfigure.class, openId);
		if(signList != null && !signList.isEmpty()){
			int repeat = Utils.obtainSpaceDays(signList.get(0).getSignTime(), currentDate);
			if(repeat >= 30){//一次活动的时间应该不会大于30天
				return false;
			}
			return true;
		}
		return false;
	}
	/**
	 * 签到赠送转盘 转动机会一次,
	 * 前提是该用户已经使用过转盘 
	 * @param openId
	 * @throws Exception 
	 */
    private void signGiveAwayFortuneWheelOpportunity(String openId) throws Exception{
    	
    	List<WheelResultRecord> list = 
    			wheelResultRecordDao.obtainListByOpenId(WheelResultRecord.class, openId, null,null);
    	if(list != null && !list.isEmpty()){
    		wheelResultRecordDao.updateRemainNumberById(list.get(0).getResultId(), true);
    	}
    }
    
	@ResponseBody
	@RequestMapping(value="/list",produces="application/json;charset=UTF-8")
	public String obtainList(@RequestBody Map<String, Object> source){
		logger.debug(source);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		String openId = Utils.obtainValue("openId", source);
		String appId = Utils.obtainValue("appId", source);
		if(Utils.isEmpty(appId) || Utils.isEmpty(openId)){
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			node.put(MessageAndCode.RESPONSEMESSAGE, "appId或者openId是必填参数,不能为空,请检查你的参数!!!!!");
			return node.toString();
		}
		try {
			List<SignConfigure> signListOne = 
					signDao.obtainListByOpenId(SignConfigure.class, openId, appId, 0, 30);
			if(signListOne != null && !signListOne.isEmpty()){
				node.put("signDays", obtainSignedDayList(signListOne, false, null, mapper));
			}else{
				node.put("signDays", mapper.createArrayNode());
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
}

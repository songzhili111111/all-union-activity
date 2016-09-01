package com.cn.function.record;

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
import com.cn.dao.interf.VoteResultRecordDao;
import com.cn.dao.interf.VoteThemeChoiceDao;
import com.cn.dao.interf.WheelResultRecordDao;
import com.cn.entity.record.VoteResultRecord;
import com.cn.entity.record.WheelResultRecord;
import com.cn.entity.voteTools.VoteThemeChoice;

/**
 * 公共结果记录 controller
 * @author songzhili
 * 2016年7月18日下午3:40:51
 */
@Controller
@RequestMapping("/voteResultRecord")
public class VoteResultRecordController {
   
	@Autowired
	public VoteResultRecordDao voteResultRecordDao;
	@Autowired
	private VoteThemeChoiceDao voteThemeChoiceDao;
	@Autowired
	private WheelResultRecordDao wheelResultRecordDao;
	/****/
	private final static Logger logger = Logger.getLogger(VoteResultRecordController.class);
	
	@ResponseBody
	@RequestMapping(value="/create",produces="application/json;charset=UTF-8")
	public String add(@RequestBody Map<String, Object> source){
	    
		logger.debug(source);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		List<VoteResultRecord> results = Utils.converseMapToObject(VoteResultRecord.class, source);
		try {
			if(results != null && !results.isEmpty()){
				for(VoteResultRecord result : results){
					String openId = result.getOpenId();
					String themeId = result.getThemeId();
					String choiceId = result.getChoiceId();
					if(Utils.isEmpty(themeId) || Utils.isEmpty(openId)
							|| Utils.isEmpty(choiceId)){
						node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
						node.put(MessageAndCode.RESPONSEMESSAGE, "choiceId或者themeId或者openId是必填参数,不能为空,请检查你的参数!!!!!");
						return node.toString();
					}
					int count = voteResultRecordDao.countByCriteria(openId,themeId);
					if(count > 0){
						node.put("vote", "2");
					}else{
						node.put("vote", "1");
						result.setCreateTime(new Date());
						voteResultRecordDao.add(result);
						voteThemeChoiceDao.updateByChoiceId(choiceId);
						VoteThemeChoice choice = voteThemeChoiceDao.getById(choiceId);
						node.put("expandParamOne", choice.getExpandParamOne());
						voteGiveAwayFortuneWheelOpportunity(openId);
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
	
	/**
	 * 投票赠送转盘 转动机会一次,
	 * 前提是该用户已经使用过转盘 
	 * @param openId
	 * @throws Exception 
	 */
    private void voteGiveAwayFortuneWheelOpportunity(String openId) throws Exception{
    	
    	List<WheelResultRecord> list = 
    			wheelResultRecordDao.obtainListByOpenId(WheelResultRecord.class, openId, null,null);
    	if(list != null && !list.isEmpty()){
    		WheelResultRecord record = list.get(0);
    		wheelResultRecordDao.updateRemainNumberById(record.getResultId(), true);
    	}
    }
    
    @ResponseBody
	@RequestMapping(value="/deleteByOpenId",produces="application/json;charset=UTF-8")
    public String deleteByOpenId(@RequestParam(value="openId",required=true) String openId){
    	
    	ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			voteResultRecordDao.deleteByOpenId(openId);
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
    
	@ResponseBody
	@RequestMapping(value="/list",produces="application/json;charset=UTF-8")
	public String obtainList(@RequestBody Map<String, Object> source){
		logger.debug(source);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode nodeOne = mapper.createObjectNode();
		List<String> requestParams = Utils.obtainParams("");
		Map<String, String> requestMap = Utils.obtianValues(source, requestParams);
		String currentPageOne = requestMap.get("curragePage");
		String pageSizeOne = requestMap.get("pageSize");
		if(Utils.isEmpty(currentPageOne) 
				|| Utils.isEmpty(pageSizeOne)){
			nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, "curragePage或pageSize是必填参数,不能为空,请检查你的参数!!!!!");
			return nodeOne.toString();
		}
		Integer currentPage = Integer.parseInt(currentPageOne);
		Integer pageSize = Integer.parseInt(pageSizeOne);
		Integer totalNumber = 0;
		ArrayNode nodes = mapper.createArrayNode();
		try {
			List<VoteResultRecord> results = voteResultRecordDao.obtainList(VoteResultRecord.class, currentPage-1, pageSize);
			totalNumber = voteResultRecordDao.count();
			for(VoteResultRecord result : results){
				ObjectNode node = mapper.createObjectNode();
				node.put("resultId", result.getResultId());
				node.put("appId", result.getAppId());
				node.put("openId", result.getOpenId());
				node.put("choiceId", result.getChoiceId());
				node.put("themeId", result.getThemeId());
				nodes.add(node);
			}
		} catch (Exception ex) {
			nodeOne.put(MessageAndCode.RESPONSECODE, "001");
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, ex.getMessage());
			return nodeOne.toString();
		}
		ObjectNode nodeTwo = mapper.createObjectNode();
		nodeTwo.put("lists", nodes);
		nodeTwo.put("total", totalNumber.toString());
		nodeTwo.put("curragePage", currentPageOne);
		nodeTwo.put("pageSize", pageSizeOne);
		nodeTwo.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		nodeTwo.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return nodeTwo.toString();
	}
}









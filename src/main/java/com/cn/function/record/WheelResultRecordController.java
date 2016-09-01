package com.cn.function.record;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.common.MessageAndCode;
import com.cn.common.Utils;
import com.cn.dao.interf.WheelResultRecordDao;
import com.cn.entity.record.WheelResultRecord;

/**
 * 转盘结果记录controller
 * @author songzhili
 * 2016年7月19日上午10:30:30
 */
@Controller
@RequestMapping("/wheelResultRecord")
public class WheelResultRecordController {

	@Autowired
	private WheelResultRecordDao wheelResultRecordDao;
	
	@ResponseBody
	@RequestMapping(value="/create",produces="application/json;charset=UTF-8")
	public String add(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		List<WheelResultRecord> results = Utils.converseMapToObject(WheelResultRecord.class, source);
		try {
			if(results != null && !results.isEmpty()){
				for(WheelResultRecord result : results){
					List<WheelResultRecord> resultsOne = 
							wheelResultRecordDao.obtainListByOpenId(WheelResultRecord.class, 
									result.getOpenId(), result.getActivityId(),null);
					if(resultsOne == null || resultsOne.isEmpty()){
						result.setRemaindNumber(2);
						result.setCreateTime(new Date());
						wheelResultRecordDao.add(result);
					}else if(!resultsOne.isEmpty()){
						
					}
				}
			}
		} catch (Exception ex) {
			node.put(MessageAndCode.RESPONSECODE, "001");
			node.put(MessageAndCode.RESPONSEMESSAGE, ex.getMessage());
			return node.toString();
		}
		return "";
	}
}

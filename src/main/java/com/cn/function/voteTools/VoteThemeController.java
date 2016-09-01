package com.cn.function.voteTools;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.common.MessageAndCode;
import com.cn.common.Utils;
import com.cn.dao.interf.VoteThemeDao;
import com.cn.entity.voteTools.VoteTheme;

/**
 * 投票活动主题controller
 * @author songzhili
 * 2016年7月1日下午2:17:22
 */
@Controller
@RequestMapping("/voteTheme")
public class VoteThemeController {
     
	@Autowired
	private VoteThemeDao voteThemeDao;
	
	@ResponseBody
	@RequestMapping(value="/create",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String createVoteTheme(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			List<VoteTheme> themes = Utils.converseMapToObject(VoteTheme.class, source);
			if(themes != null && !themes.isEmpty()){
				for(VoteTheme theme : themes){
					theme.setCreateTime(new Date());
					voteThemeDao.add(theme);
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
	
	
	@ResponseBody
	@RequestMapping(value="/delete/{themeId}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public String deleteVodeTheme(@PathVariable String themeId){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			voteThemeDao.deleteById(themeId);
		} catch (Exception ex) {
			node.put("message", "error");
			return node.toString();
		}
		node.put("message", "success");
		return node.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public String deleteVodeThemeTwo(@RequestParam(value="themeId",required=true) String themeId){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			voteThemeDao.deleteById(themeId);
		} catch (Exception ex) {
			node.put("message", "error");
			return node.toString();
		}
		node.put("message", "success");
		return node.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/update",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String updateVoteTheme(@RequestBody Map<String, Object> source){
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			List<VoteTheme> themes = Utils.converseMapToObject(VoteTheme.class, source);
			if(themes != null && !themes.isEmpty()){
				for(VoteTheme theme : themes){
					if(!Utils.isEmpty(theme.getThemeId())){
						String themeId = theme.getThemeId();
						if(Utils.isEmpty(themeId)){
							node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
							node.put(MessageAndCode.RESPONSEMESSAGE, "themeId是必填参数,不能为空,请检查你的参数!!!!!");
							return node.toString();
						}
						VoteTheme themeOne = voteThemeDao.getObjectById(themeId);
						if(!Utils.isEmpty(theme.getEndTime())){
							themeOne.setEndTime(theme.getEndTime());
						}
						if(!Utils.isEmpty(theme.getNumberLimit())){
							themeOne.setNumberLimit(theme.getNumberLimit());
						}
						if(!Utils.isEmpty(theme.getSingleOrMulti())){
							themeOne.setSingleOrMulti(theme.getSingleOrMulti());
						}
						if(!Utils.isEmpty(theme.getStartTime())){
							themeOne.setStartTime(theme.getStartTime());
						}
						if(!Utils.isEmpty(theme.getThemeName())){
							themeOne.setThemeName(theme.getThemeName());
						}
						if(!Utils.isEmpty(theme.getThirdPartyId())){
							themeOne.setThirdPartyId(theme.getThirdPartyId());
						}
						if(!Utils.isEmpty(theme.getThemeExplain())){
							themeOne.setThemeExplain(theme.getThemeExplain());
						}
						voteThemeDao.update(themeOne);
					}
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
	
	@ResponseBody
	@RequestMapping(value="/get",produces="application/json;charset=UTF-8")
	public String obtainVoteThemeTwo(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		String themeId = Utils.obtainValue("themeId", source);
		if(Utils.isEmpty(themeId)){
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			node.put(MessageAndCode.RESPONSEMESSAGE, "themeId是必填参数,不能为空,请检查你的参数!!!!!");
			return node.toString();
		}
		try {
			if(!Utils.isEmpty(themeId)){
				VoteTheme theme = voteThemeDao.getObjectById(themeId);
				if(theme != null ){
					node.put("themeId", theme.getThemeId());
					node.put("themeName", theme.getThemeName());
					node.put("numberLimit", theme.getNumberLimit());
					node.put("startTime", theme.getStartTime());
					node.put("endTime", theme.getEndTime());
					node.put("singleOrMulti", theme.getSingleOrMulti());
					node.put("thirdPartyId", theme.getThirdPartyId());
					node.put("themeExplain", theme.getThemeExplain());
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
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="/list",produces="application/json;charset=UTF-8")
	public String obtainVoteThemeList(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode array = mapper.createArrayNode();
		List<String> requestParams = Utils.obtainParams("themeName");
		Map<String, String> requestMap = Utils.obtianValues(source, requestParams);
		String themeName = requestMap.get("themeName");
		Integer currentPage = Integer.parseInt(requestMap.get("curragePage"));
		Integer pageSize = Integer.parseInt(requestMap.get("pageSize"));
		Integer totalNumber = 0;
		try {
			List listOne = null;
			if(Utils.isEmpty(themeName)){
				listOne = voteThemeDao.getList(currentPage-1,pageSize);
				totalNumber = voteThemeDao.count();
			}else{
				listOne = voteThemeDao.getVoteThemeByThemeName(themeName,currentPage-1,pageSize);
				totalNumber = voteThemeDao.countByCriteria(themeName);
			}
			if(listOne != null && !listOne.isEmpty()){
				for(int index=0;index<listOne.size();index++){
					Object object = listOne.get(index);
					if(object instanceof VoteTheme){
						VoteTheme theme = (VoteTheme)object;
						ObjectNode node = mapper.createObjectNode();
						node.put("themeId", theme.getThemeId());
						node.put("themeName", theme.getThemeName());
						node.put("numberLimit", theme.getNumberLimit());
						node.put("startTime", theme.getStartTime());
						node.put("endTime", theme.getEndTime());
						node.put("singleOrMulti", theme.getSingleOrMulti());
						node.put("thirdPartyId", theme.getThirdPartyId());
						node.put("themeExplain", theme.getThemeExplain());
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

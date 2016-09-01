package com.cn.function.voteTools;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.common.MessageAndCode;
import com.cn.common.Utils;
import com.cn.dao.interfImpl.ImageInfoDaoImpl;
import com.cn.dao.interfImpl.VoteThemeChoiceDaoImpl;
import com.cn.entity.fortuneWheel.ImageInfo;
import com.cn.entity.voteTools.VoteThemeChoice;

/**
 * 投票活动主题 选项controller
 * @author songzhili
 * 2016年7月1日下午2:18:14
 */
@Controller
@RequestMapping("/voteThemeChoice")
public class VoteThemeChoiceController {
    
	@Autowired
	private VoteThemeChoiceDaoImpl voteThemeChoiceDao;
	
	@Autowired
	private ImageInfoDaoImpl imageInfoDao;
	
	@RequestMapping(value="/create",method=RequestMethod.POST,consumes="application/json",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addVoteThemeChoice(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			List<VoteThemeChoice> choices = Utils.converseMapToObject(VoteThemeChoice.class, source);
			if(choices != null && !choices.isEmpty()){
				for(VoteThemeChoice choice : choices){
					Date date = new Date();
					choice.setCreateTime(date);
					choice.setChoiceNumber(0);
					voteThemeChoiceDao.add(choice);
					/**添加投票图片信息**/
					ImageInfo image = new ImageInfo();
					image.setImageRecord(choice.getChoiceId());
					image.setImagePath(choice.getImageUrl());
					image.setImageCreated(date);
					image.setImageName(choice.getImageName());
					imageInfoDao.addImageInfo(image);
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
	
	@RequestMapping(value="/delete",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deleteVoteThemeChoice(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		String choiceId = Utils.obtainValue("choiceId", source);
		if(Utils.isEmpty(choiceId)){
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			node.put(MessageAndCode.RESPONSEMESSAGE, "choiceId是必填参数,不能为空,请检查你的参数!!!!!");
			return node.toString();
		}
		try {
			voteThemeChoiceDao.deleteById(choiceId);
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
	
	@RequestMapping(value="/update",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateVoteThemeChoice(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			List<VoteThemeChoice> choices = Utils.converseMapToObject(VoteThemeChoice.class, source);
			if(choices != null && !choices.isEmpty()){
				for(VoteThemeChoice choice : choices){
					String choiceId = choice.getChoiceId();
					if(Utils.isEmpty(choiceId)){
						node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
						node.put(MessageAndCode.RESPONSEMESSAGE, "choiceId是必填参数,不能为空,请检查你的参数!!!!!");
						return node.toString();
					}
					VoteThemeChoice choiceOne = voteThemeChoiceDao.getById(choiceId);
					boolean choiceUpdate = false;
					if(!Utils.isEmpty(choice.getChoiceName())){
						choiceOne.setChoiceName(choice.getChoiceName());
						choiceUpdate = true;
					}
					if(!Utils.isEmpty(choice.getChoiceType())){
						choiceOne.setChoiceType(choice.getChoiceType());
						choiceUpdate = true;
					}
					if(!Utils.isEmpty(choice.getChoiceValue())){
						choiceOne.setChoiceValue(choice.getChoiceValue());
						choiceUpdate = true;
					}
					if(!Utils.isEmpty(choice.getExpandParamOne())){
						choiceOne.setExpandParamOne(choice.getExpandParamOne());
						choiceUpdate = true;
					}
					if(!Utils.isEmpty(choice.getExpandParamTwo())){
						choiceOne.setExpandParamTwo(choice.getExpandParamTwo());
						choiceUpdate = true;
					}
					if(choiceUpdate){
						voteThemeChoiceDao.update(choiceOne);
					}
					String imageUrl = choice.getImageUrl();
					String imageName = choice.getImageName();
					if(!Utils.isEmpty(imageUrl)
							|| !Utils.isEmpty(imageName)){
						List<ImageInfo> list = imageInfoDao.obtainImageInfoListByImageRecord(ImageInfo.class,choiceId);
						if(list != null && !list.isEmpty()){
							ImageInfo info = list.get(0);
							if(!Utils.isEmpty(imageUrl)){
								info.setImagePath(imageUrl);
							}
							if(!Utils.isEmpty(imageName)){
								info.setImageName(imageName);
							}
							imageInfoDao.updateImageInfo(info);
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
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	
	@RequestMapping(value="/get",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String obtainVoteThemeChoice(@RequestBody Map<String, Object> source){
	    
		ObjectMapper mapper = new ObjectMapper();
		String choiceId = Utils.obtainValue("choiceId", source);
		ObjectNode node = mapper.createObjectNode();
		if(Utils.isEmpty(choiceId)){
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			node.put(MessageAndCode.RESPONSEMESSAGE, "choiceId是必填参数,不能为空,请检查你的参数!!!!!");
			return node.toString();
		}
		try {
			VoteThemeChoice choice = voteThemeChoiceDao.getById(choiceId);
			node.put("choiceId", choice.getChoiceId());
			node.put("choiceName", choice.getChoiceName());
			node.put("choiceType", choice.getChoiceType());
			node.put("choiceValue", choice.getChoiceValue());
			node.put("expandParamOne", choice.getExpandParamOne());
			node.put("expandParamTwo", choice.getExpandParamTwo());
			node.put("themeId", choice.getThemeId());
			List<ImageInfo> list = imageInfoDao.obtainImageInfoListByImageRecord(ImageInfo.class,choiceId);
			if(list != null && !list.isEmpty()){
				ImageInfo info = list.get(0);
				node.put("imageUrl", info.getImagePath());
				node.put("imageName", info.getImageName());
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
	@SuppressWarnings("rawtypes")
	public String obtianVoteThemeChoiceList(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode array = mapper.createArrayNode();
		String themeId = Utils.obtainValue("themeId", source);
		if(Utils.isEmpty(themeId)){
			ObjectNode nodeOne = mapper.createObjectNode();
			nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, "themeId是必填参数,不能为空,请检查你的参数!!!!!");
			return nodeOne.toString();
		}
		Integer totalNumber = 0;
		try {
	        if(!Utils.isEmpty(themeId)){
	    		List list = voteThemeChoiceDao.getChoiceListByThemeId(themeId);
	    		totalNumber = voteThemeChoiceDao.countByCriteria(themeId);
	    		if(list != null && !list.isEmpty()){
					for(int index=0;index<list.size();index++){
						Object object = list.get(index);
						if(object instanceof VoteThemeChoice){
							VoteThemeChoice choice = (VoteThemeChoice)object;
							ObjectNode node = mapper.createObjectNode();
							node.put("choiceId", choice.getChoiceId());
							node.put("choiceName", choice.getChoiceName());
							node.put("choiceType", choice.getChoiceType());
							node.put("choiceValue", choice.getChoiceValue());
							node.put("expandParamOne", choice.getExpandParamOne());
							node.put("expandParamTwo", choice.getExpandParamTwo());
							node.put("themeId", choice.getThemeId());
							node.put("choiceNumber", choice.getChoiceNumber().toString());
							List<ImageInfo> listOne = imageInfoDao.obtainImageInfoListByImageRecord(ImageInfo.class,choice.getChoiceId());
							if(listOne != null && !listOne.isEmpty()){
								ImageInfo info = listOne.get(0);
								node.put("imageUrl", info.getImagePath());
								node.put("imageName", info.getImageName());
							}
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
		ObjectNode node = mapper.createObjectNode();
		node.put("lists", array);
		node.put("total", totalNumber.toString());
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
}

package com.cn;

import org.springframework.web.client.RestTemplate;

import com.cn.entity.voteTools.VoteThemeChoice;


/**
 * 投票活动主题 选项
 * @author songzhili
 * 2016年7月4日下午3:33:12
 */
public class VoteThemeChoiceTest {
   
	public static final String REST_SERVICE_URI = "http://127.0.0.1:8080/all-union-activity";
	
	public static void createThemeChoice(){
		RestTemplate restTemplate = new RestTemplate();
		VoteThemeChoice choice = new VoteThemeChoice();
		choice.setChoiceName("三峡水库");
		choice.setChoiceType("01");
		choice.setChoiceValue("胜利");
		choice.setExpandParamOne("为了胜利");
		choice.setExpandParamTwo("为了胜利2");
		choice.setThemeId("ff80818155a565040155a5699c2b0001");
		String result = restTemplate.postForObject(REST_SERVICE_URI+"/voteThemeChoice/create/", choice, String.class);
		System.out.println(result);
	}
	
	public static void main(String[] args) {
		createThemeChoice();
	}
}

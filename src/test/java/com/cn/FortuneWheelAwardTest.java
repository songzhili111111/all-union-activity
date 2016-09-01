package com.cn;

import org.springframework.web.client.RestTemplate;

import com.cn.entity.fortuneWheel.FortuneWheelAward;

public class FortuneWheelAwardTest {

	public static final String REST_SERVICE_URI = "http://127.0.0.1:8080/all-union-activity";
	
	public static void createFortuneAward(){
		
		FortuneWheelAward award = new FortuneWheelAward();
		award.setActivityId("ff80818155b3e9d50155b3ea90070000");
		award.setAwardChance("2");
		award.setAwardExplain("这个是个大奖");
		award.setAwardName("三等奖");
		award.setExpandParam("等待扩展");
		
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.postForObject(REST_SERVICE_URI+"/fortuneAward/create/", award, String.class);
		System.out.println(result);
	}
	
    public static void updateFortuneAward(){
    	FortuneWheelAward award = new FortuneWheelAward();
    	RestTemplate restTemplate = new RestTemplate();
    	award.setAwardId("ff80818155b4a7250155b4a7bd5f0001");
    	award.setAwardName("北京市新街口外大街");
    	award.setExpandParam("北京市新街口外大街");
    	String result = restTemplate.postForObject(REST_SERVICE_URI+"/fortuneAward/update/", award, String.class);
        System.out.println(result);
    }
	public static void main(String[] args) {
		/*createFortuneAward();*/
		updateFortuneAward();
	}
}

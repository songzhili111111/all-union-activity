package com.cn;

import org.springframework.web.client.RestTemplate;

import com.cn.entity.fortuneWheel.FortuneWheelConfigure;

/**
 * 
 * @author songzhili
 * 2016年6月30日下午3:45:22
 */
public class FortuneWheelConfigureTest {
	
	 public static final String REST_SERVICE_URI = "http://127.0.0.1:8081/all-union-activity";
	 
	 public static void createConfigure(){
		 FortuneWheelConfigure configure = new FortuneWheelConfigure();
		 configure.setActivityExplain("欧洲杯02");
		 configure.setActivityLimit("众联想福02");
		 configure.setActivityName("打算盘02");
		 configure.setAwardSetup("计划02");
		 configure.setImageUrl("http://song.zhili.com/songzhili.jpg");
		 configure.setImageName("计划02");
		 
		 RestTemplate restTemplate = /*(RestTemplate)context.getBean("restTemplate");*/new RestTemplate();
		 configure =  restTemplate.postForObject(REST_SERVICE_URI+"/fortune/create/", configure, FortuneWheelConfigure.class);
		 System.out.println(configure.getActivityExplain());
	 }
	 
	 public static void updateConfigure(){
		 
		 FortuneWheelConfigure configure = new FortuneWheelConfigure();
		 configure.setActivityId("ff80818155b3e9d50155b3ea90070000");
		/* configure.setActivityName("这次是为修改准备测试的");
		 configure.setActivityExplain("这次是为修改准备测试的01");
		 configure.setActivityLimit("这次是为修改准备测试的02");*/
		 configure.setAwardSetup("北京市西城区新街口外大街28号院15号楼");
		 
		 RestTemplate restTemplate = new RestTemplate();
		 String result =  restTemplate.postForObject(REST_SERVICE_URI+"/fortune/update/", configure, String.class);
		 System.out.println(result);
	 }
	 
	 public static void main(String[] args) {
		 /*createConfigure();*/
		 updateConfigure();
	}
}

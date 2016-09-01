package com.cn;

import org.springframework.web.client.RestTemplate;

public class PlusTestImplTest {

	public static final String REST_SERVICE_URI = "http://127.0.0.1:8081/all-union-activity";
	
	
	public static void create(){
		
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.postForObject(REST_SERVICE_URI+"/test/add/", null, String.class);
	    System.out.println(result);
	}
	
	public static void list(){
		
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.postForObject(REST_SERVICE_URI+"/test/list/1&song", null, String.class);
	    System.out.println(result);
	}
	
	public static void main(String[] args) {
	    create();
		list();
	}
}

package com.cn;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.cn.entity.voteTools.VoteTheme;

/**
 * 
 * @author songzhili
 * 2016年7月1日下午3:38:56
 */
public class VoteThemeTest {
  
	public static final String REST_SERVICE_URI = "http://127.0.0.1:8080/all-union-activity";
	static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static void createTheme(){
		RestTemplate restTemplate = new RestTemplate();
		VoteTheme theme = new VoteTheme();
		theme.setEndTime(format.format(new Date()));
		theme.setNumberLimit("10");
		theme.setSingleOrMulti("0");
		theme.setStartTime(format.format(new Date()));
		theme.setThemeName("欧洲杯01");
		List<VoteTheme> themes = new ArrayList<VoteTheme>();
		themes.add(theme);
		String result = restTemplate.postForObject(REST_SERVICE_URI+"/voteTheme/create/", themes, String.class);
		System.out.println(result);
	}
	
	public static void updateTheme(){
		RestTemplate restTemplate = new RestTemplate();
		VoteTheme theme = new VoteTheme();
		theme.setThemeId("ff80818155a565040155a5699c2b0001");
		theme.setEndTime(format.format(new Date()));
		theme.setNumberLimit("10");
		theme.setSingleOrMulti("0");
		theme.setStartTime(format.format(new Date()));
		theme.setThemeName("欧洲杯02");
		String result = restTemplate.postForObject(REST_SERVICE_URI+"/voteTheme/update/", theme, String.class);
		System.out.println(result);
	}
	
	public static void deleteThemeById(){
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(REST_SERVICE_URI+"/voteTheme/delete/{themeId}", String.class, "ff80818155a565040155a5688fc80000");
		System.out.println(result);
	}
	
	public static void getThemeById(){
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(REST_SERVICE_URI+"/voteTheme/get/{themeId}", String.class, "ff80818155a565040155a5688fc80000");
		System.out.println(result);
	}
	public static void obtainList(){
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(REST_SERVICE_URI+"/voteTheme/list/", String.class);
		System.out.println(result);
	}
	public static void main(String[] args) {
		createTheme();
		/*updateTheme();
		obtainList();*/
		/*getThemeById();
		deleteThemeById();*/
	}
}

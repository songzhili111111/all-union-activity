package com.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.common.Utils;

/**
 * 
 * @author songzhili
 * 2016年7月11日上午10:32:21
 */
@Controller
@RequestMapping("/test")
public class PlusTestImplController {

	@Autowired
	private PlusTestImpl plusTestImpl;
	
	@RequestMapping(value = "/add")
	@ResponseBody
    public String add(){
    	
		PlusTest test = new PlusTest();
		test.setCreateDate(new Date());
		test.setName("song");
		plusTestImpl.add(test);
		return null;
    }
	
	@RequestMapping(value = "/list/{currentPage0}" ,produces="application/json;charet=UTF-8")
	@ResponseBody
	public String getList(@PathVariable String currentPage0){
		
		Integer currentPage = 1;
		String name = "song";
		if(!Utils.isEmpty(currentPage0) && currentPage0.contains("&")){
			int index = currentPage0.indexOf("&");
			currentPage = Integer.parseInt(currentPage0.substring(0,index));
			name = currentPage0.substring(index+1);
		}
		Integer pageSize = 10;
		@SuppressWarnings("rawtypes")
		List list = plusTestImpl.getList(currentPage-1,pageSize,name);
		if(list != null){
			ObjectMapper mapper = new ObjectMapper();
			ArrayNode array = mapper.createArrayNode();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(Object object : list){
				if(object instanceof PlusTest){
					PlusTest test = (PlusTest)object;
					ObjectNode node = mapper.createObjectNode();
					node.put("id", test.getId());
					node.put("name", test.getName());
					if(test.getCreateDate() != null){
						node.put("createDate", format.format(test.getCreateDate()));
					}
					array.add(node);
				}
			}
			return array.toString();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println("s&song".indexOf("&"));
		System.out.println("s&song".substring(0,1));
	}
}

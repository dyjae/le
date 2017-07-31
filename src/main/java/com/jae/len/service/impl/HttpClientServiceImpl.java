package com.jae.len.service.impl;

import org.springframework.stereotype.Service;

import com.jae.common.util.HttpClientUtil;
import com.jae.framework.entity.DataMap;
import com.jae.len.service.HttpClientService;

@Service("HttpClientService")
public class HttpClientServiceImpl implements HttpClientService{
	
	@SuppressWarnings("unchecked")
	public String searchWordService(String word){
		String url = "http://www.iciba.com/index.php";
		DataMap param = new DataMap();
		DataMap header = new DataMap();
		
		param.set("a", "getWordMean");
		param.set("c", "search");
		param.set("list", "1,3,4,8,9,12,13,15");
		param.set("word", word);
		param.set("_", "1501239351413");
		param.set("callback", "jsonp7");
		
		String resultStr = HttpClientUtil.httpClientGet(url, param ,header);
		return resultStr;
	};
	
}

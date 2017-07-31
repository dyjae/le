package com.jae.len.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jae.common.util.HttpClientUtil;
import com.jae.framework.entity.DataMap;
import com.jae.len.model.LeTran;

public class HttpClientServiceImplTest {

	@SuppressWarnings({ "unused", "unchecked" })
	@Test
	public void test() {
		String url = "http://www.iciba.com/index.php";
		DataMap param = new DataMap();
		param.set("a", "getWordMean");
		param.set("c", "search");
		param.set("list", "1,3,4,8,9,12,13,15");
		param.set("word", "english");
		param.set("_", "1501239351413");
		param.set("callback", "jsonp7");
		DataMap header = new DataMap();
		String resultStr = HttpClientUtil.httpClientGet(url, param ,header);
		
		resultStr = resultStr.replace("jsonp7(", "");
		resultStr = resultStr.substring(0, resultStr.length()-1);
		 
		JSONObject resultObject = JSON.parseObject(resultStr);
		JSONObject baseInfo = resultObject.getJSONObject("baesInfo");
		
		
		String wordName = baseInfo.getString("word_name");
		
		JSONArray symbols = baseInfo.getJSONArray("symbols");
		
		JSONObject phInfo = (JSONObject) symbols.get(0);
		
		String phEn = phInfo.getString("ph_en");
		String phAm = phInfo.getString("ph_am");
		String phOther = phInfo.getString("ph_other");
		String phEnMp3 = phInfo.getString("ph_en_mp3");
		String phAmMp3 = phInfo.getString("ph_am_mp3");
		String phOtherMp3 = phInfo.getString("ph_tts_mp3");
		JSONArray parts = phInfo.getJSONArray("parts");
		
		List<LeTran> trans = new ArrayList<LeTran>();
		for(JSONObject object : parts.toJavaList(JSONObject.class)){
			LeTran tran = new LeTran();
			String part = object.getString("part");
			String means = object.getString("means");
			tran.setPart(part);
			tran.setMeans(means);
			trans.add(tran);
		}
		
	}
	
}

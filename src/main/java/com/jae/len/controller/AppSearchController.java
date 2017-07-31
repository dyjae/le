package com.jae.len.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jae.framework.entity.DataMap;
import com.jae.framework.entity.ResultVo;
import com.jae.len.constants.UrlConstants;
import com.jae.len.model.LePhonetic;
import com.jae.len.model.LeTran;
import com.jae.len.model.LeWord;
import com.jae.len.service.HttpClientService;
import com.jae.len.service.LePhoneticService;
import com.jae.len.service.LeTranService;
import com.jae.len.service.LeWordService;

@Controller
@RequestMapping(UrlConstants.APP)
public class AppSearchController {
	
	@Autowired
	LeWordService wordService;
	
	@Autowired
	LeTranService tranService;
	
	@Autowired
	LePhoneticService phoneticService;
	
	@Autowired
	HttpClientService httpClientService;
	
	@RequestMapping(value = UrlConstants.APP_SEARCH_GET,method = RequestMethod.GET)
	@ResponseBody
	public ResultVo searchWord(
			@PathVariable("word")String word
			){
		ResultVo result = new ResultVo();
		if(StringUtils.isEmpty(word)){
			result.setErrMsg("请输入单词！");
		}
		
		LeWord wordResult = wordService.getByPropertySingle("word", word);
		if(wordResult != null ){
			result.setResults(convertWord(wordResult));
			return result;
		}
		
		String searchResult = httpClientService.searchWordService(word);
		String resultStr = searchResult.replace("jsonp7(", "");
		resultStr = resultStr.substring(0, resultStr.length()-1);
		JSONObject resultObject = JSON.parseObject(resultStr);
		JSONObject baseInfo = resultObject.getJSONObject("baesInfo");
		int translate_type = baseInfo.getInteger("translate_type");
		if(translate_type == 1){
			String wordName = baseInfo.getString("word_name");
			
			LeWord wordSave = new LeWord(wordName);
			LeWord wordSaved = wordService.save(wordSave);

			JSONArray symbols = baseInfo.getJSONArray("symbols");
			JSONObject phInfo = (JSONObject) symbols.get(0);
			String phEn = phInfo.getString("ph_en");
			String phAm = phInfo.getString("ph_am");
			String phOther = phInfo.getString("ph_other");
			String phEnMp3 = phInfo.getString("ph_en_mp3");
			String phAmMp3 = phInfo.getString("ph_am_mp3");
			String phOtherMp3 = phInfo.getString("ph_tts_mp3");
			
			if(StringUtils.isNotEmpty(phEn)||StringUtils.isNotEmpty(phEnMp3)){
				LePhonetic phonetic = new LePhonetic();
				phonetic.setWord(wordSaved);
				phonetic.setTypeName("ph_en");
				phonetic.setPhonetic(phEn);
				phonetic.setPhMp3(phEnMp3);
				phonetic.setWordName(wordName);
				phoneticService.save(phonetic);
			}
			
			if(StringUtils.isNotEmpty(phAm)||StringUtils.isNotEmpty(phAmMp3)){
				LePhonetic phonetic = new LePhonetic();
				phonetic.setWord(wordSaved);
				phonetic.setTypeName("ph_am");
				phonetic.setPhonetic(phAm);
				phonetic.setPhMp3(phAmMp3);
				phonetic.setWordName(wordName);
				phoneticService.save(phonetic);
			}
			if(StringUtils.isNotEmpty(phOther)||StringUtils.isNotEmpty(phOtherMp3)){
				LePhonetic phonetic = new LePhonetic();
				phonetic.setWord(wordSaved);
				phonetic.setTypeName("ph_other");
				phonetic.setPhonetic(phOther);
				phonetic.setPhMp3(phOtherMp3);
				phonetic.setWordName(wordName);
				phoneticService.save(phonetic);
			}
			
			JSONArray parts = phInfo.getJSONArray("parts");
			List<DataMap> resultTrans = new ArrayList<DataMap>();
			for(JSONObject object : parts.toJavaList(JSONObject.class)){
				LeTran tran = new LeTran();
				DataMap reusltTrans = new DataMap();
				String part = object.getString("part");
				String means = object.getString("means");
				
				tran.setWord(wordSaved);
				tran.setPart(part);
				tran.setMeans(means);
				tran.setWordName(wordName);
				tranService.save(tran);
				
				reusltTrans.set("part", part);
				reusltTrans.set("means", means);
				resultTrans.add(reusltTrans);
			}
			result.setResults("word_name", wordName);
			result.setResults("ph_en", phEn);
			result.setResults("ph_am", phAm);
			result.setResults("ph_other", phOther);
			result.setResults("ph_en_mp3", phEnMp3);
			result.setResults("ph_am_mp3", phAmMp3);
			result.setResults("ph_other_mp3", phOtherMp3);
			result.setResults("trans", resultTrans);
		}else{
			result.setFalseInfo("暂无此翻译", -1);
		}
		return result;
	}
	
	private DataMap convertWord(LeWord data){
		DataMap result = new DataMap();
		result.set("word_name", data.getWord());
		List<LeTran> trans = data.getTans();
		List<DataMap> transResult = new ArrayList<DataMap>();
		for(LeTran tran : trans){
			DataMap dataMap = new DataMap();
			dataMap.set("part", tran.getPart());
			dataMap.set("means", tran.getMeans());
			transResult.add(dataMap);
		}
		result.set("trans", transResult);
		List<LePhonetic> phonetics = data.getPhonetics();
		for(LePhonetic phonetic : phonetics){
			String typeName = phonetic.getTypeName();
			result.set(typeName, phonetic.getPhonetic());
			result.set(typeName+"_mp3", phonetic.getPhMp3());
		}
		return result;
	}
	
}

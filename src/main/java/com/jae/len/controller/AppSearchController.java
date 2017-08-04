package com.jae.len.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jae.framework.entity.DataMap;
import com.jae.framework.entity.ResultVo;
import com.jae.len.constants.UrlConstants;
import com.jae.len.model.LePhonetic;
import com.jae.len.model.LeTran;
import com.jae.len.model.LeUser;
import com.jae.len.model.LeUserWord;
import com.jae.len.model.LeWord;
import com.jae.len.service.HttpClientService;
import com.jae.len.service.LePhoneticService;
import com.jae.len.service.LeTranService;
import com.jae.len.service.LeUserService;
import com.jae.len.service.LeUserWordService;
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
	
	@Autowired
	LeUserService UserService;

	@Autowired
	LeUserWordService userWordService;
	
	@RequestMapping(value = UrlConstants.APP_SEARCH_GET,method = RequestMethod.GET)
	@ResponseBody
	public ResultVo searchWord(
			@PathVariable("word")String word,
			@RequestParam(value = "mobile",required=false)String mobile
			){
		ResultVo result = new ResultVo();
		if(StringUtils.isEmpty(word)){
			result.setErrMsg("请输入单词！");
		}
		LeWord wordResult = wordService.getByPropertySingle("word", word);
		if(wordResult == null ){
			String searchResult = httpClientService.searchWordService(word);
			String resultStr = searchResult.replace("jsonp7(", "");
			resultStr = resultStr.substring(0, resultStr.length()-1);
			JSONObject resultObject = JSON.parseObject(resultStr);
			JSONObject baseInfo = resultObject.getJSONObject("baesInfo");
			int translate_type = baseInfo.getInteger("translate_type");
			if(translate_type == 1){
				String wordName = baseInfo.getString("word_name");
				LeWord wordSave = new LeWord(wordName);
				wordResult = wordService.save(wordSave);
				JSONArray symbols = baseInfo.getJSONArray("symbols");
				JSONObject phInfo = (JSONObject) symbols.get(0);
				String phEn = phInfo.getString("ph_en");
				String phAm = phInfo.getString("ph_am");
				String phOther = phInfo.getString("ph_other");
				String phEnMp3 = phInfo.getString("ph_en_mp3");
				String phAmMp3 = phInfo.getString("ph_am_mp3");
				String phOtherMp3 = phInfo.getString("ph_tts_mp3");
				List<LePhonetic> phonetics = new ArrayList<LePhonetic>();
				if(StringUtils.isNotEmpty(phEn)||StringUtils.isNotEmpty(phEnMp3)){
					LePhonetic phonetic = new LePhonetic();
					phonetic.setWord(wordResult);
					phonetic.setTypeName("ph_en");
					phonetic.setPhonetic(phEn);
					phonetic.setPhMp3(phEnMp3);
					phonetic.setWordName(wordName);
					phoneticService.save(phonetic);
					phonetics.add(phonetic);
				}
				if(StringUtils.isNotEmpty(phAm)||StringUtils.isNotEmpty(phAmMp3)){
					LePhonetic phonetic = new LePhonetic();
					phonetic.setWord(wordResult);
					phonetic.setTypeName("ph_am");
					phonetic.setPhonetic(phAm);
					phonetic.setPhMp3(phAmMp3);
					phonetic.setWordName(wordName);
					phoneticService.save(phonetic);
					phonetics.add(phonetic);
				}
				if(StringUtils.isNotEmpty(phOther)||StringUtils.isNotEmpty(phOtherMp3)){
					LePhonetic phonetic = new LePhonetic();
					phonetic.setWord(wordResult);
					phonetic.setTypeName("ph_other");
					phonetic.setPhonetic(phOther);
					phonetic.setPhMp3(phOtherMp3);
					phonetic.setWordName(wordName);
					phoneticService.save(phonetic);
					phonetics.add(phonetic);
				}
				JSONArray parts = phInfo.getJSONArray("parts");
				List<LeTran> trans = new ArrayList<LeTran>();
				for(JSONObject object : parts.toJavaList(JSONObject.class)){
					LeTran tran = new LeTran();
					String part = object.getString("part");
					String means = object.getString("means");
					tran.setWord(wordResult);
					tran.setPart(part);
					tran.setMeans(means);
					tran.setWordName(wordName);
					tranService.save(tran);
					trans.add(tran);
				}
				wordResult.setPhonetics(phonetics);
				wordResult.setTans(trans);
			}else{
				result.setFalseInfo("暂无此翻译", -1);
				return result;
			}
		}
		result.setResults(convertWord(wordResult));
		//保存搜索记录，后面考虑在另一线程中处理
		if(StringUtils.isEmpty(mobile)){//没有注册即为游客
			mobile = "13886499962";
		}
		LeUser user = UserService.getByPropertySingle("mobile", mobile);
		//强行注册，后面删掉
		if(user == null){
			user = new LeUser();
			user.setMobile(mobile);
			user = UserService.save(user);
		}
		LeUserWord record = null;
		try {
			record = userWordService.getByPropertysSingle(new String[]{"word","user"}, new Object[]{wordResult,user});
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(record == null){
			record = new LeUserWord();
			record.setWord(wordResult);
			record.setWordName(word);
			record.setUser(user);
			userWordService.save(record);
		}else if(record.getState() != 0){
			record.setState(0);
			userWordService.update(record);
		}
		return result;
	}
	
	private DataMap convertWord(LeWord data){
		DataMap result = new DataMap();
		result.set("word_name", data.getWord());
		List<LeTran> trans = data.getTans();
		List<DataMap> transResult = new ArrayList<DataMap>();
		if(trans != null){
			for(LeTran tran : trans){
				DataMap dataMap = new DataMap();
				dataMap.set("part", tran.getPart());
				dataMap.set("means", tran.getMeans());
				transResult.add(dataMap);
			}
		}
		result.set("trans", transResult);
		List<LePhonetic> phonetics = data.getPhonetics();
		if(phonetics != null){
			for(LePhonetic phonetic : phonetics){
				String typeName = phonetic.getTypeName();
				result.set(typeName, phonetic.getPhonetic());
				result.set(typeName+"_mp3", phonetic.getPhMp3());
			}
		}
		return result;
	}
	
}

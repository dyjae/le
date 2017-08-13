package com.jae.len.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jae.framework.entity.DataMap;
import com.jae.framework.entity.EntityPageQuery;
import com.jae.framework.entity.Pagination;
import com.jae.framework.entity.ResultVo;
import com.jae.len.constants.UrlConstants;
import com.jae.len.model.LePhonetic;
import com.jae.len.model.LeTran;
import com.jae.len.model.LeUser;
import com.jae.len.model.LeUserWord;
import com.jae.len.model.LeWord;
import com.jae.len.service.LeUserService;
import com.jae.len.service.LeUserWordService;


@Controller
@RequestMapping(UrlConstants.APP)
public class AppReciteController {
	
	@Autowired
	LeUserService userService;
	
	@Autowired
	LeUserWordService userWordService;
	
	@RequestMapping(value=UrlConstants.APP_WORD_POST,method=RequestMethod.POST)
	@ResponseBody
	public ResultVo getAllWord(
			@RequestParam(required=false) String token,
			@RequestParam(required=false,defaultValue="50") Integer num
			){
		ResultVo result = new ResultVo();
		LeUser user = new LeUser();
		if(StringUtils.isEmpty(token)){
			user = userService.getByPropertySingle("mobile", "13886499962");
		}else{
			user = userService.getByPropertySingle("token", token);
		}
		
		List<LeUserWord> userWords = new ArrayList<LeUserWord>();
		try {
			Criteria criteria = userWordService.createCriteria().add(Restrictions.eq("user", user)).add(Restrictions.eq("state", 0)).addOrder(Order.desc("rightTimes"));
			EntityPageQuery<LeUserWord>  ep = userWordService.createPageQueryModelByCriteria(criteria);
			ep.setPage(0);
			ep.setRows(num);
			Pagination<LeUserWord> pg =ep.query();
			userWords = pg.getRows();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DataMap results = new DataMap();
		convertUserWords(userWords,results);
		result.setResults(results);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value=UrlConstants.APP_RECITE_POST,method=RequestMethod.POST)
	public ResultVo addRecite(
			@RequestParam(required=false) String token,
			@RequestParam(required=false) String type,
			@RequestParam(required=false) Integer num,
			@PathVariable(required=true) String word
			){
		ResultVo result = new ResultVo();
		LeUser user = new LeUser();
		if(StringUtils.isEmpty(token)){
			user = userService.getByPropertySingle("mobile", "13886499962");
		}else{
			user = userService.getByPropertySingle("token", token);
		}
		
		try {
			LeUserWord userWord = userWordService.getByPropertysSingle(new String[]{"user","wordName"}, new Object[]{user,word});
			if(type.equals("add")){
				userWord.setTotaltimes(userWord.getTotaltimes()+1);
				userWord.setTolRightTimes(userWord.getTolRightTimes()+1);
				int rightTimes = userWord.getRightTimes()+num;
				userWord.setRightTimes(rightTimes);
				if(rightTimes == 10){
					userWord.setState(1);
					userWord.setRightTimes(0);
					userWord.setErrorTimes(0);
				}
			}else if(type.equals("sub")){
				userWord.setTotaltimes(userWord.getTotaltimes()+1);
				userWord.setTolErrorTimes(userWord.getTolRightTimes()+1);
				userWord.setErrorTimes(userWord.getErrorTimes()+num);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

	private DataMap convertUserWords(List<LeUserWord> datas, DataMap results) {
		if(datas.size()==0){
			return results;
		}
		LeUserWord data = datas.get(0);
		datas.remove(0);
		DataMap result = convertUserWord(data);
		results.set(data.getWordName(), result);
		//results.add(result);
		return convertUserWords(datas,results);
	}

	private DataMap convertUserWord(LeUserWord data) {
		DataMap result = new DataMap();
		//result.set("word_name", data.getWordName());
		LeWord word = data.getWord();
		List<LePhonetic> phonetics = word.getPhonetics();
		List<LeTran> trans = word.getTans();
		List<DataMap> transResult = new ArrayList<DataMap>();
		if(trans != null){
			for(LeTran tran : trans){
				DataMap dataMap = new DataMap();
				dataMap.set("part", tran.getPart());
				dataMap.set("means", tran.getMeans());
				transResult.add(dataMap);
			}
		}
		DataMap phoneticData = new DataMap();
		if(phonetics != null){
			for(LePhonetic phonetic : phonetics){
				String typeName = phonetic.getTypeName();
				phoneticData.set(typeName, phonetic.getPhonetic());
				phoneticData.set(typeName+"_mp3", phonetic.getPhMp3());
				//result.set(typeName, phonetic.getPhonetic());
				//result.set(typeName+"_mp3", phonetic.getPhMp3());
			}
		}
		DataMap times = new DataMap();
		times.set("state", data.getState());
		times.set("total_times", data.getTotaltimes());
		times.set("total_error_times", data.getTolErrorTimes());
		times.set("total_right_times", data.getTolRightTimes());
		times.set("error_times", data.getErrorTimes());
		times.set("right_times", data.getRightTimes());
		times.set("updateTm", data.getUpdateTm());
		
		
		result.set("trans", transResult);
		result.set("phonetics", phoneticData);
		result.set("times", times);
		return result;
	}
	
	
	
	
	
/*	results{
		wordName : {
			trans : [
				{
					part:xxxx,
					means:xxx
				},
				{
					part:xxxx,
					means:xxx
				}
			] ,
			phonetics : {
				xxx : ddd
				
			},
			times {
				STATE : 11,
				TOTAL_TIMES : 0,
				TOLTAL_ERROR_TIMES : 0,
				TOTAL_RIGHT_TIMES : 1,
				ERROR_TIMES : 2,
				rightTimes : 3,
				updateTm : dfsdfsd,
			}
		
		},
		wordName : {
			trans : [
				{
					part:xxxx,
					means:xxx
				},
				{
					part:xxxx,
					means:xxx
				}
			] ,
			phonetics : {
				xxx : ddd
				
			},
			times {
				STATE : 11,
				TOTAL_TIMES : 0,
				TOLTAL_ERROR_TIMES : 0,
				TOTAL_RIGHT_TIMES : 1,
				ERROR_TIMES : 2,
				right_Times : 3,
				updateTm : dfsdfsd,
			}
		
		},
	}*/
	
	
	
	
}

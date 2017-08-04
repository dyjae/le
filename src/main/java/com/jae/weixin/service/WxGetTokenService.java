package com.jae.weixin.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jae.common.util.DateUtil;
import com.jae.common.util.HttpClientUtil;
import com.jae.framework.entity.DataMap;
import com.jae.weixin.constans.WXConstans;
import com.jae.weixin.model.WxToken;

@Service("WxGetTokenService")
public class WxGetTokenService {
	
	private String WX_TOKEN_URL = WXConstans.WX_ACCESS_TOKEN_URL;
	
	private String WX_IP_URL = WXConstans.WX_IP_LIST_URL;
	
	@Autowired
	private WxTokenService tokenService;
	
	public String getToken(){
		
		String resultToken = null;
		
		List<WxToken> tokens = tokenService.getAll();
		WxToken token = null;
		
		if(tokens.size() != 0 && DateUtil.compareDate(tokens.get(0).getEndTime(), new Date()) >0){
			resultToken = tokens.get(0).getToken();
		}else{
			token = new WxToken();
			String reslutStr = HttpClientUtil.httpClientGet(WX_TOKEN_URL, null, null);
			JSONObject resultJson = JSON.parseObject(reslutStr);
			//{"access_token":"ACCESS_TOKEN","expires_in":7200}
			//{"errcode":40013,"errmsg":"invalid appid"}
			resultToken = resultJson.getString("access_token");
			if(StringUtils.isEmpty(resultToken)){
				//报错时
				@SuppressWarnings("unused")
				String errcode =  resultJson.getString("errcode");
				@SuppressWarnings("unused")
				String errmsg =  resultJson.getString("errmsg");
			}
			String expiresIn =  resultJson.getString("expires_in");
			
			token.setToken(resultToken);
			token.setExpiresIn(Integer.parseInt(expiresIn));
			token.setEndTime(DateUtil.addHours(new Date(), Integer.parseInt(expiresIn)/3600));
			
			if(tokens.size() == 0){
				tokenService.save(token);
			}else{
				tokenService.update(token);
			}
			
		}
		return resultToken;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getIpList(){
		//access_token
		DataMap data = new DataMap();
		data.set("access_token", this.getToken());
		String resultJsonStr = HttpClientUtil.httpClientGet(WX_IP_URL, data, null);
		List<String> ipList = (List<String>) JSON.parse(resultJsonStr);
		return ipList;
	}
	
	
	
	

}

package com.jae.weixin.util;

import java.util.Arrays;

import com.jae.common.util.Sha1Util;
import com.jae.framework.entity.DataMap;
import com.jae.weixin.constans.WXConstans;

public class WxUtil {
	
	private static final String WX_TOKEN = WXConstans.WX_TOKEN;
	public static String checkWx(DataMap data){
		String timestamp = data.getString("timestamp");
		String nonce = data.getString("nonce");
		
		String[] params = new String[]{WX_TOKEN,timestamp,nonce};
		
		Arrays.sort(params);
		
		StringBuffer str = new StringBuffer();
		for(int i=0; i<params.length;i++){
			str.append(params[i]);
		}
		
		String sha1Str = Sha1Util.getSha1(str.toString());
		return sha1Str;
	}
	
	
}

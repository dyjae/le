package com.jae.weixin.constans;

public interface WXConstans {
	
	//https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1465199793_BqlKA
	/*公众平台接口域名说明
	开发者可以根据自己的服务器部署情况，选择最佳的接入点（延时更低，稳定性更高）。除此之外，可以将其他接入点用作容灾用途，当网络链路发生故障时，可以考虑选择备用接入点来接入。
	1. 通用域名(api.weixin.qq.com)，使用该域名将访问官方指定就近的接入点；
	2. 上海域名(sh.api.weixin.qq.com)，使用该域名将访问上海的接入点；
	3. 深圳域名(sz.api.weixin.qq.com)，使用该域名将访问深圳的接入点；
	4. 香港域名(hk.api.weixin.qq.com)，使用该域名将访问香港的接入点。*/
	public static final String WX_TOKEN = "JaeToken";
	
	//微信获取Get 获取ACCESS_TOKEN
	//调试工具地址
	//https://mp.weixin.qq.com/debug/cgi-bin/apiinfo?t=index&type=%E5%9F%BA%E7%A1%80%E6%94%AF%E6%8C%81&form=%E8%8E%B7%E5%8F%96access_token%E6%8E%A5%E5%8F%A3%20/token 
	public static final String  WX_APPID = "wx4109b1af44c81b80";

	public static final String  WX_SECRET = "83692f896675309a71c5baefb2535dcc";
	
	//https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
	public static final String WX_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WX_APPID+"&secret="+WX_SECRET;
	
	//https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN
	public static final String WX_IP_LIST_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
	
	public static final String WX_MSG_TYPE_TEXT = "text";
	public static final String WX_MSG_TYPE_IMAGE = "image";
	public static final String WX_MSG_TYPE_VOICE = "voice";
	public static final String WX_MSG_TYPE_VIDEO = "video";
	public static final String WX_MSG_TYPE_SHORTVIDEO = "shortvideo";
	public static final String WX_MSG_TYPE_LOCATION = "location";
	public static final String WX_MSG_TYPE_LINK = "link";
	
	
}

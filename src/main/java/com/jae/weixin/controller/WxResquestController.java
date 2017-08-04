package com.jae.weixin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jae.common.util.XMLUtil;
import com.jae.framework.entity.DataMap;
import com.jae.weixin.constans.UrlConstans;
import com.jae.weixin.constans.WXConstans;
import com.jae.weixin.pojo.WxResult;
import com.jae.weixin.util.WxUtil;

@Controller
@RequestMapping(UrlConstans.WX)
public class WxResquestController {
	
	@RequestMapping(value = UrlConstans.WX_REQUEST,method = RequestMethod.GET)
	public void wxRequestGet(  
			String signature,
			String timestamp,
			String nonce,
			String echostr,
			HttpServletResponse response
			){
		DataMap param = new DataMap();
		param.set("timestamp", timestamp);
		param.set("nonce", nonce);
		System.out.println(signature+"=========="+timestamp+"============"+nonce+"============"+echostr);
		
		String shStr = WxUtil.checkWx(param);
		if(shStr.equals(signature)){
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.println(echostr);
				System.out.println(echostr);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				out.close();
			}
		}
	}
	
	@RequestMapping(value = UrlConstans.WX_REQUEST,method = RequestMethod.POST)
	public void wxRequestPost(  
			HttpServletRequest request,
			HttpServletResponse response
			){
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final String TEXT = WXConstans.WX_MSG_TYPE_TEXT;
		final String IMAGE = WXConstans.WX_MSG_TYPE_IMAGE;
		final String VOICE = WXConstans.WX_MSG_TYPE_VOICE;
		final String VIDEO = WXConstans.WX_MSG_TYPE_VIDEO;
		final String SHORTVIDEO = WXConstans.WX_MSG_TYPE_SHORTVIDEO;
		final String LOCATION = WXConstans.WX_MSG_TYPE_LOCATION;
		final String LINK = WXConstans.WX_MSG_TYPE_LINK;
		
		String result = null;
		WxResult wxResult = new WxResult();
		
		try {
			InputStream in = request.getInputStream();
			JSONObject json = XMLUtil.toJson(convertStreamToString(in));
			String toUserName = json.getString("ToUserName");
			String fromUserName = json.getString("FromUserName");
			String createTime = json.getString("CreateTime");
			String msgType = json.getString("MsgType");
			String msgId = json.getString("MsgId");
			System.out.println("toUserName============="+toUserName);
			System.out.println("fromUserName============="+fromUserName);
			System.out.println("createTime============="+createTime);
			System.out.println("msgType============="+msgType);
			System.out.println("msgId============="+msgId);
			wxResult.setToUserName(fromUserName);
			wxResult.setFromUserName(toUserName);
			wxResult.setCreateTime(new Date().getTime()+"");
			//DateUtil.getYmdTmsNum(new Date())
			
			if(TEXT.equals(msgType)){
				//文字类型
				@SuppressWarnings("unused")
				String content = json.getString("Content");
				wxResult.setMsgType(TEXT);
				wxResult.setContent("已经收到消息！");
				String jsonString = JSON.toJSONString(wxResult);
				System.out.println(jsonString);
				JSONObject resultJson = (JSONObject) JSON.toJSON(wxResult);
				result = XMLUtil.toXml(convertJson(resultJson));
				//result = WxXmlUtil.ObjectToXml(wxResult);
				System.out.println(result);
				
			}else if(IMAGE.equals(msgType)){
				//图片消息
				@SuppressWarnings("unused")
				String mediaId = json.getString("MediaId");
				@SuppressWarnings("unused")
				String picUrl = json.getString("PicUrl");
				System.out.println("图片");
			}else if(VIDEO.equals(msgType)||SHORTVIDEO.equals(msgType)){
				@SuppressWarnings("unused")
				String mediaId = json.getString("MediaId");
				//视频消息  视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
				//小视频消息
				@SuppressWarnings("unused")
				String thumbMediaId = json.getString("ThumbMediaId");
				System.out.println("视频");
			}else if(VOICE.equals(msgType)){
				@SuppressWarnings("unused")
				String mediaId = json.getString("MediaId");
				//语音消息
				@SuppressWarnings("unused")
				String format = json.getString("Format");
				//语音识别   开通语音识别后
				@SuppressWarnings("unused")
				String recognition = json.getString("Recognition");
				System.out.println("语音");
			}else if(LOCATION.equals(msgType)){
				//地理位置消息
				@SuppressWarnings("unused")
				String locationX = json.getString("Location_X"); //地理位置维度
				@SuppressWarnings("unused")
				String locationY = json.getString("Location_Y");	//	地理位置经度
				@SuppressWarnings("unused")
				String scale = json.getString("Scale");	//地图缩放大小
				@SuppressWarnings("unused")
				String label = json.getString("Label");	//地理位置信息
				System.out.println("地理位置");
			}else if(LINK.equals(msgType)){
				//链接消息
				@SuppressWarnings("unused")
				String title = json.getString("Title");
				@SuppressWarnings("unused")
				String description = json.getString("Description");
				@SuppressWarnings("unused")
				String url = json.getString("Url");
				System.out.println("链接");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	
	private JSONObject convertJson(JSONObject json) {
		JSONObject result =  new JSONObject() ;
		for(Iterator<String> iter=json.keySet().iterator();iter.hasNext();){
			String name=iter.next();
			if(json.get(name)!=null){
				result.put(captureName(name), json.get(name));
			}
		}
		
		return result;
		
	}

	public static String convertStreamToString(InputStream is) {      
        /*  
          * To convert the InputStream to String we use the BufferedReader.readLine()  
          * method. We iterate until the BufferedReader return null which means  
          * there's no more data to read. Each line will appended to a StringBuilder  
          * and returned as String.  
          */     
         BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
         StringBuilder sb = new StringBuilder();      
     
         String line = null;      
        try {      
            while ((line = reader.readLine()) != null) {      
                 sb.append(line + "\n");      
             }      
         } catch (IOException e) {      
             e.printStackTrace();      
         } finally {      
            try {      
                 is.close();      
             } catch (IOException e) {      
                 e.printStackTrace();      
             }      
         }      
     
        return sb.toString();      
     }
	
	
	//首字母大写    因为微信的xml里面首字母要大写，jsonfast又将首字母转成了小写，这里转回来
    public static String captureName(String name) {
   //     name = name.substring(0, 1).toUpperCase() + name.substring(1);
//        return  name;
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
	
}

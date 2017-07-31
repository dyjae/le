package com.jae.framework.util.json;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON工具
 * @author Jae
 *
 */
public class JSONUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 将实体对象转换成JSON格式的字符串
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj){
		String json = null;
		StringWriter writer = new StringWriter();
		JsonGenerator generator = null;
		try {
			generator = mapper.getFactory().createGenerator(writer);
			mapper.writeValue(generator, obj);
			json = writer.toString();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}finally{
			try {
				if(null != generator)
					generator.close();
				if(null != writer)
				    writer.close();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}	
		}
		return json;
	}
	
	/**
	 * JSON格式的字符串转成实体对象
	 * @param json
	 * @param valueType
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> valueType){
		try {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return null == json ? null : mapper.readValue(json, valueType);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
 
}

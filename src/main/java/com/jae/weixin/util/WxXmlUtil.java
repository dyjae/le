package com.jae.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jae.framework.entity.DataMap;
import com.thoughtworks.xstream.XStream;

public class WxXmlUtil {
	public static DataMap xmlToMap(InputStream in) throws DocumentException, IOException{
		DataMap result = new DataMap();
		SAXReader reader = new SAXReader();
		Document document = reader.read(in);
		
		Element root = document.getRootElement();
		
		@SuppressWarnings("unchecked")
		List<Element> elements = root.elements();
		
		for(Element e: elements){
			result.set(e.getName(), e.getText());
		}
		in.close();
		return result;
	}
	
	public static String ObjectToXml(Object object){
		XStream xStream = new XStream();
		xStream.alias("xml", object.getClass());
		return xStream.toXML(object);
	}
}

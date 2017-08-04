package com.jae.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public final class XMLUtil {

	private static Logger logger = LoggerFactory.getLogger(XMLUtil.class);

	/**
	 * 把XML按照给定的XSL进行转换，返回转换后的值
	 * 
	 * @param xml
	 *            xml
	 * @param xsl
	 *            xsl
	 * @return
	 * @throws Exception
	 */
	public static String xml2xsl(String xml, URL xsl) throws Exception {
		if (StringUtils.isEmpty(xml)) {
			throw new Exception("xml string is empty");
		}
		if (xsl == null) {
			throw new Exception("xsl string is empty");
		}

		StringWriter writer = new StringWriter();
		Source xmlSource = null;
		Source xslSource = null;
		Result result = null;

		try {
			xmlSource = new StreamSource(new StringReader(xml));
			xslSource = new StreamSource(xsl.openStream());
			result = new StreamResult(writer);

			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xslSource);
			trans.transform(xmlSource, result);
			return writer.toString();
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			writer.close();
			writer = null;
			xmlSource = null;
			xslSource = null;
			result = null;
		}
	}

	/**
	 * 把XML按用户定义好的XSL样式进行输出
	 * 
	 * @param xmlFilePath
	 *            XML文档
	 * @param xsl
	 *            XSL样式
	 * @return 样式化后的字段串
	 */
	public static String xml2xsl(String xmlFilePath, String xsl)
			throws Exception {
		if (StringUtils.isEmpty(xmlFilePath)) {
			throw new Exception("xml string is empty");
		}
		if (StringUtils.isEmpty(xsl)) {
			throw new Exception("xsl string is empty");
		}

		StringWriter writer = new StringWriter();
		Source xmlSource = new StreamSource(new File(xmlFilePath));
		Source xslSource = new StreamSource(new File(xsl));
		Result result = new StreamResult(writer);

		try {
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xslSource);
			Properties properties = trans.getOutputProperties();
			properties.setProperty(OutputKeys.ENCODING, "gb2312");
			properties.put(OutputKeys.METHOD, "html");
			trans.setOutputProperties(properties);

			trans.transform(xmlSource, result);
			return writer.toString();
		} catch (Exception ex) {
			System.out.println("xml2xsl:" + ex);
			throw new Exception(ex);
		} finally {
			writer.close();
			writer = null;

			xmlSource = null;
			xslSource = null;
			result = null;
		}
	}

	/**
	 * 读取XML文档，返回Document对象.<br>
	 * 
	 * @param xmlFile
	 *            XML文件路径
	 * @return Document 对象
	 */
	public static Document getDocument(String xmlFile) throws Exception {
		if (StringUtils.isEmpty(xmlFile)) {
			return null;
		}

		File file = null;
		SAXReader saxReader = new SAXReader();

		file = new File(xmlFile);
		return saxReader.read(file);
	}

	/**
	 * 读取XML文档，返回Document对象.<br>
	 * 
	 * @param xmlFile
	 *            file对象
	 * @return Document 对象
	 */
	public static Document getDocument(File xmlFile) {
		try {
			SAXReader saxReader = new SAXReader();
			return saxReader.read(xmlFile);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 
	 * 描述：读取XML文档，先从指定的位置读取，没有再通过文件流读取(读jar包的配置文件) 时间：2015-5-15 下午9:13:12
	 * 
	 * @param cls
	 * @param propFile
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Document getDocument(Class cls, String xmlFile) {
		Document document = null;
		File file = FileUtil.guessPropFile(cls, xmlFile);
		if (file != null && file.exists() && file.isFile()) {
			document = XMLUtil.getDocument(file);
		} else {
			InputStream ins = null;
			try {
				// 得到类的类装载器
				ClassLoader loader = cls.getClassLoader();
				if (loader != null) {
					// 先从当前类所处路径的根目录中寻找属性文件
					ins = loader.getResourceAsStream(xmlFile);
				}

				if (ins != null) {
					SAXReader reader = new SAXReader();
					document = reader.read(ins);
				}
			} catch (Exception ex) {
				logger.error("读取xml文件出错，返回null", ex);
			} finally {
				if (ins != null) {
					try {
						ins.close();
						ins = null;
					} catch (IOException e) {
						logger.error("", e);
					}
				}
			}
		}
		return document;
	}

	/**
	 * 读取XML字串，返回Document对象
	 * 
	 * @param xmlString
	 *            XML文件路径
	 * @return Document 对象
	 */
	public static Document getDocumentFromString(String xmlString) {
		if (StringUtils.isEmpty(xmlString)) {
			return null;
		}
		try {
			SAXReader saxReader = new SAXReader();
			return saxReader.read(new StringReader(xmlString));
		} catch (Exception ex) {
			logger.error("解析xml字符串出错，返回null", ex);
			return null;
		}
	}

	/**
	 * 描述：把xml输出成为html 作者： 时间：Oct 29, 2008 4:57:56 PM
	 * 
	 * @param xmlDoc
	 *            xmlDoc
	 * @param xslFile
	 *            xslFile
	 * @param encoding
	 *            编码
	 * @return
	 * @throws Exception
	 */
	public static String xml2html(String xmlDoc, String xslFile, String encoding)
			throws Exception {
		if (StringUtils.isEmpty(xmlDoc)) {
			throw new Exception("xml string is empty");
		}
		if (StringUtils.isEmpty(xslFile)) {
			throw new Exception("xslt file is empty");
		}

		StringWriter writer = new StringWriter();
		Source xmlSource = null;
		Source xslSource = null;
		Result result = null;
		String html = null;
		try {
			xmlSource = new StreamSource(new StringReader(xmlDoc));
			xslSource = new StreamSource(new File(xslFile));

			result = new StreamResult(writer);

			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xslSource);
			Properties properties = trans.getOutputProperties();
			properties.put(OutputKeys.METHOD, "html");
			properties.setProperty(OutputKeys.ENCODING, encoding);
			trans.setOutputProperties(properties);

			trans.transform(xmlSource, result);

			html = writer.toString();
			writer.close();

			return html;
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			writer = null;

			xmlSource = null;
			xslSource = null;
			result = null;
		}
	}

	/**
	 * 描述：把xml输出成为html 作者： 时间：Oct 29, 2008 4:58:48 PM
	 * 
	 * @param xmlFile
	 *            xmlFile
	 * @param xslFile
	 *            xslFile
	 * @param encoding
	 *            编码
	 * @return
	 * @throws Exception
	 */
	public static String xmlFile2html(String xmlFile, String xslFile,
			String encoding) throws Exception {
		if (StringUtils.isEmpty(xmlFile)) {
			throw new Exception("xml string is empty");
		}
		if (StringUtils.isEmpty(xslFile)) {
			throw new Exception("xslt file is empty");
		}

		StringWriter writer = new StringWriter();
		Source xmlSource = null;
		Source xslSource = null;
		Result result = null;
		String html = null;
		try {
			xmlSource = new StreamSource(new File(xmlFile));
			xslSource = new StreamSource(new File(xslFile));

			result = new StreamResult(writer);

			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xslSource);
			Properties properties = trans.getOutputProperties();
			properties.put(OutputKeys.METHOD, "html");
			properties.setProperty(OutputKeys.ENCODING, encoding);
			trans.setOutputProperties(properties);

			trans.transform(xmlSource, result);

			html = writer.toString();
			writer.close();

			return html;
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			writer = null;

			xmlSource = null;
			xslSource = null;
			result = null;
		}
	}

	/**
	 * 描述： 作者： 时间：Oct 29, 2008 5:00:10 PM
	 * 
	 * @param name
	 *            名
	 * @param element
	 *            元素
	 * @return
	 */
	public static String getString(String name, Element element) {
		return (element.valueOf(name) == null) ? "" : element.valueOf(name);
	}

	/**
	 * 将一个XML文档保存至文件中.
	 * 
	 * @param doc
	 *            要保存的XML文档对象.
	 * @param filePath
	 *            要保存到的文档路径.
	 * @param format
	 *            要保存的输出格式
	 * @return true代表保存成功，否则代表不成功.
	 */
	public static boolean savaToFile(Document doc, String filePathName,
			OutputFormat format) {
		XMLWriter writer;
		try {
			String filePath = FileUtil.getFullPath(filePathName);
			// 若目录不存在，则建立目录
			if (!FileUtil.exists(filePath)) {
				if (!FileUtil.createDirectory(filePath)) {
					return false;
				}
			}

			writer = new XMLWriter(new FileWriter(new File(filePathName)),
					format);
			writer.write(doc);
			writer.close();
			return true;
		} catch (IOException ex) {
			logger.error("写文件出错", ex);
		}

		return false;
	}

	/**
	 * 将一个XML文档保存至文件中.
	 * 
	 * @param filePath
	 *            要保存到的文档路径.
	 * @param doc
	 *            要保存的XML文档对象.
	 * @return true代表保存成功，否则代表不成功.
	 */
	public static boolean writeToXml(String filePathName, Document doc) {
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding("GBK");
		return savaToFile(doc, filePathName, format);
	}

	
	public static String toAttribute(String s) {
		return s;
	}

	public static boolean isEmpty(Element parent, String childTagName) {
		Element child = parent.element(childTagName);
		return (child == null) || (StringUtils.isEmpty(child.getText()));
	}

	public static int getInt(Element parent, String childTagName) {
		String text = parent.elementText(childTagName);
			return Integer.parseInt(text);
	}

	public static boolean getBoolean(Element parent, String childTagName) {
		return Boolean.parseBoolean(parent.elementText(childTagName));
	}

	public static String getString(Element parent, String childTagName) {
		return parent.elementText(childTagName);
	}
	
	public static JSONObject toJson(String xml){
		return toJson(xml, null);
	}
	public static JSONObject toJson(String xml,String rootName){
		//System.out.println(xml);
		if(rootName==null){
			rootName="xml";
		}
		JSONObject json=new JSONObject();
		@SuppressWarnings("unused")
		SAXReader reader = new SAXReader();
		try {
			Document document=DocumentHelper.parseText(xml);
			//Document document = reader.read(new ByteArrayInputStream(xml2.getBytes()));
			//System.out.println(document.asXML());
			Element root = document.getRootElement();
			_toJson(json, root);
		}catch(Exception e){
			e.printStackTrace();
		}
		return (JSONObject)json.get(rootName);
	}
	
	@SuppressWarnings("unchecked")
	public static void _toJson(JSONObject parentJson,Element e){
		List<Element> elements = e.elements();
		
		String name = e.getName();
		int size = elements.size();
		if(size==0){//叶子节点
			parentJson.put(name, e.getTextTrim());
		}else{
			if(size>1&&elements.get(0).getName().equals(elements.get(1).getName())){
				//数组
				JSONArray jsons=new JSONArray();
				for(int i=0;i<size;i++){
					JSONObject json=new JSONObject();
					_toJson(json, elements.get(i));
					jsons.add(json);
				}
				parentJson.put(name,jsons);
			}else{
				JSONObject json=new JSONObject();
				for(int i=0;i<size;i++){
					_toJson(json, elements.get(i));
				}
				parentJson.put(name, json);
			}
		}
	}
	
	public static String toXml(JSONObject json){
		return toXml(json, null);
	}
	
	public static String toXml(JSONObject json,String rootName){
		return toXml(json, rootName, true);
	}
	public static String toXml(JSONObject json,String rootName,boolean isCDATAText){
		if(rootName==null){
			rootName="xml";
		}
		
		Document doc=DocumentHelper.createDocument();
		Element root=doc.addElement(rootName);
		_toXml(root, json,isCDATAText);
		return doc.getRootElement().asXML();
	}
	public static void _toXml(Element e,Object value,boolean isCDATAText){
		if(value instanceof JSONArray){
			JSONArray items=(JSONArray)value;
			for(int i=0;i<items.size();i++){
				JSONObject item = items.getJSONObject(i);//只能是jsonobject
				for(Iterator<String> iter=item.keySet().iterator();iter.hasNext();){
					String name=iter.next();
					Element subElement = e.addElement(name);
					if(item.get(name)!=null)
					_toXml(subElement,item.get(name),isCDATAText);
				}
			}
		}else if(value instanceof JSONObject){
			JSONObject json=(JSONObject)value;
			for(Iterator<String> iter=json.keySet().iterator();iter.hasNext();){
				String name=iter.next();
				if(json.get(name)!=null){
					Element subElement = e.addElement(name);
					_toXml(subElement,json.get(name),isCDATAText);
				}
			}
			
		}else{
			if(isCDATAText){
				e.addCDATA(value.toString());
			}else{
				e.addText(value.toString());
			}
			return;
		}
	}
	


}

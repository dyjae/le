package com.jae.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlConfigurationUtil {
		
		private static Logger logger = LoggerFactory.getLogger(XmlConfigurationUtil.class);
		
		@SuppressWarnings("rawtypes")
		private static Map items = new HashMap();
		
		private static String CONFIG_FILE_NAME = "configuration.xml";
		
		static
		{
			loadConfig();
		}
		
		/**
		 * 读入配置文件
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		private static void loadConfig()
		{
			try
			{
				
				Document document = XMLUtil.getDocument(XmlConfigurationUtil.class, CONFIG_FILE_NAME);
				if (document != null)
				{
					Element systemElement = document.getRootElement();
					List catList = systemElement.elements("items");
					for (Iterator catIter = catList.iterator(); catIter.hasNext();)
					{
						Element catElement = (Element) catIter.next();
						String catName = catElement.attributeValue("name");
						if (StringUtils.isEmpty(catName))
						{
							continue;
						}
						
						List itemList = catElement.elements("item");
						for (Iterator itemIter = itemList.iterator(); itemIter.hasNext();)
						{
							Element itemElement = (Element) itemIter.next();
							String itemName = itemElement.attributeValue("name");
							String value = itemElement.attributeValue("value");
							if (!StringUtils.isEmpty(itemName))
							{
								items.put(catName + "." + itemName, value);
							}
						}
					}
				}
			}
			catch (Exception ex)
			{
				logger.error("读入配置文件出错", ex);
			}
			finally
			{
			}
			
		}
		
		/**
		 * 获得字串配置值
		 *
		 * @param name
		 * @return
		 */
		public static String getString(String name)
		{
			String value = (String) items.get(name);
			return (value == null) ? "" : value;
		}
		
		/**
		 * 获得字串配置值，若为空，则返回缺省值
		 *
		 * @param name
		 * @param defaultValue
		 * @return
		 */
		public static String getString(String name, String defaultValue)
		{
			String value = (String) items.get(name);
			if (value != null && value.length() > 0)
				return value;
			else
				return defaultValue;
		}
		
		/**
		 * 获得整型配置值
		 *
		 * @param name
		 * @return
		 */
		public static int getInt(String name)
		{
			String value = getString(name);
			try
			{
				return Integer.parseInt(value);
			}
			catch (NumberFormatException ex)
			{
				logger.error("配置文件key["+name+"]配置错误，return 0", ex);
				return 0;
			}
		}
		
		/**
		 * 获得整型配置值
		 *
		 * @param name
		 * @return
		 */
		public static int getInt(String name, int defaultValue)
		{
			String value = getString(name);
			if("".equals(value)) {
				return defaultValue;
			}
			try
			{
				return Integer.parseInt(value);
			}
			catch (NumberFormatException ex)
			{
				logger.error("配置文件key["+name+"]配置错误，return "+defaultValue, ex);
			}
			return defaultValue;
		}
		
		/**
		 * 获得布尔型配置值
		 *
		 * @param name
		 * @return
		 */
		public static boolean getBoolean(String name)
		{
			String value = getString(name);
			return Boolean.valueOf(value).booleanValue();
		}
		
		
		/**
		 * 获得双精度浮点数配置值
		 * 
		 * @param name
		 * @return
		 */
		public static double getDouble(String name, double defaultValue)
		{
			String value = getString(name);
			try
			{
				return Double.parseDouble(value);
			}
			catch (NumberFormatException ex)
			{
				logger.error("配置文件key["+name+"]配置错误，return "+defaultValue, ex);
			}
			return defaultValue;
		}
		
		/**
		 * @Description:获取所有配置
		 * @return Map
		 * @exception:
		 * @author: yj
		 * @time:2017年7月26日 下午9:19:33
		 */
		@SuppressWarnings("rawtypes")
		public static Map getItems()
		{
			return items;
		}
}

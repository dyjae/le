package com.jae.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @Description:读取配置文件公共方法
 * @author:yj
 * @time:2017年7月26日 下午5:44:48
 */
public class ConfigurationUtil
{
	static String fileName = "configs.properties";
	
    static HashMap<String,String> hm = null;
    
    
    public static String getValue(String name) {  
    	String line = "";
    	String returnValue = "";
    	if (hm != null && hm.containsKey(name))
    	{
    		returnValue = (String)hm.get(name);
    		return returnValue;
    	}
    	InputStream file = null;
    	InputStreamReader file1 = null;
    	BufferedReader br = null;
    	try
		{
    		file = ConfigurationUtil.class.getClassLoader()
					.getResourceAsStream(fileName);
			 file1 = new InputStreamReader(file);
		     br = new BufferedReader(file1);
			while ((line = br.readLine()) != null)
			{
				int index = line.indexOf("=");
				if (index == -1)
				{
					continue;
				}
				String key = line.substring(0, index).trim();
				String value = line.substring(index + 1).trim();
				if (hm == null)
				{
					hm = new HashMap<String,String>();
				}
				hm.put(key, value);
			}

		}
    	catch(Exception e)
    	{
    		System.err.println(e.getMessage());
    	}finally{
			try {
				if(null != br)
					br.close();
				if(null != file1)
					file1.close();
				if(null != file)
					file.close();	
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
    	}
    	return (String)hm.get(name);
    }
}

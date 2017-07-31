package com.jae.common.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述: HttpClient工具类
 * 版本: 1.0 
 * 创建日期: May 8, 2014 
 * 创建时间: 7:23:27 PM
 */
@SuppressWarnings("deprecation")
public class HttpClientUtil
{
	
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	// 读取超时  
	private final static int SOCKET_TIMEOUT = 300000;
	
	// 连接超时  
	private final static int CONNECTION_TIMEOUT = 300000;
	
	// 每个HOST的最大连接数量  
	private final static int MAX_CONN_PRE_HOST = 20;
	
	// 连接池的最大连接数  
	private final static int MAX_CONN = 60;
	
	// 编码
	private final static String CHARSET = "UTF-8";
	
	// 连接池  
	private final static PoolingHttpClientConnectionManager httpConnectionManager;
	// httpClient实例.  
	private static CloseableHttpClient httpClient;
	//请求配置
	private static RequestConfig requestConfig;
	
	static
	{
		httpConnectionManager = new PoolingHttpClientConnectionManager();
		httpConnectionManager.setMaxTotal(MAX_CONN);//连接池最大并发连接数
		httpConnectionManager.setDefaultMaxPerRoute(MAX_CONN_PRE_HOST);//单路由最大并发数
		// 创建httpClient实例.  
		httpClient = HttpClients.custom().setConnectionManager(httpConnectionManager).build();
		// 设置请求和传输超时时间,设置cookie策略
		requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECTION_TIMEOUT).setCookieSpec(CookieSpecs.BEST_MATCH).build();
	}
	
	/**
	 * @功能： post请求
	 * @time：May 8, 2014 7:24:39 PM
	 * @param url
	 * @param map
	 * @return
	 */
	public static String httpClientPost(String url, Map<String, Object> map, Map<String,Object> headerMap)
	{
		if (StringUtils.isBlank(url))
		{
			return null;
		}
		// 创建httppost    
		HttpPost post = new HttpPost(url);
		// 创建参数队列    
		List<NameValuePair> params = map2NameValuePair(map);
		UrlEncodedFormEntity uefEntity;
		try
		{
			uefEntity = new UrlEncodedFormEntity(params, CHARSET); //设置参数列表
			post.setEntity(uefEntity);
			for (Entry<String, Object> entry : headerMap.entrySet()) {
				post.setHeader(entry.getKey(),(String) entry.getValue());
			}
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return getResEntity(httpClient, post); //发送请求，获取返回数据
	}
	
	/**
	 * @功能： get请求
	 * @time：May 8, 2014 7:24:39 PM
	 * @param url
	 * @param map
	 * @return
	 */
	public static String httpClientGet(String url, Map<String, Object> map, Map<String, Object> headerMap)
	{
		if (StringUtils.isBlank(url))
		{
			return null;
		}
		List<NameValuePair> params = map2NameValuePair(map); // 创建参数队列    
		
		try
		{
			if (url.indexOf("?") != -1) //拼接url参数
			{
				url = url + "&" + EntityUtils.toString(new UrlEncodedFormEntity(params, CHARSET));
			}
			else
			{
				url = url + "?" + EntityUtils.toString(new UrlEncodedFormEntity(params, CHARSET));
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		HttpGet httpGet = new HttpGet(url);
			for (Entry<String, Object> entry : headerMap.entrySet()) {
				httpGet.setHeader(entry.getKey(),(String) entry.getValue());
			}
		return getResEntity(httpClient, httpGet); //发送请求，获取返回数据
	}
	
	private static String getResEntity(CloseableHttpClient httpClient, HttpRequestBase httpObj)
	{
		try
		{
			httpObj.setConfig(requestConfig); // 设置请求和传输超时时间,设置cookie策略
			CloseableHttpResponse httpReponse = httpClient.execute(httpObj);
			try
			{
				HttpEntity entity = httpReponse.getEntity();
				int statusCode = httpReponse.getStatusLine().getStatusCode();
				if (statusCode != 200)
				{
					httpObj.abort();
					throw new RuntimeException("HttpClient,error status code :" + statusCode);
				}
				if (entity != null)
				{
					String result = EntityUtils.toString(entity, CHARSET);
					EntityUtils.consume(entity);
					logger.info("Response content : " + result);
					return result;
				}
			}
			finally
			{
				httpReponse.close();
			}
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * @功能：根据传参map转为参数队列NameValuePair
	 * @time：May 8, 2014 8:13:25 PM
	 * @param map
	 * @return
	 */
	private static List<NameValuePair> map2NameValuePair(Map<String, Object> map)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (map != null && !map.isEmpty()) //参数map是否有值
		{
			for (Entry<String, Object> entry : map.entrySet())
			{
				params.add(new BasicNameValuePair(entry.getKey(),  ObjectUtils.toString(entry.getValue())));
			}
		}
		return params;
	}
}
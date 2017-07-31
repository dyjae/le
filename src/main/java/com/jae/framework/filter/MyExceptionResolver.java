package com.jae.framework.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.jae.framework.exception.MyException;
import com.jae.framework.util.ResultUtil;

public class MyExceptionResolver implements HandlerExceptionResolver{

	/**日志log*/

    //系统抛出的异常  
    @Override  
    public ModelAndView resolveException(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex) {  
        //handler就是处理器适配器要执行的Handler对象(只有method)  
        //解析出异常类型。  
    	/*  使用response返回    */  
        response.setStatus(HttpStatus.OK.value()); //设置状态码  
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType  
        response.setCharacterEncoding("UTF-8"); //避免乱码  
        response.setHeader("Cache-Control", "no-cache, must-revalidate");  
        //如果该 异常类型是系统 自定义的异常，直接取出异常信息。  
        MyException lsException=null;  
        try {
	        if(ex instanceof MyException){  
	        	lsException = (MyException)ex;  
	            //错误信息  
				response.getWriter().write(JSONObject.toJSONString(ResultUtil.error(lsException.getCode(),lsException.getErrMsg())));
	        }else
	        	response.getWriter().write(JSONObject.toJSONString(ResultUtil.error(-1,ex.getMessage())));
    	} catch (IOException e) {
            //log.error("与客户端通讯异常:"+ e.getMessage(), e);  
			e.printStackTrace();
		}
        ModelAndView modelAndView=new ModelAndView();  
          
        return modelAndView;  
    } 

}

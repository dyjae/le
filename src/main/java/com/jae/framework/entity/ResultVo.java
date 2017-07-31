package com.jae.framework.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jae.framework.util.BeanMapUtil;

public class ResultVo {
	
	private static final Logger logger = LoggerFactory.getLogger(ResultVo.class);
	
	private Integer errNo;
	
	private boolean success;
	
	private String errMsg;
	
	private Map<String, Object> results = null;
	
	private final String ERRORMSG = "操作失败!";

	private final int ERRORNO = -1;
	
	public ResultVo()
	{
		this.errNo = 0;
		this.isTrue();
		this.errMsg = "操作成功！";
		results = new HashMap<String, Object>();
	}

	public Integer getErrNo() {
		return errNo;
	}

	public void setErrNo(Integer errNo) {
		this.errNo = errNo;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
		logger.info(errMsg);
	}

	public Map<String, Object> getResults() {
		return results;
	}

	public void setResults(String name, Object object) {
		results.put(name, object);
	}
	
	@SuppressWarnings("unchecked")
	public void setResults(Object object)
	{
		if (object instanceof Map)
		{
			this.results = (Map<String, Object>) object;
		}
		else if (object instanceof List)
		{
			results.put("rows", object);
		}else{
			this.results = BeanMapUtil.toMap(object);
		}
	}

	public boolean isSuccess() {
		return success;
	}
	
	private void setSuccess(boolean success) {
		this.success = success;
	}
	
	public void isTrue(){
		this.setSuccess(true);
	}
	
	public void isFalse(){
		this.setSuccess(false);
	}
	
	public void setFalseInfo(int errNo){
		this.isFalse();
		this.setErrNo(errNo);
		this.setErrMsg(ERRORMSG);
	}
	
	public void setFalseInfo(String errMsg){
		this.isFalse();
		this.setErrNo(ERRORNO);
		this.setErrMsg(errMsg);
	}
	
	public void setFalseInfo(String errMsg,int errNo){
		this.isFalse();
		this.setErrNo(errNo);
		this.setErrMsg(errMsg);
	}
	
}

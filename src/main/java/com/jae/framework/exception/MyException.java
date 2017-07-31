package com.jae.framework.exception;

import com.jae.framework.entity.ResultVo;

/**
 * 自定义异常类
 * @author Jae
 *
 */
public class MyException extends Exception{

	private static final long serialVersionUID = 866974950937939446L;
	private Integer code;
	private String errMsg;
    public MyException(){}
    
    public MyException(int code,String errMsg){
    	this.code = code;
    	this.errMsg = errMsg;
    }
    
    public MyException(String errMsg){
    	this.code = -1;
    	this.errMsg = errMsg;
    }
    
    public MyException(ResultVo result) {
    	super((result.getErrMsg()));
        this.code = result.getErrNo();
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
    
}

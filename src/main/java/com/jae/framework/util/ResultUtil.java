package com.jae.framework.util;


import com.jae.framework.entity.ResultVo;

public class ResultUtil {
	private static final String SUCCESS_MSG = "成功";
    /**
     * http回调成功
     * @param object
     * @return
     */
	public  static ResultVo success(Object object){
    	ResultVo result = new ResultVo();
        result.isSuccess();
        result.setErrMsg(SUCCESS_MSG);
        return result;
    }
    /**
     * 无object返回
     * @return
     */
	public  static ResultVo success(){
        return success(null);
    }
    /**
     * http回调错误
     * @param code
     * @param msg
     * @return
     */
	public static ResultVo error(Integer code,String msg){
    	ResultVo result = new ResultVo();
    	result.isFalse();
        result.setErrNo(code);;
        result.setErrMsg(msg);
        return  result;
    }
}

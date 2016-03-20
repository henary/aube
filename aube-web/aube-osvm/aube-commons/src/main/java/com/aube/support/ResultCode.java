package com.aube.support;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.aube.constant.ErrorCodeConstant;

/**
 * 统一错误返回标识<br>
 * TODO 错误消息处理
 * 
 * @param <T>
 */
public class ResultCode<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3371328988577070101L;
	// 错误编码
	private String errcode;
	// 错误信息
	private String msg;
	// 错误信息填充参数
	private String[] errParams;
	// 返回值
	private T retval;
	private Throwable exception;

	protected ResultCode(String code) {
		this.errcode = code;
	}
	
	protected ResultCode(String code, String...errParams) {
		this.errcode = code;
		this.errParams = errParams;
	}

	protected ResultCode(String code, T retval, String...errParams) {
		this.errcode = code;
		this.errParams = errParams;
		this.retval = retval;
	}

	public static ResultCode<String> SUCCESS = new ResultCode<String>(ErrorCodeConstant.CODE_SUCCESS);

	@Override
	public boolean equals(Object another) {
		if (another == null || !(another instanceof ResultCode))
			return false;
		return this.errcode == ((ResultCode<?>) another).errcode;
	}

	public boolean isSuccess() {
		return StringUtils.equals(errcode, ErrorCodeConstant.CODE_SUCCESS);
	}

	public static <T> ResultCode<T> getFailure(String code, String...errParams) {
		return new ResultCode<T>(code, errParams);
	}

	public static <T> ResultCode<T> getSuccessReturn(T retval) {
		return new ResultCode<T>(ErrorCodeConstant.CODE_SUCCESS, retval);
	}
	
	public static <T> ResultCode<T> getFailureReturn(T retval) {
		return new ResultCode<T>(ErrorCodeConstant.CODE_SUCCESS, retval);
	}
	
	public static <T> ResultCode<T> getFailureReturn(String code, T retval) {
		return new ResultCode<T>(ErrorCodeConstant.CODE_SUCCESS, retval);
	}

	public static <T> ResultCode<T> getFailureReturn(String code, T retval, String ...errParams) {
		return new ResultCode<T>(ErrorCodeConstant.CODE_SUCCESS, retval, errParams);
	}

	public T getRetval() {
		return retval;
	}

	public String getMsg() {
		return msg;
	}

	public String getErrcode() {
		return errcode;
	}

	public Throwable getException() {
		return exception;
	}

	/**
	 * dubbo接口服务端请不要设置此异常！只作为客户端封装使用
	 * 
	 * @param exception
	 */
	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public String[] getErrParams() {
		return errParams;
	}

	public void setErrParams(String[] errParams) {
		this.errParams = errParams;
	}

}

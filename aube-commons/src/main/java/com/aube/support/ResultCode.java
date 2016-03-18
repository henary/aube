package com.aube.support;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * 统一错误返回标识<br>
 * TODO 错误消息处理
 * @param <T>
 */
public class ResultCode<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3371328988577070101L;

	public static final String CODE_SUCCESS = "0000";
	public static final String CODE_UNKNOWN_ERROR = "9999";
	public static final String CODE_DATA_ERROR = "4005";

	private String errcode;
	private String msg;
	private T retval;
	private boolean success = false;
	private Throwable exception;

	protected ResultCode(String code, T retval) {
		this.errcode = code;
		this.retval = retval;
		this.success = StringUtils.equals(code, CODE_SUCCESS);
	}

	public static ResultCode<String> SUCCESS = new ResultCode<String>(CODE_SUCCESS, null);

	@Override
	public boolean equals(Object another) {
		if (another == null || !(another instanceof ResultCode))
			return false;
		return this.errcode == ((ResultCode<?>) another).errcode;
	}

	public boolean isSuccess() {
		return success;
	}

	public static <T> ResultCode<T> getFailure(String code) {
		return new ResultCode<T>(code, null);
	}

	public static <T> ResultCode<T> getSuccessReturn(T retval) {
		return new ResultCode<T>(CODE_SUCCESS, retval);
	}

	public static <T> ResultCode<T> getFailureReturn(String code, T retval) {
		return new ResultCode<T>(CODE_UNKNOWN_ERROR, retval);
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

}

package com.aube.support;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class ResultCode<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3371328988577070101L;

	public static final String CODE_SUCCESS = "0000";
	public static final String CODE_UNKNOWN_ERROR = "9999";

	private String errcode;
	private String msg;
	private T retval;
	private boolean success = false;

	protected ResultCode(String code, String msg, T retval) {
		this.errcode = code;
		this.msg = msg;
		this.retval = retval;
		this.success = StringUtils.equals(code, CODE_SUCCESS);
	}

	public static ResultCode<String> SUCCESS = new ResultCode<String>(CODE_SUCCESS, "操作成功！", null);

	@Override
	public boolean equals(Object another) {
		if (another == null || !(another instanceof ResultCode))
			return false;
		return this.errcode == ((ResultCode<?>) another).errcode;
	}

	public boolean isSuccess() {
		return success;
	}

	public static <T> ResultCode<T> getFailure(String msg) {
		return new ResultCode<T>(CODE_UNKNOWN_ERROR, msg, null);
	}

	public static <T> ResultCode<T> getFailure(String code, String msg) {
		return new ResultCode<T>(code, msg, null);
	}

	public static <T> ResultCode<T> getSuccess(String msg) {
		return new ResultCode<T>(CODE_SUCCESS, msg, null);
	}

	public static <T> ResultCode<T> getSuccessReturn(T retval) {
		return new ResultCode<T>(CODE_SUCCESS, null, retval);
	}

	public static <T> ResultCode<T> getFailureReturn(T retval) {
		return new ResultCode<T>(CODE_UNKNOWN_ERROR, null, retval);
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

}
package com.aube.support;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import com.aube.constant.ErrorCodeConstant;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

/**
 * 统一错误返回标识<br>
 * 
 * @param <T>
 */
public class ResultCode<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3371328988577070101L;
	private transient static final AubeLogger logger = LoggerUtils.getLogger(ResultCode.class);

	private static final String AUBE_BUNDLE_NAME = "aube_messages";
	private static final ResourceBundle AUBE_RESOURCE_BUNDLE = ResourceBundle.getBundle(AUBE_BUNDLE_NAME);

	// 错误编码
	private String errcode;
	// 错误信息填充参数
	private transient Object[] errParams;
	private String errmsg;
	// 返回值
	private T retval;
	private transient Throwable exception;
	public boolean success;

	protected ResultCode() {
		this.errcode = ErrorCodeConstant.CODE_SUCCESS;
		this.setErrmsg();
	}

	protected ResultCode(String code) {
		this.errcode = code;
		this.setErrmsg();
	}

	protected ResultCode(String code, Object... errParams) {
		this.errcode = code;
		this.errParams = errParams;
		this.setErrmsg();
	}

	protected ResultCode(String code, T retval, Object... errParams) {
		this.errcode = code;
		this.errParams = errParams;
		this.retval = retval;
		this.setErrmsg();
	}

	public static ResultCode<String> SUCCESS = new ResultCode<String>(ErrorCodeConstant.CODE_SUCCESS);

	@Override
	public boolean equals(Object another) {
		if (another == null || !(another instanceof ResultCode))
			return false;
		return this.errcode == ((ResultCode<?>) another).errcode;
	}

	public boolean isSuccess() {
		return success;
	}

	public static <T> ResultCode<T> getFailure(String code, Object... errParams) {
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

	public static <T> ResultCode<T> getFailureReturn(String code, T retval, Object... errParams) {
		return new ResultCode<T>(ErrorCodeConstant.CODE_SUCCESS, retval, errParams);
	}

	public T getRetval() {
		return retval;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrParams(Object... errParams) {
		this.errParams = errParams;
	}

	public Object[] getErrParams() {
		return errParams;
	}

	private void setErrmsg() {
		success = StringUtils.equals(errcode, ErrorCodeConstant.CODE_SUCCESS);
		String message = "";
		try {
			message = AUBE_RESOURCE_BUNDLE.getString(errcode);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		// 如果没有找到则直接返回errcode
		if (StringUtils.isBlank(message)) {
			message = errcode;
		}
		this.errmsg = MessageFormat.format(message, errParams);
	}

	/**
	 * 错误信息返回
	 * 
	 * @return
	 */
	public String getErrmsg() {
		return errmsg;
	}

	public Throwable getException() {
		return exception;
	}

	/**
	 * 业务不能使用，近限于dubbo服务拦截器使用
	 * 
	 * @param exception
	 */
	public void setException(Throwable exception) {
		this.exception = exception;
	}

}

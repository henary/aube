package com.aube.resp.vo;

import org.apache.commons.lang.StringUtils;

import com.aube.constant.ErrorCodeConstant;
import com.aube.support.ResultCode;
import com.aube.vo.GsonObject;

/**
 * 数据统一返回结构</br>
 * 
 * @see ErrorCodeConstant <br/>
 *      当errorCodeErrorCodeConstant.CODE_SUSCCESS时返回true，其他为false
 * @param <T>
 */
public class DataRespVo<T> implements GsonObject<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1494371252287689009L;

	// 错误码
	private String errCode;
	// 错误消息提示
	private String errMsg;
	// 返回结果
	private T result;
	// 是否成功
	@SuppressWarnings("unused")
	private boolean success;

	/**
	 * 默认返回success为true
	 */
	public DataRespVo() {
		this.errCode = ErrorCodeConstant.CODE_SUCCESS;
	}

	/**
	 * 成功返回的
	 * 
	 * @param t
	 *            成功返回的值
	 */
	public DataRespVo(T result) {
		this.errCode = ErrorCodeConstant.CODE_SUCCESS;
		this.result = result;
	}
	
	/**
	 * 根据ResultCode的返回值内容返回
	 * @see com.aube.support.ResultCode
	 * @param resultcode
	 */
	public DataRespVo(ResultCode<T> resultcode) {
		this.errMsg = resultcode.getErrmsg();
		this.errCode = resultcode.getErrcode();
		this.result = resultcode.getRetval();
	}

	/**
	 * 错误提示
	 * 
	 * @param errorCode
	 *            错误代码
	 */
	public DataRespVo(String errorCode) {
		this.errCode = errorCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	/**
	 * 错误码是否为ErrorCodeConstant.CODE_SUSCCESS
	 * 
	 * @see ErrorCodeConstant.CODE_SUSCCESS
	 * @return
	 */
	public boolean isSuccess() {
		return StringUtils.equals(ErrorCodeConstant.CODE_SUCCESS, this.errCode);
	}
}

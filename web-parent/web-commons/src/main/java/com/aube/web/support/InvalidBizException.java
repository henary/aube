package com.aube.web.support;

public class InvalidBizException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public InvalidBizException(){
		
	}
	public InvalidBizException(String msg){
		super(msg);
	}
}

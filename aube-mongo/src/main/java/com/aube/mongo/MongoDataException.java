package com.aube.mongo;

public class MongoDataException extends RuntimeException{

	private static final long serialVersionUID = -1910421083859837453L;
	public MongoDataException(String msg){
		super(msg);
	}
	public MongoDataException(String msg, Exception e){
		super(msg, e);
	}
}

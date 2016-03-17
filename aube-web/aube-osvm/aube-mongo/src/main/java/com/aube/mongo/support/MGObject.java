package com.aube.mongo.support;

import com.aube.vo.GsonObject;

public class MGObject<T> implements GsonObject<T>{
	private static final long serialVersionUID = 1548619945417324314L;
	private Object _id;

	public void set_id(Object _id) {
		this._id = _id;
	}
	public Object realId(){
		return _id;
	}
	
	
}

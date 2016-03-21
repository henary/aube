package com.aube.api.vo;

import java.io.Serializable;

import com.aube.util.JsonUtils;

public class BaseVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5640067796438580701L;

	@Override
	public String toString() {
		return JsonUtils.writeObjectToJson(this);
	}
}

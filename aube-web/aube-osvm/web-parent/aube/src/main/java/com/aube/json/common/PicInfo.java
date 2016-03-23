package com.aube.json.common;

import com.aube.mongo.support.MGObject;

/**
 * 图片高宽信息
 *
 */
public class PicInfo extends MGObject<PicInfo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5486274694181581797L;
	public static final String PIC_ID = "picurl";
	// 图片高度
	private Integer height;
	// 图片宽度
	private Integer width;
	// 图片的频道（show、video）
	private String picTag;
	// 关联的ID
	private String picRelatedId;
	private String picurl;

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public String getPicTag() {
		return picTag;
	}

	public void setPicTag(String picTag) {
		this.picTag = picTag;
	}

	public String getPicRelatedId() {
		return picRelatedId;
	}

	public void setPicRelatedId(String picRelatedId) {
		this.picRelatedId = picRelatedId;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

}

package com.aube.resp.vo.pic;

import com.aube.vo.GsonObject;

public class PicInfoRespVo implements GsonObject<PicInfoRespVo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1156216325322738325L;
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

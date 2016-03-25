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
	// 图片高度
	private Integer height;
	// 图片宽度
	private Integer width;
	// 图片的频道（show、video）
	private String fileTag;
	// 关联的ID
	private String relatedId;
	// 文件路径
	private String fileurl;
	// 文件扩展名
	private String extname;
	// 文件类型
	private String fileType;
	// 音频&视频文件时长(秒)
	private Integer duration;

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

	public String getFileTag() {
		return fileTag;
	}

	public void setFileTag(String fileTag) {
		this.fileTag = fileTag;
	}

	public String getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(String relatedId) {
		this.relatedId = relatedId;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public String getExtname() {
		return extname;
	}

	public void setExtname(String extname) {
		this.extname = extname;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

}

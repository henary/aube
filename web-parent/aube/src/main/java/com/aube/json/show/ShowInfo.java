package com.aube.json.show;

import com.aube.mongo.support.MGObject;

/**
 * 节目名称
 */
public class ShowInfo extends MGObject<ShowInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4329884994427111621L;
	public static final String SHOW_ID = "showid";
	private String showid;
	// 节目名称
	private String showName;
	// 节目描述
	private String showDesc;
	// 节目配色
	private String showColor;
	// 节目排序
	private Integer sortNum;
	// 节目背景图片Json格式
	private String picInfo;
	// 节目的appkey
	private String appKey;
	// groupId
	private String groupId;
	// 添加时间
	private String addTime;
	// 修改时间
	private String updTime;

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getShowDesc() {
		return showDesc;
	}

	public void setShowDesc(String showDesc) {
		this.showDesc = showDesc;
	}

	public String getShowColor() {
		return showColor;
	}

	public void setShowColor(String showColor) {
		this.showColor = showColor;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getPicInfo() {
		return picInfo;
	}

	public void setPicInfo(String picInfo) {
		this.picInfo = picInfo;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getUpdTime() {
		return updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}

	public String getShowid() {
		return showid;
	}

	public void setShowid(String showid) {
		this.showid = showid;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}

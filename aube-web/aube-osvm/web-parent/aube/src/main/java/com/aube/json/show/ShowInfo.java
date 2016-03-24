package com.aube.json.show;

import com.aube.json.admin.base.AdminBaseMgObject;

/**
 * 节目名称
 */
public class ShowInfo extends AdminBaseMgObject<ShowInfo> {

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

	public String getShowid() {
		return showid;
	}

	public void setShowid(String showid) {
		this.showid = showid;
	}

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

}

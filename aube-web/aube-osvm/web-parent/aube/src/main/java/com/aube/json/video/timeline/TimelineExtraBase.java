package com.aube.json.video.timeline;

import com.aube.mongo.support.MGObject;

public class TimelineExtraBase<T> extends MGObject<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -499224143469835786L;

	public static final String EXTRA_TLID = "tlid";
	// 记录ID
	private String rid;
	// 对应的时间线
	private String tlid;
	private Integer extraSortNum;
	
	public String getTlid() {
		return tlid;
	}

	public void setTlid(String tlid) {
		this.tlid = tlid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public Integer getExtraSortNum() {
		return extraSortNum;
	}

	public void setExtraSortNum(Integer extraSortNum) {
		this.extraSortNum = extraSortNum;
	}

}

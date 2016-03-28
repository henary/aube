package com.aube.json.video.timeline;

import com.aube.mongo.support.MGObject;

public class TimelineVS extends MGObject<TimelineVS>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8343312848809857300L;
	// 对应的时间线
	private String tlid;
	// 对应的选项
	private String option;
	public String getTlid() {
		return tlid;
	}
	public void setTlid(String tlid) {
		this.tlid = tlid;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	
	

}

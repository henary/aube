package com.aube.json.video.timeline;

public class TimelineExtraQA extends TimelineExtraBase<TimelineExtraQA> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3999964447743354909L;
	// QA选项
	private String optionValue;
	// H5跳转的URL
	private String h5url;
	// 图片信息
	private String picInfo;
	// 是否答案
	private String answer;
	
	
	public TimelineExtraQA() {
		
	}
	

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public String getH5url() {
		return h5url;
	}

	public void setH5url(String h5url) {
		this.h5url = h5url;
	}

	public String getPicInfo() {
		return picInfo;
	}

	public void setPicInfo(String picInfo) {
		this.picInfo = picInfo;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}

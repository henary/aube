package com.aube.json.video;

import com.aube.json.admin.base.AdminBaseMgObject;

/**
 * 视频的时间线设置
 *
 */
public class VideoTimelineInfo extends AdminBaseMgObject<VideoTimelineInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2381690166830047246L;

	public static final String TIMELINE_ID = "tlid";
	// 时间线－－id
	private String tlid;
	// 视频id
	private String videoid;
	// 节目id
	private String showid;
	// 时间线－－类型
	private String tlType;
	// 时间线－－标题
	private String tlTitle;
	// 时间线－－描述
	private String tlDesc;
	// 开始时间分
	private Integer startMin;
	// 开始时间秒
	private Integer startSec;
	// 结束时间分
	private Integer endMin;
	// 结束时间秒
	private Integer endSec;
	// 图片信息
	private String picInfo;
	// 卡片是否收起
	private String thumbnailCard;
	// 单位秒
	@SuppressWarnings("unused")
	private Integer startTime;
	// 单位秒
	@SuppressWarnings("unused")
	private Integer endTime;
	// 答案揭晓时间分--QA用
	private Integer showAnswerMin;
	// 答案揭晓时间秒--QA用
	private Integer showAnswerSec;

	// QA用的答案揭晓时间（单位秒）
	public Integer gainShowAnswerTime() {
		return (showAnswerMin == null || showAnswerSec == null) ? 0 : showAnswerMin * 60 + showAnswerSec;
	}
	
	public Integer getStartTime() {
		return (startMin == null || startSec == null) ? 0 : startMin * 60 + startSec;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = (startMin == null || startSec == null) ? 0 : startMin * 60 + startSec;
	}

	public Integer getEndTime() {
		return (endMin == null || endSec == null) ? 0 : endMin * 60 + endSec;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = (endMin == null || endSec == null) ? 0 : endMin * 60 + endSec;
	}

	public String getTlid() {
		return tlid;
	}

	public void setTlid(String tlid) {
		this.tlid = tlid;
	}

	public String getTlType() {
		return tlType;
	}

	public void setTlType(String tlType) {
		this.tlType = tlType;
	}

	public String getTlDesc() {
		return tlDesc;
	}

	public void setTlDesc(String tlDesc) {
		this.tlDesc = tlDesc;
	}

	public Integer getStartMin() {
		return startMin;
	}

	public void setStartMin(Integer startMin) {
		this.startMin = startMin;
	}

	public Integer getStartSec() {
		return startSec;
	}

	public void setStartSec(Integer startSec) {
		this.startSec = startSec;
	}

	public Integer getEndMin() {
		return endMin;
	}

	public void setEndMin(Integer endMin) {
		this.endMin = endMin;
	}

	public Integer getEndSec() {
		return endSec;
	}

	public void setEndSec(Integer endSec) {
		this.endSec = endSec;
	}

	public String getVideoid() {
		return videoid;
	}

	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}

	public String getShowid() {
		return showid;
	}

	public void setShowid(String showid) {
		this.showid = showid;
	}

	public String getTlTitle() {
		return tlTitle;
	}

	public void setTlTitle(String tlTitle) {
		this.tlTitle = tlTitle;
	}

	public String getPicInfo() {
		return picInfo;
	}

	public void setPicInfo(String picInfo) {
		this.picInfo = picInfo;
	}

	public String getThumbnailCard() {
		return thumbnailCard;
	}

	public void setThumbnailCard(String thumbnailCard) {
		this.thumbnailCard = thumbnailCard;
	}

	public Integer getShowAnswerMin() {
		return showAnswerMin;
	}

	public void setShowAnswerMin(Integer showAnswerMin) {
		this.showAnswerMin = showAnswerMin;
	}

	public Integer getShowAnswerSec() {
		return showAnswerSec;
	}

	public void setShowAnswerSec(Integer showAnswerSec) {
		this.showAnswerSec = showAnswerSec;
	}

}

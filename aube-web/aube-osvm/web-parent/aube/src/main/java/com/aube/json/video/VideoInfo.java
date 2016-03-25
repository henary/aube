package com.aube.json.video;

import com.aube.json.admin.base.AdminBaseMgObject;

/**
 * 视频信息
 *
 */
public class VideoInfo extends AdminBaseMgObject<VideoInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1504279394618631416L;
	public static final String SHOW_ID = "showid";
	public static final String VIDEO_ID = "videoid";
	public static final String VIDEO_SORTNUM = "sortNum";
	public static final String VIDEO_STATUS = "videoStatus";
	// 视频ID
	private String videoid;
	// 节目ID
	private String showid;
	// 标题
	private String title;
	// 第几期
	private Integer sortNum;
	// 播放时间戳yyyy-MM-dd HH:mm:ss
	private String playTimestamp;
	// 文件路径
	private String filePath;
	// 图片信息
	private String picInfo;
	// 视频时长
	private Integer duration;
	// CC 视频ID
	private String ccid;
	// 发布状态
	private String videoStatus;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlayTimestamp() {
		return playTimestamp;
	}

	public void setPlayTimestamp(String playTimestamp) {
		this.playTimestamp = playTimestamp;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getCcid() {
		return ccid;
	}

	public void setCcid(String ccid) {
		this.ccid = ccid;
	}

	public String getPicInfo() {
		return picInfo;
	}

	public void setPicInfo(String picInfo) {
		this.picInfo = picInfo;
	}

	public String getVideoStatus() {
		return videoStatus;
	}

	public void setVideoStatus(String videoStatus) {
		this.videoStatus = videoStatus;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

}

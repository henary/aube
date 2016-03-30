package com.aube.json.video.timeline;

public class TimelineExtraMulticamera extends TimelineExtraBase<TimelineExtraMulticamera> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5428703288583194510L;
	private String videoTitle;
	private String videoDesc;
	private String videono;
	private Integer duration;

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public String getVideoDesc() {
		return videoDesc;
	}

	public void setVideoDesc(String videoDesc) {
		this.videoDesc = videoDesc;
	}

	public String getVideono() {
		return videono;
	}

	public void setVideono(String videono) {
		this.videono = videono;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
}

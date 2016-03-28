package com.aube.service;

import java.util.List;

import com.aube.json.video.VideoTimelineInfo;
import com.aube.json.video.timeline.TimelineExtraBase;
import com.aube.support.ResultCode;

public interface TimelineService {

	/**
	 * 保存时间线信息
	 * 
	 * @param timeline
	 * @param extra
	 * @return
	 */
	<T extends TimelineExtraBase<T>> ResultCode<VideoTimelineInfo> saveVideoTimeline(VideoTimelineInfo timeline, List<T> extraList);

	/**
	 * 时间线扩展信息
	 * @param timeline
	 * @return
	 */
	<T extends TimelineExtraBase> ResultCode<List<T>> getTimelineExtraList(VideoTimelineInfo timeline);

}

package com.aube.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aube.constans.MongoData;
import com.aube.constant.AubeErrorCodeConstants;
import com.aube.constants.AubeConstants;
import com.aube.constants.VideoTimelineExtraEnum;
import com.aube.json.video.VideoTimelineInfo;
import com.aube.json.video.timeline.TimelineExtraBase;
import com.aube.mdb.operation.Expression;
import com.aube.service.BaseService;
import com.aube.service.TimelineService;
import com.aube.support.ResultCode;

@Service("timelineService")
public class TimelineServiceImpl extends BaseService implements TimelineService {
	@Override
	public <T extends TimelineExtraBase<T>> ResultCode<VideoTimelineInfo> saveVideoTimeline(VideoTimelineInfo timeline, List<T> extraList) {
		ResultCode<VideoTimelineExtraEnum> extraEnumCode = VideoTimelineExtraEnum.getExtraEnumByTlType(timeline.getTlType());
		if (!extraEnumCode.isSuccess()) {
			return ResultCode.<VideoTimelineInfo> getFailure(extraEnumCode.getErrcode(), extraEnumCode.getErrmsg());
		}
		ResultCode<VideoTimelineInfo> timeValidCode = validateTimelineBase(timeline);
		if (!timeValidCode.isSuccess()) {
			return timeValidCode;
		}
		Expression params = new Expression();
		params.eq(TimelineExtraBase.EXTRA_TLID, timeline.realId().toString());
		// 多镜头的上传一次保存一次，不统一处理
		if (!StringUtils.equals(timeline.getTlType(), VideoTimelineExtraEnum.MULTICAMERA.getTlType())) {
			if (CollectionUtils.isEmpty(extraList)) {
				return ResultCode.<VideoTimelineInfo> getFailure(AubeErrorCodeConstants.CODE_DATA_ERROR, "extra");
			}
			VideoTimelineExtraEnum extraEnum = extraEnumCode.getRetval();
			mongoService.removeObjectList(extraEnum.getExtraClazz(), params);
			mongoService.saveOrUpdateObjectList(extraList, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM);
		}
		mongoService.saveOrUpdateObject(timeline, VideoTimelineInfo.TIMELINE_ID);
		return ResultCode.<VideoTimelineInfo> getSuccessReturn(timeline);
	}

	@Override
	public ResultCode<VideoTimelineInfo> saveVideoTimeline(VideoTimelineInfo timeline) {
		ResultCode<VideoTimelineExtraEnum> extraEnumCode = VideoTimelineExtraEnum.getExtraEnumByTlType(timeline.getTlType());
		if (!extraEnumCode.isSuccess()) {
			return ResultCode.<VideoTimelineInfo> getFailure(extraEnumCode.getErrcode(), extraEnumCode.getErrmsg());
		}
		ResultCode<VideoTimelineInfo> timeValidCode = validateTimelineBase(timeline);
		if (!timeValidCode.isSuccess()) {
			return timeValidCode;
		}
		mongoService.saveOrUpdateObject(timeline, VideoTimelineInfo.TIMELINE_ID);
		return ResultCode.<VideoTimelineInfo> getSuccessReturn(timeline);
	}
	
	@Override
	public ResultCode<VideoTimelineInfo> modityTimelineTime(String tlid, Integer startMin, Integer startSec, Integer endMin, Integer endSec) {
		VideoTimelineInfo timeline = mongoService.getObjectById(VideoTimelineInfo.class, VideoTimelineInfo.TIMELINE_ID, tlid);
		timeline.setStartMin(startMin);
		timeline.setStartSec(startSec);
		timeline.setEndMin(endMin);
		timeline.setEndSec(endSec);
		ResultCode<VideoTimelineInfo> timeValidCode = validateTimelineBase(timeline);
		if (!timeValidCode.isSuccess()) {
			return timeValidCode;
		}
		mongoService.saveOrUpdateObject(timeline, VideoTimelineInfo.TIMELINE_ID);
		return ResultCode.<VideoTimelineInfo> getSuccessReturn(timeline);
	}

	/**
	 * 时间线的开始时间结束时间验证
	 */
	private ResultCode<VideoTimelineInfo> validateTimelineBase(VideoTimelineInfo timeline) {
		if (timeline == null) {
			return ResultCode.<VideoTimelineInfo> getFailure(AubeErrorCodeConstants.CODE_DATA_ERROR);
		}
		if (StringUtils.isBlank(timeline.getTlTitle())) {
			return ResultCode.<VideoTimelineInfo> getFailure(AubeErrorCodeConstants.CODE_DATA_ERROR, "标题");
		}
		if (timeline.gainStartTime() < 0) {
			return ResultCode.<VideoTimelineInfo> getFailure(AubeErrorCodeConstants.CODE_DATA_ERROR, "开始时间");
		}
		if (timeline.gainEndTime() < 0) {
			return ResultCode.<VideoTimelineInfo> getFailure(AubeErrorCodeConstants.CODE_DATA_ERROR, "结束时间");
		}
		if (timeline.gainStartTime() > timeline.gainEndTime()) {
			return ResultCode.<VideoTimelineInfo> getFailure(AubeErrorCodeConstants.CODE_DATA_ERROR, "开始时间必须大于结束时间");
		}
		if (StringUtils.equals(timeline.getTlType(), VideoTimelineExtraEnum.QA.getTlType())) {
			if (timeline.gainShowAnswerTime() != 0 && timeline.gainStartTime() != 0 && timeline.gainEndTime() != 0
					&& (timeline.gainShowAnswerTime() < timeline.gainStartTime() || timeline.gainShowAnswerTime() > timeline.gainEndTime())) {
				return ResultCode.<VideoTimelineInfo> getFailure(AubeErrorCodeConstants.CODE_DATA_ERROR, "揭晓时间必须在开始结束时间之内"); 
			}
		}
		Expression params = new Expression();
		params.eq(VideoTimelineInfo.TIMELINE_VIDEOID, timeline.getVideoid());
		List<VideoTimelineInfo> tlList = mongoService.getObjectList(VideoTimelineInfo.class, params);
		Collections.sort(tlList, new PropertyComparator<VideoTimelineInfo>(VideoTimelineInfo.TIMELINE_STARTTIME, true, true));
		if (CollectionUtils.isNotEmpty(tlList)) {
			// 同类型的做判断 TL_TYPE_2_TABS_CACHE
			String tabs = AubeConstants.TL_TYPE_2_TABS_CACHE.getIfPresent(timeline.getTlType());
			for (VideoTimelineInfo tl : tlList) {
				String tlTab = AubeConstants.TL_TYPE_2_TABS_CACHE.getIfPresent(tl.getTlType());
				// 同一个tab的做时间线判断
				if (StringUtils.equals(tabs, tlTab)) {
					if ((tl.gainStartTime() > timeline.gainStartTime() && tl.gainStartTime() < timeline.gainEndTime())
							|| (tl.gainEndTime() > timeline.gainStartTime() && tl.gainEndTime() < timeline.gainEndTime())) {
						return ResultCode.<VideoTimelineInfo> getFailure(AubeErrorCodeConstants.CODE_TL_TIME_REPEAT, tl.getTlTitle());
					}
				}
			}
		}
		return ResultCode.<VideoTimelineInfo> getSuccessReturn(timeline);
	}

	@Override
	public <T extends TimelineExtraBase> ResultCode<List<T>> getTimelineExtraList(VideoTimelineInfo timeline) {
		Expression params = new Expression();
		params.eq(TimelineExtraBase.EXTRA_TLID, timeline.realId().toString());
		ResultCode<VideoTimelineExtraEnum> extraEnumCode = VideoTimelineExtraEnum.getExtraEnumByTlType(timeline.getTlType());
		if (!extraEnumCode.isSuccess()) {
			return ResultCode.<List<T>> getFailure(extraEnumCode.getErrcode(), extraEnumCode.getErrParams());
		}
		VideoTimelineExtraEnum extraEnum = extraEnumCode.getRetval();
		List<T> extraList = (List<T>) mongoService.getObjectList(extraEnum.getExtraClazz(), params);
		return ResultCode.<List<T>> getSuccessReturn(extraList);
	}
}

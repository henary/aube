package com.aube.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aube.constans.MongoData;
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
		Expression params = new Expression();
		params.eq(TimelineExtraBase.EXTRA_TLID, timeline.realId().toString());
		ResultCode<VideoTimelineExtraEnum> extraEnumCode = VideoTimelineExtraEnum.getExtraEnumByTlType(timeline.getTlType());
		if (!extraEnumCode.isSuccess()) {
			return ResultCode.<VideoTimelineInfo>getFailure(extraEnumCode.getErrcode(), extraEnumCode.getErrmsg());
		}
		VideoTimelineExtraEnum extraEnum = extraEnumCode.getRetval();
		mongoService.removeObjectList(extraEnum.getExtraClazz(), params);
		mongoService.saveOrUpdateObjectList(extraList, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM);
		mongoService.saveOrUpdateObject(timeline, VideoTimelineInfo.TIMELINE_ID);
		return ResultCode.<VideoTimelineInfo> getSuccessReturn(timeline);
	}
	
	
	@Override
	public <T extends TimelineExtraBase> ResultCode<List<T>> getTimelineExtraList(VideoTimelineInfo timeline) {
		Expression params = new Expression();
		params.eq(TimelineExtraBase.EXTRA_TLID, timeline.realId().toString());
		ResultCode<VideoTimelineExtraEnum> extraEnumCode = VideoTimelineExtraEnum.getExtraEnumByTlType(timeline.getTlType());
		if (!extraEnumCode.isSuccess()) {
			return ResultCode.<List<T>>getFailure(extraEnumCode.getErrcode(), extraEnumCode.getErrParams());
		}
		VideoTimelineExtraEnum extraEnum = extraEnumCode.getRetval();
		List<T> extraList = (List<T>) mongoService.getObjectList(extraEnum.getExtraClazz(), params);
		return ResultCode.<List<T>>getSuccessReturn(extraList);
	}
}

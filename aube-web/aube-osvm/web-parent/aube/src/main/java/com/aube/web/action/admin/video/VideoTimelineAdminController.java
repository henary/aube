package com.aube.web.action.admin.video;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aube.constans.MongoData;
import com.aube.constants.AubeConstants;
import com.aube.json.show.ShowInfo;
import com.aube.json.video.VideoTimelineInfo;
import com.aube.json.video.VideoInfo;
import com.aube.support.ResultCode;
import com.aube.util.BeanUtil;
import com.aube.util.DateUtil;
import com.aube.util.WebUtils;
import com.aube.web.action.admin.BaseAdminController;

/**
 * TODO 权限控制
 *
 */
@Controller
public class VideoTimelineAdminController extends BaseAdminController {
	@RequestMapping("/admin/video/timelineList.xhtml")
	public String timeList(String videoid, String tabsType, HttpServletRequest request, ModelMap model) {
		ResultCode<VideoInfo> videoInfoCode = getVideoInfoById(videoid, request);
		if (!videoInfoCode.isSuccess()) {
			return showErrorPage(videoInfoCode.getErrmsg(), model);
		}
		VideoInfo video = videoInfoCode.getRetval();
		ResultCode<ShowInfo> showCode = getShowInfoById(video.getShowid(), request);
		if (!showCode.isSuccess()) {
			return showErrorPage(videoInfoCode.getErrmsg(), model);
		}
		if (StringUtils.isBlank(tabsType)) {
			tabsType = AubeConstants.TABS_TL_ALL;
		}
		model.put("tabs2TypeListMap", AubeConstants.TABS_2TYPE_LIST_MAP);
		model.put("tabsType", tabsType);
		model.put("video", video);
		model.put("show", showCode.getRetval());
		
		List<VideoTimelineInfo> timelineList = mongoService.getObjectList(VideoTimelineInfo.class);
		model.put("timelineList", timelineList);
		return "admin/video/timeline.vm";
	}

	@RequestMapping("/admin/video/getTimeline.xhtml")
	public String timelineDetail(String tlid, String videoid, String tlType, ModelMap model,
			HttpServletRequest request) {
		ResultCode<VideoInfo> videoCode = getVideoInfoById(videoid, request);
		if (!videoCode.isSuccess()) {
			return showErrorPage(videoCode.getErrmsg(), model);
		}
		VideoInfo video = videoCode.getRetval();
		model.put("duration", video.getDuration() == null || video.getDuration() ==0 ? AubeConstants.VIDEO_DEFAULT_DURATION : video.getDuration());
		if (StringUtils.isNotBlank(tlid)) {
			VideoTimelineInfo info = mongoService.getObjectById(VideoTimelineInfo.class, VideoTimelineInfo.TIMELINE_ID, tlid);
			if (StringUtils.isBlank(tlType)) {
				tlType = info.getTlType();
				videoid = info.getVideoid();
			}
			model.put("info", info);
		} else {
			model.put("tlid", MongoData.buildId());
		}

		model.put("videoid", videoid);
		return "admin/video/timeline/timeline" + tlType + ".vm";
	}
	
	@RequestMapping("/admin/video/saveTimelineVS.xhtml")
	@ResponseBody
	public String saveTimeline(String tlid, String optionList, HttpServletRequest request) {
		Map<String, String> reqMap = WebUtils.getRequestMap(request);
		VideoTimelineInfo timeline = mongoService.getObjectById(VideoTimelineInfo.class, VideoTimelineInfo.TIMELINE_ID, tlid);
		if (timeline == null) {
			timeline = new VideoTimelineInfo();
			timeline.set_id(tlid);
			timeline.setTlid(tlid);
			// TODO 人员
			timeline.setAddTime(DateUtil.getCurFullTimestampStr());
		}
		BeanUtil.copyProperties(timeline, reqMap);
		timeline.setUpdTime(DateUtil.getCurFullTimestampStr());
		mongoService.saveOrUpdateObject(timeline, VideoTimelineInfo.TIMELINE_ID);
		return result2Json(ResultCode.SUCCESS);
	}
}

package com.aube.web.action.admin.video;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aube.constans.MongoData;
import com.aube.constant.AubeErrorCodeConstants;
import com.aube.constants.AubeConstants;
import com.aube.constants.VideoTimelineExtraEnum;
import com.aube.constants.common.StatusConstants;
import com.aube.json.show.ShowInfo;
import com.aube.json.video.VideoInfo;
import com.aube.json.video.VideoTimelineInfo;
import com.aube.json.video.timeline.TimelineExtraBase;
import com.aube.json.video.timeline.TimelineExtraMulticamera;
import com.aube.json.video.timeline.TimelineExtraQA;
import com.aube.mdb.operation.Expression;
import com.aube.service.TimelineService;
import com.aube.support.MultiPropertyComparator;
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
	
	@Autowired@Qualifier("timelineService")
	private TimelineService timelineService;
	
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
		return "admin/video/timeline.vm";
	}

	
	@RequestMapping("/admin/video/ajax/timelineList.xhtml")
	public String ajaxTimeline(String tabsType, HttpServletRequest request, ModelMap model) {
		List<VideoTimelineInfo> timelineList = mongoService.getObjectList(VideoTimelineInfo.class);
		Collections.sort(timelineList, new MultiPropertyComparator<VideoTimelineInfo>(new String[]{"startTime", "endTime"}, new boolean[]{Boolean.TRUE, Boolean.TRUE}));
		model.put("timelineList", timelineList);
		return "admin/video/timeline/detail/timeline_table.vm";
	}
	@RequestMapping("/admin/video/getTimeline.xhtml")
	public String timelineDetail(String tlid, String videoid, String tlType, ModelMap model,
			HttpServletRequest request) {
		if (StringUtils.isNotBlank(tlid)) {
			VideoTimelineInfo timeline = mongoService.getObjectById(VideoTimelineInfo.class, VideoTimelineInfo.TIMELINE_ID, tlid);
			if (StringUtils.isBlank(tlType)) {
				tlType = timeline.getTlType();
			}
			if (StringUtils.isBlank(videoid)) {
				videoid = timeline.getVideoid();
			}
			model.put("info", BeanUtil.getBeanMap(timeline));
			ResultCode<List<TimelineExtraBase>> extraListCode = timelineService.getTimelineExtraList(timeline);
			if (extraListCode.isSuccess()) {
				List<TimelineExtraBase> extraList = extraListCode.getRetval();
				Collections.sort(extraList, new PropertyComparator<>("extraSortNum", true, true));
				model.put("extraList", extraList);
			}
		} else {
			tlid = MongoData.buildId();
		}
		ResultCode<VideoInfo> videoCode = getVideoInfoById(videoid, request);
		if (!videoCode.isSuccess()) {
			return showErrorPage(videoCode.getErrmsg(), model);
		}
		VideoInfo video = videoCode.getRetval();
		ResultCode<ShowInfo> showCode = getShowInfoById(video.getShowid(), request);
		if (!showCode.isSuccess()) {
			return showErrorPage(showCode.getErrmsg(), model);
		}
		model.put("tlid", tlid);
		model.put("video", videoCode.getRetval());
		model.put("show", showCode.getRetval());
		model.put("duration", video.getDuration() == null || video.getDuration() ==0 ? AubeConstants.VIDEO_DEFAULT_DURATION : video.getDuration());
		model.put("videoid", videoid);
		return "admin/video/timeline/timeline" + tlType + ".vm";
	}
	
	private VideoTimelineInfo createTimelineInfo(String tlid, Map<String, String> reqMap) {
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
		return timeline;
	}
	
	@RequestMapping("/admin/video/saveTimelineVS.xhtml")
	@ResponseBody
	public String saveTimelineVS(String tlid, HttpServletRequest request) {
		Map<String, String> reqMap = WebUtils.getRequestMap(request);
		VideoTimelineInfo timeline = createTimelineInfo(tlid, reqMap);
		timeline.setTlType(VideoTimelineExtraEnum.VS.getTlType());
		ResultCode<VideoTimelineInfo> timelineCode = timelineService.saveVideoTimeline(timeline);
		if (!timelineCode.isSuccess()) {
			return result2Json(timelineCode);
		}
		return result2Json(ResultCode.SUCCESS);
	}
	
	@RequestMapping("/admin/video/saveTimelineVote.xhtml")
	@ResponseBody
	public String saveTimelineVote(String tlid, HttpServletRequest request) {
		Map<String, String> reqMap = WebUtils.getRequestMap(request);
		VideoTimelineInfo timeline = createTimelineInfo(tlid, reqMap);
		timeline.setTlType(VideoTimelineExtraEnum.VOTE.getTlType());
		ResultCode<VideoTimelineInfo> timelineCode = timelineService.saveVideoTimeline(timeline);
		if (!timelineCode.isSuccess()) {
			return result2Json(timelineCode);
		}
		return result2Json(ResultCode.SUCCESS);
	}
	
	
	@RequestMapping("/admin/video/saveTimelineQA.xhtml")
	@ResponseBody
	public String saveTimelineQA(String tlid, String[] optionValue, String[] h5url, String[] qaPicInfo,String[] answerDesc, String answer, Integer[] extraSortNum, HttpServletRequest request) {
		Map<String, String> reqMap = WebUtils.getRequestMap(request);
		VideoTimelineInfo timeline = createTimelineInfo(tlid, reqMap);
		timeline.setTlType(VideoTimelineExtraEnum.QA.getTlType());
		List<TimelineExtraQA> qaList = new ArrayList<TimelineExtraQA>();
		for (int i = 0; i < optionValue.length; i++) {
			TimelineExtraQA extraQA = new TimelineExtraQA();
			String rid = MongoData.buildId();
			extraQA.set_id(rid);
			extraQA.setRid(rid);
			extraQA.setTlid(timeline.getTlid());
			extraQA.setOptionValue(optionValue[i]);
			extraQA.setPicInfo(qaPicInfo[i]);
			extraQA.setExtraSortNum(extraSortNum[i]);
			if (StringUtils.equals(answerDesc[i], answer)) {
				extraQA.setAnswer(StatusConstants.STATUS_Y);
			} else {
				extraQA.setAnswer(StatusConstants.STATUS_N);
			}
			extraQA.setH5url(h5url[i]);
			qaList.add(extraQA);
		}
		ResultCode<VideoTimelineInfo> timelineCode = timelineService.saveVideoTimeline(timeline, qaList);
		if (!timelineCode.isSuccess()) {
			return result2Json(timelineCode);
		}
		return result2Json(ResultCode.SUCCESS);
	}
	
	@RequestMapping("/admin/video/saveTimelineMc.xhtml")
	@ResponseBody
	public String saveTimelineMc(String tlid, HttpServletRequest request, ModelMap model) {
		Expression params = new Expression();
		params.eq(TimelineExtraMulticamera.EXTRA_TLID, tlid);
		List<TimelineExtraMulticamera> extraMulticameraList = mongoService.getObjectList(TimelineExtraMulticamera.class, params);
		if (CollectionUtils.isEmpty(extraMulticameraList)) {
			return result2Json(ResultCode.<String>getFailure(AubeErrorCodeConstants.CODE_MC_LIST_NULL));
		}
		Map<String, String> reqMap = WebUtils.getRequestMap(request);
		VideoTimelineInfo timeline = createTimelineInfo(tlid, reqMap);
		timeline.setTlType(VideoTimelineExtraEnum.MULTICAMERA.getTlType());
		ResultCode<VideoTimelineInfo> timelineCode = timelineService.saveVideoTimeline(timeline);
		if (!timelineCode.isSuccess()) {
			return result2Json(timelineCode);
		}
		return result2Json(ResultCode.SUCCESS);
	}
	
	
	@RequestMapping("/admin/video/saveTimelineGoods.xhtml")
	@ResponseBody
	public String saveTimelineGoods(String tlid, HttpServletRequest request, ModelMap model) {
		return "";
	}
	
	
	@RequestMapping("/admin/video/saveTimelineInfo.xhtml")
	@ResponseBody
	public String saveTimelineInfo(String tlid, HttpServletRequest request, ModelMap model) {
		Map<String, String> reqMap = WebUtils.getRequestMap(request);
		VideoTimelineInfo timeline = createTimelineInfo(tlid, reqMap);
		timeline.setTlType(VideoTimelineExtraEnum.INFO.getTlType());
		ResultCode<VideoTimelineInfo> timelineCode = timelineService.saveVideoTimeline(timeline);
		if (!timelineCode.isSuccess()) {
			return result2Json(timelineCode);
		}
		return result2Json(ResultCode.SUCCESS);
	}
}

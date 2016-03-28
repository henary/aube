package com.aube.web.action.admin.video;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.aube.constans.MongoData;
import com.aube.constants.AubeConstants;
import com.aube.constants.VideoTimelineExtraEnum;
import com.aube.constants.common.StatusConstants;
import com.aube.json.show.ShowInfo;
import com.aube.json.video.VideoInfo;
import com.aube.json.video.VideoTimelineInfo;
import com.aube.json.video.timeline.TimelineExtraBase;
import com.aube.json.video.timeline.TimelineExtraQA;
import com.aube.json.video.timeline.TimelineExtraVS;
import com.aube.json.video.timeline.TimelineExtraVote;
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
	public String saveTimelineVS(String tlid, String[] options, HttpServletRequest request) {
		Map<String, String> reqMap = WebUtils.getRequestMap(request);
		VideoTimelineInfo timeline = createTimelineInfo(tlid, reqMap);
		timeline.setTlType(VideoTimelineExtraEnum.VS.getTlType());
		List<TimelineExtraVS> vsList = new ArrayList<TimelineExtraVS>();
		for (String option : options) {
			TimelineExtraVS vsInfo = new TimelineExtraVS();
			String rid = MongoData.buildId();
			vsInfo.set_id(rid);
			vsInfo.setRid(rid);
			vsInfo.setTlid(timeline.getTlid());
			vsInfo.setOption(option);
			vsList.add(vsInfo);
		}
		ResultCode<VideoTimelineInfo> timelineCode = timelineService.saveVideoTimeline(timeline, vsList);
		if (!timelineCode.isSuccess()) {
			return result2Json(timelineCode);
		}
		return result2Json(ResultCode.SUCCESS);
	}
	
	@RequestMapping("/admin/video/saveTimelineVote.xhtml")
	@ResponseBody
	public String saveTimelineVote(String tlid, String[] options, HttpServletRequest request) {
		Map<String, String> reqMap = WebUtils.getRequestMap(request);
		VideoTimelineInfo timeline = createTimelineInfo(tlid, reqMap);
		timeline.setTlType(VideoTimelineExtraEnum.VOTE.getTlType());
		List<TimelineExtraVote> voteList = new ArrayList<TimelineExtraVote>();
		for (String option : options) {
			TimelineExtraVote voteInfo = new TimelineExtraVote();
			String rid = MongoData.buildId();
			voteInfo.set_id(rid);
			voteInfo.setRid(rid);
			voteInfo.setTlid(timeline.getTlid());
			voteInfo.setOption(option);
			voteList.add(voteInfo);
		}
		ResultCode<VideoTimelineInfo> timelineCode = timelineService.saveVideoTimeline(timeline, voteList);
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
}

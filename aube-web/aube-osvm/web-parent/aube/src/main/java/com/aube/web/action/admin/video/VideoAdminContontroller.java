package com.aube.web.action.admin.video;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aube.constans.MongoData;
import com.aube.constants.AubeConstants;
import com.aube.json.show.ShowInfo;
import com.aube.json.video.VideoInfo;
import com.aube.mdb.helper.BuilderUtils;
import com.aube.mdb.operation.Expression;
import com.aube.support.ResultCode;
import com.aube.util.BeanUtil;
import com.aube.util.DateUtil;
import com.aube.util.WebUtils;
import com.aube.web.action.admin.BaseAdminController;


/**
 * TODO video用户权限控制
 *
 */
@Controller
public class VideoAdminContontroller extends BaseAdminController {
	@RequestMapping("/admin/video/list.xhtml")
	public String showlist(String showid, HttpServletRequest request, ModelMap model) {
		ResultCode<ShowInfo> showCode = getShowInfoById(showid, request);
		if (!showCode.isSuccess()) {
			// TODO 
		}
		model.put("show", showCode.getRetval());
		Expression params = new Expression();
		params.eq(VideoInfo.SHOW_ID, showid);
		List<VideoInfo> videoList = mongoService.getObjectList(VideoInfo.class, params);
		Collections.sort(videoList, new PropertyComparator<VideoInfo>(VideoInfo.VIDEO_SORTNUM, false, false));
		model.put("videoList", videoList);
		model.put("showid", showid);
		model.put("videoStatusMap", AubeConstants.VIDEO_STATUS_MAP);
		return "admin/video/videoList.vm";
	}

	@RequestMapping("/admin/video/getVideoDetail.xhtml")
	public String showDetail(String showid, String videoid, HttpServletRequest request, ModelMap model) {
		if (StringUtils.isBlank(videoid)) {
			videoid = MongoData.buildId();
		} else {
			ResultCode<VideoInfo> videoCode = getVideoInfoById(videoid, request);
			if (!videoCode.isSuccess()) {
				model.put("errorMsg", videoCode.getErrmsg());
			}
			VideoInfo video = videoCode.getRetval();
			model.put("info", video);
		}
		model.put("videoid", videoid);
		model.put("showid", showid);
		return "admin/video/videoDetail.vm";
	}

	@RequestMapping("/admin/video/removeVideo.xhtml")
	@ResponseBody
	public String removeVideo(String videoid, HttpServletRequest request) {
		ResultCode<VideoInfo> videoCode = getVideoInfoById(videoid, request);
		return result2Json(videoCode);
	}

	@RequestMapping("/admin/video/saveVideo.xhtml")
	@ResponseBody
	public String saveVideo(String videoid, HttpServletRequest request) {
		return result2Json(ResultCode.<String>getFailureReturn("asdasd"));
	/*	Map<String, String> reqMap = WebUtils.getRequestMap(request);
		VideoInfo info = null;
		if (StringUtils.isNotBlank(videoid)) {
			info = mongoService.getObjectById(VideoInfo.class, VideoInfo.VIDEO_ID, videoid);
		}
		if (info == null) {
			info = new VideoInfo();
			info.set_id(videoid);
			info.setVideoStatus(AubeConstants.VIDEO_STATUS_DRAFT);
			info.setAddTime(DateUtil.getCurFullTimestampStr());
		}
		BeanUtil.copyProperties(info, reqMap);
		info.setUpdTime(DateUtil.getCurFullTimestampStr());
		mongoService.saveOrUpdateObject(info, VideoInfo.VIDEO_ID);
		return result2Json(ResultCode.SUCCESS);*/
	}

	
	
	@RequestMapping("/admin/video/modifyStatus.xhtml")
	@ResponseBody
	public String modifyStatus(String videoid, String status, HttpServletRequest request) {
		ResultCode<VideoInfo> videoCode = getVideoInfoById(videoid, request);
		if (!videoCode.isSuccess()) {
			return result2Json(videoCode);
		}
		// TODO 所属用户判断
		mongoService.execUpdate(BuilderUtils.prepareUpdate(VideoInfo.class)
				.setCondition(new Expression().eq(MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM, videoid)).setUpdateFirst(true)
				.setInsert4NotFind(false).addData(VideoInfo.VIDEO_STATUS, status));
		return result2Json(ResultCode.SUCCESS);
	}
}

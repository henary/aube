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
import com.aube.constant.ShowErrorCodeConstants;
import com.aube.json.video.VideoInfo;
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
	public String showlist(String showid, ModelMap model) {
		Expression params = new Expression();
		params.eq(VideoInfo.SHOW_ID, showid);
		List<VideoInfo> videoList = mongoService.getObjectList(VideoInfo.class, params);
		model.put("videoList", videoList);
		model.put("showid", showid);
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
		Map<String, String> reqMap = WebUtils.getRequestMap(request);
		VideoInfo info = null;
		if (StringUtils.isNotBlank(videoid)) {
			info = mongoService.getObjectById(VideoInfo.class, VideoInfo.VIDEO_ID, videoid);
		}
		if (info == null) {
			info = new VideoInfo();
			info.set_id(videoid);
			info.setAddTime(DateUtil.getCurFullTimestampStr());
		}
		BeanUtil.copyProperties(info, reqMap);
		info.setUpdTime(DateUtil.getCurFullTimestampStr());
		mongoService.saveOrUpdateObject(info, VideoInfo.VIDEO_ID);
		return result2Json(ResultCode.SUCCESS);
	}

	private ResultCode<VideoInfo> getVideoInfoById(String videoid, HttpServletRequest request) {
		VideoInfo video = mongoService.getObjectById(VideoInfo.class, VideoInfo.VIDEO_ID, videoid);
		if (video == null) {
			return ResultCode.<VideoInfo> getFailure(ShowErrorCodeConstants.CODE_SHOW_NOT_EXITS);
		}
		// TODO 判断是否属于登录帐号的appkey ShowErrorCodeConstants.CODE_SHOW_NO_OPERA_AUTH
		return ResultCode.<VideoInfo> getSuccessReturn(video);
	}
}

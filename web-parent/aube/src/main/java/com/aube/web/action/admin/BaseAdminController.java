package com.aube.web.action.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aube.constant.ShowErrorCodeConstants;
import com.aube.json.show.ShowInfo;
import com.aube.json.video.VideoInfo;
import com.aube.mongo.MongoService3;
import com.aube.support.ResultCode;
import com.aube.util.JsonUtils;
import com.aube.web.action.BaseController;

public class BaseAdminController extends BaseController {
	@Autowired
	@Qualifier("mongoService")
	protected MongoService3 mongoService;

	protected <T> String result2Json(ResultCode<T> result) {
		return "var data=" + JsonUtils.writeObjectToJson(result);
	}
	
	
	protected ResultCode<VideoInfo> getVideoInfoById(String videoid, HttpServletRequest request) {
		VideoInfo video = mongoService.getObjectById(VideoInfo.class, VideoInfo.VIDEO_ID, videoid);
		if (video == null) {
			return ResultCode.<VideoInfo> getFailure(ShowErrorCodeConstants.CODE_SHOW_NOT_EXITS);
		}
		// TODO 判断是否属于登录帐号的appkey ShowErrorCodeConstants.CODE_SHOW_NO_OPERA_AUTH
		return ResultCode.<VideoInfo> getSuccessReturn(video);
	}
	
	
	protected ResultCode<ShowInfo> getShowInfoById(String showid, HttpServletRequest request) {
		ShowInfo show = mongoService.getObjectById(ShowInfo.class, ShowInfo.SHOW_ID, showid);
		if (show == null) {
			return ResultCode.<ShowInfo> getFailure(ShowErrorCodeConstants.CODE_SHOW_NOT_EXITS);
		}
		// TODO 判断是否属于登录帐号的appkey ShowErrorCodeConstants.CODE_SHOW_NO_OPERA_AUTH
		return ResultCode.<ShowInfo> getSuccessReturn(show);
	}
}

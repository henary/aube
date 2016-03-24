package com.aube.web.action.admin.video;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aube.web.action.admin.BaseAdminController;

/**
 * TODO 权限控制
 *
 */
@Controller
public class VideoTimelineAdminController extends BaseAdminController {
	@RequestMapping("/admin/video/timelineList.xhtml")
	public String timeList(String videoid, HttpServletRequest request, ModelMap model) {
		model.put("videoid", videoid);
		return "";
	}
}

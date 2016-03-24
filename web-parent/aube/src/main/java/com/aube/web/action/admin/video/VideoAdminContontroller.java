package com.aube.web.action.admin.video;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aube.web.action.admin.BaseAdminController;

@Controller
public class VideoAdminContontroller extends BaseAdminController {
	@RequestMapping("/admin/video/list.xhtml")
	public String videoList(String showid, HttpServletRequest request, ModelMap model) {
		
		return "";
	}
}

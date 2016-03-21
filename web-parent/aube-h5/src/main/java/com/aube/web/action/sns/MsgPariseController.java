package com.aube.web.action.sns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.aube.support.ResultCode;
import com.aube.web.action.AubeH5BaseController;


@Controller
public class MsgPariseController extends AubeH5BaseController {
	@Autowired@Qualifier("")
	@RenderMapping("/msgparise.xhtml")
	@ResponseBody
	public String msgparise() {
	//	ResultCode<>
		return "";
	}
}

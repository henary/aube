package com.aube.web.action.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aube.web.action.BaseController;

@Controller
public class AubeTestController extends BaseController {
	@RequestMapping("/test/vm.xhtml")
	public String testvm() {
		return "test/index.vm";
	}
	
	@RequestMapping("/test/index.xhtml")
	@ResponseBody
	public String testBody() {
		return "asssssssssd";
	}
}

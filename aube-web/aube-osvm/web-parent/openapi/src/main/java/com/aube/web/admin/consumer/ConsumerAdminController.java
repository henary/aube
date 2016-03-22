package com.aube.web.admin.consumer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aube.web.admin.OpenapiBaseAdminController;

@Controller
public class ConsumerAdminController extends OpenapiBaseAdminController {
	@RequestMapping("/admin/consumer/getAll.xhtml")
	public String getAllConsumer(ModelMap model) {
		return "";
	}
}

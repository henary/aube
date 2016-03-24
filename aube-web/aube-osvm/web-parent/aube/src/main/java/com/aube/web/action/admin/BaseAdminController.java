package com.aube.web.action.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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
}

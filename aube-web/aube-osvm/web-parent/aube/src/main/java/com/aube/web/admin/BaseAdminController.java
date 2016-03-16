package com.aube.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aube.mongo.MongoService3;
import com.aube.web.action.BaseController;

public class BaseAdminController extends BaseController {
	@Autowired
	@Qualifier("mongoService")
	protected MongoService3 mongoService;
}

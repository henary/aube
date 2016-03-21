package com.aube.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aube.mongo.MongoService3;

public class AubeSnsBaseController extends BaseController {
	@Autowired@Qualifier("mongoService")
	protected MongoService3 mongoService;
}

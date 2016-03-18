package com.aube.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aube.Config;
import com.aube.mongo.MongoService3;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

public class BaseService {
	@Autowired
	@Qualifier("config")
	protected Config config;
	
	@Autowired@Qualifier("mongoService")
	protected MongoService3 mongoService;
	
	
	protected transient final AubeLogger logger = LoggerUtils.getLogger(this.getClass());
	
	
}

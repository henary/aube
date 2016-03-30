package com.aube.web.action.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aube.constans.MongoData;
import com.aube.constants.AubeConfigConstants;
import com.aube.json.AubeConfig;
import com.aube.mongo.MongoService3;
import com.aube.util.JsonUtils;
import com.aube.vo.BokeccConfigVo;
import com.aube.web.action.BaseController;

@Controller
public class AubeTestController extends BaseController {
	@Autowired@Qualifier("mongoService")
	private MongoService3 mongoService;
	@RequestMapping("/test/ccConfig.xhtml")
	public String ccConfig() {
		BokeccConfigVo configVo = new BokeccConfigVo();
		configVo.setAppkey("E778404B771671CE");
		configVo.setAppSecret("l7l7PX9hVOwkxx7gKg8GeAeFBebx8Tau");
		
		AubeConfig aubeConfig = new AubeConfig();
		aubeConfig.set_id(AubeConfigConstants.CC_CONFIG);
		aubeConfig.setContent(JsonUtils.writeObjectToJson(configVo));
		mongoService.saveOrUpdateObject(aubeConfig, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM);
		return "ss";
	}
	
}

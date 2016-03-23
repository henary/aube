package com.aube.web.action.commons;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aube.constans.MongoData;
import com.aube.constant.ErrorCodeConstant;
import com.aube.json.common.PicInfo;
import com.aube.mongo.MongoService3;
import com.aube.resp.vo.pic.PicInfoRespVo;
import com.aube.support.ResultCode;
import com.aube.untrans.common.AubePicService;
import com.aube.util.BeanUtil;
import com.aube.util.JsonUtils;
import com.aube.util.StringUtil;
import com.aube.util.WebUtils;
import com.aube.web.action.BaseController;
import com.aube.web.support.AubeMultipartResolver;

@Controller
public class UpLoadFileController extends BaseController {

	@Autowired
	@Qualifier("aubeMultipartResolver")
	private AubeMultipartResolver aubeMultipartResolver;
	@Autowired
	@Qualifier("aubePicService")
	private AubePicService aubePicService;
	@Autowired
	@Qualifier("mongoService")
	private MongoService3 mongoService;

	@RequestMapping("/common/uploadSignPicture.xhtml")
	@ResponseBody
	public String uploadSignPicture(HttpServletRequest request, ModelMap model) {
		MultipartHttpServletRequest multipartRequest = aubeMultipartResolver.resolveMultipart(request);
		ResultCode<PicInfoRespVo> resultCode = uploadSignleFile(multipartRequest, model);
		if (resultCode.isSuccess()) {
			PicInfoRespVo respVo = resultCode.getRetval();
			PicInfo picInfo = new PicInfo();
			BeanUtil.copyProperties(picInfo, respVo);
			picInfo.set_id(StringUtil.md5(picInfo.getPicurl()));
			mongoService.saveOrUpdateObject(picInfo, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM);
		}
		return JsonUtils.writeObjectToJson(resultCode);
	}

	private ResultCode<PicInfoRespVo> uploadSignleFile(MultipartHttpServletRequest multipartRequest, ModelMap model) {
		Map<String, String> reqMap = WebUtils.getRequestMap(multipartRequest);
		model.putAll(reqMap);
		String picTag = reqMap.get("picTag");
		String relatedId = reqMap.get("relatedId");
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			return aubePicService.saveToTempPic(entry.getValue(), picTag, relatedId);
		}
		return ResultCode.<PicInfoRespVo> getFailure(ErrorCodeConstant.CODE_UPLOAD_EMPTY);
	}
}

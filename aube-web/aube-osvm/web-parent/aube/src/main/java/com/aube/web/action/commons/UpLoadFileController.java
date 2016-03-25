package com.aube.web.action.commons;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.aube.json.video.VideoInfo;
import com.aube.mdb.helper.BuilderUtils;
import com.aube.mdb.operation.Expression;
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
			picInfo.set_id(StringUtil.md5(picInfo.getFileurl()));
			mongoService.saveOrUpdateObject(picInfo, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM);
		}
		return JsonUtils.writeObjectToJson(resultCode);
	}

	@RequestMapping("/admin/upload/video/uploadSignVideo.xhtml")
	@ResponseBody
	public String uploadSignVideo(HttpServletRequest request, ModelMap model) {
		MultipartHttpServletRequest multipartRequest = aubeMultipartResolver.resolveMultipart(request);
		ResultCode<PicInfoRespVo> resultCode = uploadSignleFile(multipartRequest, model);
		if (resultCode.isSuccess()) {
			PicInfoRespVo respVo = resultCode.getRetval();
			PicInfo picInfo = new PicInfo();
			BeanUtil.copyProperties(picInfo, respVo);
			picInfo.set_id(StringUtil.md5(picInfo.getFileurl()));
			mongoService.saveOrUpdateObject(picInfo, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM);

			/**
			 * 更新视频信息
			 * TODO 所属用户判断
			 */
			Map<String, Object> updMap = new HashMap<String, Object>();
			updMap.put("duration", picInfo.getDuration());
			updMap.put("filePath", picInfo.getFileurl());
			mongoService.execUpdate(BuilderUtils.prepareUpdate(VideoInfo.class)
					.setCondition(new Expression().eq(MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM, picInfo.getRelatedId())).setUpdateFirst(true)
					.setInsert4NotFind(false).addData(updMap));
		}
		return JsonUtils.writeObjectToJson(resultCode);
	}

	private ResultCode<PicInfoRespVo> uploadSignleFile(MultipartHttpServletRequest multipartRequest, ModelMap model) {
		Map<String, String> reqMap = WebUtils.getRequestMap(multipartRequest);
		model.putAll(reqMap);
		String fileTag = reqMap.get("fileTag");
		String relatedId = reqMap.get("relatedId");
		String fileType = reqMap.get("fileType");
		if (StringUtils.isBlank(fileTag) || StringUtils.isBlank(relatedId)) {
			return ResultCode.<PicInfoRespVo> getFailure(ErrorCodeConstant.CODE_UPLOAD_PARAM_ERROR);
		}
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			if (StringUtils.equals(fileType, "video")) {
				return aubePicService.saveToTempVideo(entry.getValue(), fileTag, relatedId);
			} else {
				return aubePicService.saveToTempPic(entry.getValue(), fileTag, relatedId);
			}
		}
		return ResultCode.<PicInfoRespVo> getFailure(ErrorCodeConstant.CODE_UPLOAD_EMPTY);
	}
}

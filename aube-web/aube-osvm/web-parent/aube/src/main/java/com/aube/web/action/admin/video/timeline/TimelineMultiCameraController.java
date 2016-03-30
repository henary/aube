package com.aube.web.action.admin.video.timeline;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aube.constans.MongoData;
import com.aube.json.video.timeline.TimelineExtraBase;
import com.aube.json.video.timeline.TimelineExtraMulticamera;
import com.aube.mdb.helper.BuilderUtils;
import com.aube.mdb.operation.Expression;
import com.aube.support.ResultCode;
import com.aube.util.BeanUtil;
import com.aube.util.WebUtils;
import com.aube.web.action.admin.BaseAdminController;

@Controller
public class TimelineMultiCameraController extends BaseAdminController {

	@RequestMapping("/admin/video/timeline/saveMultiCamera.xhtml")
	@ResponseBody
	public String saveMultiCamera(String rid, HttpServletRequest request, ModelMap model) {
		Map<String, String> reqMap = WebUtils.getRequestMap(request);
		TimelineExtraMulticamera multicamera = null;
		if (StringUtils.isNotBlank(rid)) {
			multicamera = mongoService.getObjectById(TimelineExtraMulticamera.class, TimelineExtraBase.EXTRA_RID, rid);
		}
		if (multicamera == null) {
			multicamera = new TimelineExtraMulticamera();
		}
		BeanUtil.copyProperties(multicamera, reqMap);
		if (StringUtils.isBlank(rid)) {
			rid = MongoData.buildId();
			multicamera.set_id(rid);
			multicamera.setRid(rid);
		}
		Expression params = new Expression();
		params.eq(TimelineExtraBase.EXTRA_TLID, multicamera.getTlid());
		List<TimelineExtraMulticamera> multicameraList = mongoService.getObjectList(TimelineExtraMulticamera.class, params);
		Integer sortNum = 1;
		if (CollectionUtils.isNotEmpty(multicameraList)) {
			Collections.sort(multicameraList, new PropertyComparator<TimelineExtraMulticamera>(TimelineExtraBase.EXTRA_SORTNUM, true, false));
			if (multicameraList.get(0).getExtraSortNum() != null) {
				sortNum = multicameraList.get(0).getExtraSortNum() + 1;
			}
		}
		// 新加的排序在最后
		multicamera.setExtraSortNum(sortNum);
		mongoService.saveOrUpdateObject(multicamera, TimelineExtraMulticamera.EXTRA_RID);
		return result2Json(ResultCode.SUCCESS);
	}

	@RequestMapping("/admin/video/timeline/removeMultiCamera.xhtml")
	@ResponseBody
	public String removeMultiCamera(String rid, ModelMap model) {
		mongoService.removeObjectById(TimelineExtraMulticamera.class, TimelineExtraBase.EXTRA_RID, rid);
		return result2Json(ResultCode.SUCCESS);
	}

	@RequestMapping("/admin/video/timeline/getMultiCameraByTlid.xhtml")
	public String getMultiCameraByTlid(String tlid, HttpServletRequest request, ModelMap model) {
		Expression params = new Expression();
		params.eq(TimelineExtraMulticamera.EXTRA_TLID, tlid);
		List<TimelineExtraMulticamera> extraMulticameraList = mongoService.getObjectList(TimelineExtraMulticamera.class, params);
		Collections.sort(extraMulticameraList, new PropertyComparator<TimelineExtraMulticamera>(TimelineExtraBase.EXTRA_SORTNUM, true, true));
		model.put("mcList", extraMulticameraList);
		return "admin/video/timeline/detail/multicamera_li.vm";
	}

	@RequestMapping("/admin/video/timeline/sortMultiCamera.xhtml")
	@ResponseBody
	public String sortMultiCamera(String rids, ModelMap model) {
		String[] ridArr = StringUtils.split(rids, ",");
		for (int i = 0; i < ridArr.length; i++) {
			mongoService.execUpdate(
					BuilderUtils.prepareUpdate(TimelineExtraMulticamera.class).setCondition(new Expression().eq(TimelineExtraBase.EXTRA_RID, ridArr[i]))
							.setUpdateFirst(true).setInsert4NotFind(false).addData(TimelineExtraBase.EXTRA_SORTNUM, i + 1));
		}
		return result2Json(ResultCode.SUCCESS);
	}
}

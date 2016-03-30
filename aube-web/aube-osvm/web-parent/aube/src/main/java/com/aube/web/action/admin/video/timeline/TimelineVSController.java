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
import com.aube.constant.AubeErrorCodeConstants;
import com.aube.json.video.timeline.TimelineExtraBase;
import com.aube.json.video.timeline.TimelineExtraVS;
import com.aube.mdb.helper.BuilderUtils;
import com.aube.mdb.operation.Expression;
import com.aube.support.ResultCode;
import com.aube.util.BeanUtil;
import com.aube.util.WebUtils;
import com.aube.web.action.admin.BaseAdminController;

@Controller
public class TimelineVSController extends BaseAdminController {

	@RequestMapping("/admin/video/timeline/saveVs.xhtml")
	@ResponseBody
	public String saveVs(String rid, HttpServletRequest request, ModelMap model) {
		Map<String, String> reqMap = WebUtils.getRequestMap(request);
		TimelineExtraVS vs = null;
		if (StringUtils.isNotBlank(rid)) {
			vs = mongoService.getObjectById(TimelineExtraVS.class, TimelineExtraBase.EXTRA_RID, rid);
		}
		if (vs == null) {
			vs = new TimelineExtraVS();
		}
		BeanUtil.copyProperties(vs, reqMap);
		if (StringUtils.isBlank(rid)) {
			String temprid = MongoData.buildId();
			vs.set_id(temprid);
			vs.setRid(temprid);
		}
		Expression params = new Expression();
		params.eq(TimelineExtraBase.EXTRA_TLID, vs.getTlid());
		List<TimelineExtraVS> vsList = mongoService.getObjectList(TimelineExtraVS.class, params);
		Integer sortNum = 1;
		if (CollectionUtils.isNotEmpty(vsList)) {
			if (StringUtils.isBlank(rid) && vsList.size() >= 2) {
				return result2Json(ResultCode.<String>getFailure(AubeErrorCodeConstants.CODE_VS_MOST2));
			}
			Collections.sort(vsList, new PropertyComparator<TimelineExtraVS>(TimelineExtraBase.EXTRA_SORTNUM, true, false));
			if (vsList.get(0).getExtraSortNum() != null) {
				sortNum = vsList.get(0).getExtraSortNum() + 1;
			}
		}
		// 新加的排序在最后
		vs.setExtraSortNum(sortNum);
		mongoService.saveOrUpdateObject(vs, TimelineExtraBase.EXTRA_RID);
		return result2Json(ResultCode.SUCCESS);
	}

	@RequestMapping("/admin/video/timeline/removeVs.xhtml")
	@ResponseBody
	public String removeVs(String rid, ModelMap model) {
		mongoService.removeObjectById(TimelineExtraVS.class, TimelineExtraBase.EXTRA_RID, rid);
		return result2Json(ResultCode.SUCCESS);
	}

	@RequestMapping("/admin/video/timeline/getVsListByTlid.xhtml")
	public String getVsListByTlid(String tlid, HttpServletRequest request, ModelMap model) {
		Expression params = new Expression();
		params.eq(TimelineExtraBase.EXTRA_TLID, tlid);
		List<TimelineExtraVS> vsList = mongoService.getObjectList(TimelineExtraVS.class, params);
		Collections.sort(vsList, new PropertyComparator<TimelineExtraVS>(TimelineExtraBase.EXTRA_SORTNUM, true, true));
		model.put("vsList", vsList);
		return "admin/video/timeline/detail/vs_li.vm";
	}

	@RequestMapping("/admin/video/timeline/sortVs.xhtml")
	@ResponseBody
	public String sortVs(String rids, ModelMap model) {
		String[] ridArr = StringUtils.split(rids, ",");
		for (int i = 0; i < ridArr.length; i++) {
			mongoService.execUpdate(
					BuilderUtils.prepareUpdate(TimelineExtraVS.class).setCondition(new Expression().eq(TimelineExtraBase.EXTRA_RID, ridArr[i]))
							.setUpdateFirst(true).setInsert4NotFind(false).addData(TimelineExtraBase.EXTRA_SORTNUM, i + 1));
		}
		return result2Json(ResultCode.SUCCESS);
	}
}

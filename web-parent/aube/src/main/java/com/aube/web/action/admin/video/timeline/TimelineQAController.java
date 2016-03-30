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
import com.aube.json.video.timeline.TimelineExtraQA;
import com.aube.mdb.helper.BuilderUtils;
import com.aube.mdb.operation.Expression;
import com.aube.support.ResultCode;
import com.aube.util.BeanUtil;
import com.aube.util.WebUtils;
import com.aube.web.action.admin.BaseAdminController;

@Controller
public class TimelineQAController extends BaseAdminController {

	@RequestMapping("/admin/video/timeline/saveQA.xhtml")
	@ResponseBody
	public String saveQA(String rid, HttpServletRequest request, ModelMap model) {
		Map<String, String> reqMap = WebUtils.getRequestMap(request);
		TimelineExtraQA qa = null;
		if (StringUtils.isNotBlank(rid)) {
			qa = mongoService.getObjectById(TimelineExtraQA.class, TimelineExtraBase.EXTRA_RID, rid);
		}
		if (qa == null) {
			qa = new TimelineExtraQA();
		}
		BeanUtil.copyProperties(qa, reqMap);
		if (StringUtils.isBlank(rid)) {
			String temprid = MongoData.buildId();
			qa.set_id(temprid);
			qa.setRid(temprid);
		}
		Expression params = new Expression();
		params.eq(TimelineExtraBase.EXTRA_TLID, qa.getTlid());
		List<TimelineExtraQA> qaList = mongoService.getObjectList(TimelineExtraQA.class, params);
		Integer sortNum = 1;
		if (CollectionUtils.isNotEmpty(qaList)) {
			Collections.sort(qaList, new PropertyComparator<TimelineExtraQA>(TimelineExtraBase.EXTRA_SORTNUM, true, false));
			if (qaList.get(0).getExtraSortNum() != null) {
				sortNum = qaList.get(0).getExtraSortNum() + 1;
			}
		}
		// 新加的排序在最后
		qa.setExtraSortNum(sortNum);
		mongoService.saveOrUpdateObject(qa, TimelineExtraBase.EXTRA_RID);
		return result2Json(ResultCode.SUCCESS);
	}

	@RequestMapping("/admin/video/timeline/removeQA.xhtml")
	@ResponseBody
	public String removeQA(String rid, ModelMap model) {
		mongoService.removeObjectById(TimelineExtraQA.class, TimelineExtraBase.EXTRA_RID, rid);
		return result2Json(ResultCode.SUCCESS);
	}

	@RequestMapping("/admin/video/timeline/getQAListByTlid.xhtml")
	public String getQAListByTlid(String tlid, HttpServletRequest request, ModelMap model) {
		Expression params = new Expression();
		params.eq(TimelineExtraBase.EXTRA_TLID, tlid);
		List<TimelineExtraQA> qaList = mongoService.getObjectList(TimelineExtraQA.class, params);
		Collections.sort(qaList, new PropertyComparator<TimelineExtraQA>(TimelineExtraBase.EXTRA_SORTNUM, true, true));
		model.put("qaList", qaList);
		return "admin/video/timeline/detail/qa_li.vm";
	}

	@RequestMapping("/admin/video/timeline/sortQA.xhtml")
	@ResponseBody
	public String sortQA(String rids, ModelMap model) {
		String[] ridArr = StringUtils.split(rids, ",");
		for (int i = 0; i < ridArr.length; i++) {
			mongoService.execUpdate(
					BuilderUtils.prepareUpdate(TimelineExtraQA.class).setCondition(new Expression().eq(TimelineExtraBase.EXTRA_RID, ridArr[i]))
							.setUpdateFirst(true).setInsert4NotFind(false).addData(TimelineExtraBase.EXTRA_SORTNUM, i + 1));
		}
		return result2Json(ResultCode.SUCCESS);
	}
}

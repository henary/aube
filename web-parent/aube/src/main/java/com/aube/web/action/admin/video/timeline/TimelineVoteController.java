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
import com.aube.json.video.timeline.TimelineExtraVote;
import com.aube.mdb.helper.BuilderUtils;
import com.aube.mdb.operation.Expression;
import com.aube.support.ResultCode;
import com.aube.util.BeanUtil;
import com.aube.util.WebUtils;
import com.aube.web.action.admin.BaseAdminController;

@Controller
public class TimelineVoteController extends BaseAdminController {

	@RequestMapping("/admin/video/timeline/saveVote.xhtml")
	@ResponseBody
	public String saveVote(String rid, HttpServletRequest request, ModelMap model) {
		Map<String, String> reqMap = WebUtils.getRequestMap(request);
		TimelineExtraVote vote = null;
		if (StringUtils.isNotBlank(rid)) {
			vote = mongoService.getObjectById(TimelineExtraVote.class, TimelineExtraBase.EXTRA_RID, rid);
		}
		if (vote == null) {
			vote = new TimelineExtraVote();
		}
		BeanUtil.copyProperties(vote, reqMap);
		if (StringUtils.isBlank(rid)) {
			rid = MongoData.buildId();
			vote.set_id(rid);
			vote.setRid(rid);
		}
		Expression params = new Expression();
		params.eq(TimelineExtraBase.EXTRA_TLID, vote.getTlid());
		List<TimelineExtraVote> voteList = mongoService.getObjectList(TimelineExtraVote.class, params);
		Integer sortNum = 1;
		if (CollectionUtils.isNotEmpty(voteList)) {
			Collections.sort(voteList, new PropertyComparator<TimelineExtraVote>(TimelineExtraBase.EXTRA_SORTNUM, true, false));
			if (voteList.get(0).getExtraSortNum() != null) {
				sortNum = voteList.get(0).getExtraSortNum() + 1;
			}
		}
		// 新加的排序在最后
		vote.setExtraSortNum(sortNum);
		mongoService.saveOrUpdateObject(vote, TimelineExtraBase.EXTRA_RID);
		return result2Json(ResultCode.SUCCESS);
	}

	@RequestMapping("/admin/video/timeline/removeVote.xhtml")
	@ResponseBody
	public String removeVote(String rid, ModelMap model) {
		mongoService.removeObjectById(TimelineExtraVote.class, TimelineExtraBase.EXTRA_RID, rid);
		return result2Json(ResultCode.SUCCESS);
	}

	@RequestMapping("/admin/video/timeline/getVoteListByTlid.xhtml")
	public String getVoteListByTlid(String tlid, HttpServletRequest request, ModelMap model) {
		Expression params = new Expression();
		params.eq(TimelineExtraBase.EXTRA_TLID, tlid);
		List<TimelineExtraVote> voteList = mongoService.getObjectList(TimelineExtraVote.class, params);
		Collections.sort(voteList, new PropertyComparator<TimelineExtraVote>(TimelineExtraBase.EXTRA_SORTNUM, true, true));
		model.put("voteList", voteList);
		return "admin/video/timeline/detail/vote_li.vm";
	}

	@RequestMapping("/admin/video/timeline/sortVote.xhtml")
	@ResponseBody
	public String sortVote(String rids, ModelMap model) {
		String[] ridArr = StringUtils.split(rids, ",");
		for (int i = 0; i < ridArr.length; i++) {
			mongoService.execUpdate(
					BuilderUtils.prepareUpdate(TimelineExtraVote.class).setCondition(new Expression().eq(TimelineExtraBase.EXTRA_RID, ridArr[i]))
							.setUpdateFirst(true).setInsert4NotFind(false).addData(TimelineExtraBase.EXTRA_SORTNUM, i + 1));
		}
		return result2Json(ResultCode.SUCCESS);
	}
}

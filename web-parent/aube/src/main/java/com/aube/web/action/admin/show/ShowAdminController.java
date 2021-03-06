package com.aube.web.action.admin.show;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aube.constans.MongoData;
import com.aube.json.show.ShowInfo;
import com.aube.mdb.operation.Expression;
import com.aube.support.ResultCode;
import com.aube.util.BeanUtil;
import com.aube.util.DateUtil;
import com.aube.util.WebUtils;
import com.aube.web.action.admin.BaseAdminController;

/**
 * 节目列表 TODO 权限判断
 * 
 */
@Controller
public class ShowAdminController extends BaseAdminController {
	@RequestMapping("/admin/show/list.xhtml")
	public String showlist(ModelMap model) {
		Expression params = new Expression();
		// TODO 根据登录用户拿到节目
		List<ShowInfo> showList = mongoService.getObjectList(ShowInfo.class, params);
		Collections.sort(showList, new PropertyComparator<ShowInfo>(ShowInfo.SORT_KEY_ADDTIME, false, false));
		model.put("showList", showList);
		return "admin/show/showList.vm";
	}

	@RequestMapping("/admin/show/getShowDetail.xhtml")
	public String showDetail(String showid, HttpServletRequest request, ModelMap model) {
		if (StringUtils.isBlank(showid)) {
			showid = MongoData.buildId();
		} else {
			ResultCode<ShowInfo> showCode = getShowInfoById(showid, request);
			if (!showCode.isSuccess()) {
				model.put("errorMsg", showCode.getErrmsg());
			}
			ShowInfo show = showCode.getRetval();
			model.put("info", BeanUtil.getBeanMap(show));
		}
		model.put("showid", showid);
		return "admin/show/showDetail.vm";
	}

	@RequestMapping("/admin/show/removeShow.xhtml")
	@ResponseBody
	public String removeShow(String showid, HttpServletRequest request) {
		ResultCode<ShowInfo> showCode = getShowInfoById(showid, request);
		return result2Json(showCode);
	}

	@RequestMapping("/admin/show/saveShow.xhtml")
	@ResponseBody
	public String saveShow(String showid, HttpServletRequest request) {
		Map<String, String> reqMap = WebUtils.getRequestMap(request);
		ShowInfo info = null;
		if (StringUtils.isNotBlank(showid)) {
			info = mongoService.getObjectById(ShowInfo.class, ShowInfo.SHOW_ID, showid);
		}
		if (info == null) {
			info = new ShowInfo();
			info.set_id(showid);
			info.setAddTime(DateUtil.getCurFullTimestampStr());
		}
		BeanUtil.copyProperties(info, reqMap);
		info.setUpdTime(DateUtil.getCurFullTimestampStr());
		mongoService.saveOrUpdateObject(info, ShowInfo.SHOW_ID);
		return result2Json(ResultCode.SUCCESS);
	}
}

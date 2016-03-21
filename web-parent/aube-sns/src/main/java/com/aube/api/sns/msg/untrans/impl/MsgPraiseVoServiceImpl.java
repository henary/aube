package com.aube.api.sns.msg.untrans.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.support.PropertyComparator;

import com.aube.api.sns.msg.untrans.MsgPraiseVoService;
import com.aube.api.vo.sns.msgpraise.MsgPraiseRespVo;
import com.aube.service.BaseService;
import com.aube.support.ResultCode;

public class MsgPraiseVoServiceImpl extends BaseService implements MsgPraiseVoService {
	// TODO 以后放入redis缓存中
	private Map<String/* vidoid */, Map<String/*msgid*/,Integer/*计数*/>> MSG_PRAISE_COUNT_MAP = new HashMap<String, Map<String, Integer>>();

	@Override
	public void praisemsg(String videoid, String msgid) {
		Map<String, Integer> map = MSG_PRAISE_COUNT_MAP.get(videoid);
		if (MapUtils.isEmpty(MSG_PRAISE_COUNT_MAP.get(videoid))) {
			map.put(msgid, 0);
		}
		map.put(msgid, map.get(msgid) + 1);
		MSG_PRAISE_COUNT_MAP.put(msgid, map);
	}

	@Override
	public ResultCode<List<MsgPraiseRespVo>> getMsgPraiseList(String videoid, Integer from, Integer maxnum) {
		Map<String, Integer> map = MSG_PRAISE_COUNT_MAP.get(videoid);
		if (MapUtils.isEmpty(map)) {
			List<MsgPraiseRespVo> list = new ArrayList<MsgPraiseRespVo>();
			return ResultCode.getSuccessReturn(list);
		}
		List<MsgPraiseRespVo> praiseList = new ArrayList<MsgPraiseRespVo>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			praiseList.add(new MsgPraiseRespVo(entry.getKey(), entry.getValue()));
		}
		Collections.sort(praiseList, new PropertyComparator<MsgPraiseRespVo>("praiseCount", true, false));
		return ResultCode.getSuccessReturn(praiseList);
	}

}

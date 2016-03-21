package com.aube.api.sns.msg.untrans;

import java.util.List;

import com.aube.api.vo.sns.msgpraise.MsgPraiseRespVo;
import com.aube.support.ResultCode;

public interface MsgPraiseVoService {
	void praisemsg(String videoid, String msgid);

	/**
	 * 根据vidoid获取点赞信息的列表
	 * 
	 * @param reqVo
	 * @return
	 */	
	ResultCode<List<MsgPraiseRespVo>> getMsgPraiseList(String videoid, Integer from, Integer maxnum);
}

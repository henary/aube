package com.aube.api.vo.sns.msgpraise;

import com.aube.api.vo.BaseVo;

public class MsgPraiseRespVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1301315275096056083L;
	// 消息ID
	private String msgid;
	// 点赞数
	private Integer praiseCount;
	
	public MsgPraiseRespVo() {
		
	}
	public MsgPraiseRespVo(String msgid, Integer praiseCount) {
		this.msgid = msgid;
		this.praiseCount = praiseCount;
	}
	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}
}

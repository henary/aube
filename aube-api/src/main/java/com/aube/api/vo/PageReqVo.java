package com.aube.api.vo;

public class PageReqVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1238632235384017427L;
	private Integer from;
	private Integer maxnum;

	public Integer getFrom() {
		return from == null ? 0 : from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getMaxnum() {
		return maxnum == null ? 10 : maxnum;
	}

	public void setMaxnum(Integer maxnum) {
		this.maxnum = maxnum;
	}

}

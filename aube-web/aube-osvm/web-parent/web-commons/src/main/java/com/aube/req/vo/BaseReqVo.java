package com.aube.req.vo;

import java.io.Serializable;

/**
 * 页面以流形式提交的参数 
 * 
 *
 */
public class BaseReqVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7532828604262217599L;
	// 排序字段
	private String sort;
	// 升序（asc）or降序（desc）
	private String order;
	// 每页多少条
	private Integer limit;
	// 第几条纪录开始
	private Integer offset;
	// 搜索的字段
	private String search;

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}

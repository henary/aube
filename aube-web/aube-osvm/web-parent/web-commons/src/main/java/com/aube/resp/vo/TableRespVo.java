package com.aube.resp.vo;

import java.util.List;

/**
 * 页面返回表格结果 
 */
public class TableRespVo<T> extends DataRespVo<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 116815093942050920L;
	// 返回的结果集
	private List<T> rows;
	// 记录条数
	private Integer total;

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}

package com.aube.mongo.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aube.monitor.DataReport;
import com.aube.monitor.ReportType;
import com.aube.util.BeanUtil;
import com.aube.util.DateUtil;


public class MongoDataReport implements DataReport {
	private int countThreshold = 50000;
	private int countMin = 120;
	private long timeThreshold = DateUtil.m_minute * 30;
	@Override
	public List<Map<String, String>> getReportData() {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<TableStats> statsList = MongoStats.getTableStats();
		for(TableStats stats: statsList){
			if(stats.total() > countThreshold || System.currentTimeMillis() - stats.getStarttime() > timeThreshold && stats.total()> countMin ){
				Map<String, String> row = BeanUtil.toSimpleStringMap(BeanUtil.getBeanMap(stats));
				row.put("starttime", DateUtil.formatTimestamp(stats.getStarttime()));
				result.add(row);
				stats.reset();
			}
		}
		return result;
	}

	@Override
	public ReportType getReptype() {
		return ReportType.mongo;
	}

	public void setCountThreshold(int countThreshold) {
		this.countThreshold = countThreshold;
	}

	public void setTimeThreshold(long timeThreshold) {
		this.timeThreshold = timeThreshold;
	}

}

package com.aube.monitor;

import java.util.List;
import java.util.Map;

import ReportType.ReportType;

public interface DataReport {
	List<Map<String, String>> getReportData();
    ReportType getReptype();
}

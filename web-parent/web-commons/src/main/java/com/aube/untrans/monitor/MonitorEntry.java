package com.aube.untrans.monitor;

import java.util.Map;

public interface MonitorEntry {
	String KEY_COLUMN_DATATYPE = "datatype";
	String getDatatype();
	byte[] getRowid();
	Map<String, String> getDataMap();
	Map<String, String> getHeadMap();
}

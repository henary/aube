package com.aube.untrans.monitor.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.aube.Config;
import com.aube.untrans.monitor.MonitorEntry;

public class MapMonitorEntry implements MonitorEntry{
	private Map<String, String> rowdata;
	private Map<String, String> headMap;
	private String datatype;
	private byte[] rowid;
	@Override
	public byte[] getRowid() {
		return rowid;
	}
	public MapMonitorEntry(String datatype, Map<String, String> rowdata){
		this.datatype = datatype;
		this.rowdata = new LinkedHashMap<String, String>(rowdata);
		this.rowdata.put(KEY_COLUMN_DATATYPE, datatype);
		this.rowdata.put("systemid", Config.SYSTEMID);
	}
	public MapMonitorEntry(String datatype, byte[] rowid, Map<String, String> rowdata){
		this(datatype, rowdata);
		this.rowid = rowid;
	}
	@Override
	public String getDatatype() {
		return datatype;
	}
	@Override
	public Map<String, String> getDataMap() {
		return rowdata;
	}
	public Map<String, String> getHeadMap() {
		return headMap;
	}
	public void setHeadMap(Map<String, String> headMap) {
		this.headMap = headMap;
	}
}

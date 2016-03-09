package com.aube.mongo.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

public class MongoStats {
	public static String OP_QUERY = "query";
	public static String OP_UPDATE = "update";
	public static String OP_REMOVE = "remove";
	
	public static boolean enableStats = true;
	public static boolean isEnableStats() {
		return enableStats;
	}
	public static void setEnableStats(boolean enableStats) {
		MongoStats.enableStats = enableStats;
	}
	
	private static Map<String/*namespace*/, TableStats> statsMap = new ConcurrentHashMap<String, TableStats>();
	
	public static void addOp(String namespace, String op){
		if(!enableStats) return;
		TableStats stats = statsMap.get(namespace);
		if(stats==null){
			stats = new TableStats(namespace);
			statsMap.put(namespace, stats);
		}
		if(StringUtils.equals(op, OP_QUERY)){
			stats.addQuery();
		}else if(StringUtils.equals(op, OP_UPDATE)){
			stats.addUpdate();
		}else if(StringUtils.equals(op, OP_REMOVE)){
			stats.addRemove();
		}
	}
	public static List<TableStats> getTableStats(){
		return new ArrayList<TableStats>(statsMap.values());
	}
}

package com.aube.web.support;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.support.PropertyComparator;

import com.aube.util.DateUtil;

public class DynamicStats {
	private Map<String, LogCounter> counterMap = new ConcurrentHashMap<String, LogCounter>();
	private AtomicInteger totalProcessing = new AtomicInteger(0);
	private long lastRemoveTime = 0;	//上次删除时间
	private long lastCleanTime = 0;		//上次清理时间
	private boolean init = false;
	private String type;

	public long getLastRemoveTime() {
		return lastRemoveTime;
	}

	public long getLastCleanTime() {
		return lastCleanTime;
	}

	public String getType() {
		return type;
	}

	public DynamicStats(String type){
		this.type = type;
	}
	
	public void setInit(boolean init) {
		this.init = init;
	}

	public boolean isInit() {
		return init;
	}

	/**
	 * 计数器数量
	 * @return
	 */
	public int getCounterSize(){
		return counterMap.size();
	}
	/**
	 * 当前总共处理次数
	 * @return
	 */
	public int getTotalProcessing() {
		return totalProcessing.get();
	}
	/**
	 * 获取正在处理的请求列表
	 * @param waitmill
	 * @return Map(url,avgwait,processing,processed)
	 */
	public List<Map> getProcessingList(int waitmill){
		List<Map> result = new ArrayList<Map>();
		long totalprocessing = 0, maxnum = 0, totalprocessed = 0, totaltime = 0;
		for(LogCounter counter: counterMap.values()){
			int p = counter.processing.get();
			if(p>0){
				Map row = new LinkedHashMap();
				long total = System.currentTimeMillis() * p - counter.processTime.get();
				totaltime += total;
				totalprocessing += p;
				if(total > maxnum){
					maxnum = total;
				}
				long avgwait = total/p;
				if(avgwait > waitmill){
					int processed = counter.processed.get();
					totalprocessed += processed;
					row.put("processed", processed);
					row.put("processing", p);
					row.put("avgwait", avgwait);
					row.put("url", counter.url);
					result.add(row);
				}
			}
		}
		if(result.size()>0){
			Map row = new LinkedHashMap();
			row.put("processed", totalprocessed);
			row.put("processing", totalprocessing);
			row.put("avgwait", totaltime/totalprocessing);
			row.put("url", "total");
			result.add(row);
		}
		return result;
	}
	/**
	 * 注册请求
	 * @param sourceName
	 * @param type
	 * @param regtime
	 */
	public LogCounter register(String sourceName, long regtime){
		LogCounter lc = new LogCounter(sourceName, regtime);
		lc.type = type;
		counterMap.put(sourceName, lc);
		return lc;
	}

	/**
	 * 加入处理队列，完成后afterProcess退出处理队列
	 * @param resourceName
	 * @param curtime
	 * @return
	 */
	public LogCounter beforeProcess(String resourceName, long curtime){
		LogCounter counter = counterMap.get(resourceName);
		if(counter!=null){
			totalProcessing.incrementAndGet();
			counter.processing.incrementAndGet();
			counter.processTime.addAndGet(curtime);
		}
		return counter;
	}
	
	/**
	 * 获取资源正在处理的数量
	 * @param resourceName
	 * @return
	 */
	public int getResourceProcessingCount(String resourceName){
		LogCounter counter = counterMap.get(resourceName);
		if(counter != null){
			return counter.getProcessing().intValue();
		}
		return 0;
	}
	
	public LogCounter beforeProcessAndReg(String resourceName, long curtime){
		LogCounter counter = counterMap.get(resourceName);
		if(counter == null){
			counter = register(resourceName, curtime);
		}
		if(counter!=null){
			totalProcessing.incrementAndGet();
			counter.processing.incrementAndGet();
			counter.processTime.addAndGet(curtime);
		}
		return counter;
	}
	/**
	 * 退出处理队列
	 * @param counter
	 * @param curtime
	 */
	public void afterProcess(LogCounter counter, long lasttime, boolean updateTotaltime){
		if(counter!=null){
			totalProcessing.decrementAndGet();
			counter.processing.decrementAndGet();
			counter.processTime.addAndGet(-lasttime);
			counter.processed.incrementAndGet();
			if(updateTotaltime){
				counter.totaltime.addAndGet(System.currentTimeMillis() - lasttime);
			}
		}
	}
	
	/**
	 * 更新计数
	 * @param uri
	 * @param count
	 * @return
	 */
	public LogCounter incrementCount(String uri){
		LogCounter counter = counterMap.get(uri);
		long cur = System.currentTimeMillis();
		if(counter==null) {
			counter = new LogCounter(uri, cur);
			counterMap.put(uri, counter);
		}
		totalProcessing.incrementAndGet();
		counter.count.incrementAndGet();
		counter.endtime = cur;
		return counter;
	}
	/**
	 * 更新请求处理时间
	 * @param uri
	 * @param time
	 * @param clearmax
	 * @return
	 */
	public Map<String, String> updateProcessTime(String uri, long time, int clearmax) {
		LogCounter counter = counterMap.get(uri);
		if(counter==null) return null;
		counter.count.incrementAndGet();
		counter.totaltime.addAndGet(time);
		if(time > 1000L){
			counter.count2.incrementAndGet();
			counter.count2Time.addAndGet(time);
		}
		Long cur = System.currentTimeMillis();
		if(counter.count.get() >= clearmax || counter.starttime + DateUtil.m_hour < cur){
			Map<String, String> result = getLogMap(counter, cur);
			
			resetCounter(counter, cur);
			return result;
		}
		return null;
	}
	/**
	 * 如果计数达到maxnum，则清除
	 * @param maxnum
	 * @return
	 */
	public Map<String, Integer> getStatsAndClear(int maxnum){
		if(getTotalProcessing() >=maxnum){
			HashMap<String, Integer> result = new HashMap<String, Integer>();
			List<LogCounter> counterList = new ArrayList<LogCounter>(counterMap.values());
			for(LogCounter counter: counterList){
				int value = counter.count.getAndSet(0);
				if(value>0) result.put(counter.getUrl(), value);
			}
			totalProcessing.getAndSet(0);
			lastCleanTime = System.currentTimeMillis();
			return result;
		}
		return null;
	}
	/**
	 * 如果计数达到maxnum，则清除
	 * @param maxnum
	 * @return
	 */
	public List<Map<String, String>> getAllStatsAndClear(int maxnum){
		Long cur = System.currentTimeMillis();
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<LogCounter> counterList = new ArrayList<LogCounter>(counterMap.values());
		for(LogCounter counter: counterList){
			int ct = counter.processed.get();
			if(ct > maxnum || ct > 0 && (cur - counter.getStarttime() > DateUtil.m_minute *30)){
				Map<String, String> row = getLogMap(counter, cur);
				if(cur - counter.getStarttime() > DateUtil.m_minute *11){
					row.put("flag", "long");
				}else{
					row.put("flag", "normal");
				}
				resetCounter(counter, cur);
				result.add(row);
			}
		}
		lastCleanTime = cur;
		return result;
	}
	/**
	 * starttime在timeBefore之前建立的
	 * @param timeBefore
	 * @return List<Map(count,url,starttime,endtime)>
	 */
	public List<Map> removeCountBefore(long timeBefore){
		List<Map> result = new ArrayList<Map>();
		List<LogCounter> counterList = new ArrayList<LogCounter>(counterMap.values());
		for(LogCounter counter: counterList){
			if(counter.getStarttime()<timeBefore){
				counterMap.remove(counter.getUrl());
				Map row = new LinkedHashMap();
				row.put("count", counter.getCount());
				row.put("url", counter.getUrl());
				row.put("starttime", counter.starttime);
				row.put("endtime", counter.endtime);
			}
		}
		lastRemoveTime = System.currentTimeMillis();
		return result;

	}

	/**
	 * 获取计数统计
	 * @return
	 */
	public Map<String, Integer> getStatsMap(){
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		for(Entry<String, LogCounter> entry: counterMap.entrySet()){
			int value = entry.getValue().count.get();
			if(value>0) result.put(entry.getKey(), value);
		}
		return result;
	}
	
	
	/**
	 * 根据处理次数获取url统计
	 * @param mincount
	 * @param reqFields
	 * @return
	 */
	public List<Map> getCountList(int mincount, boolean reqFields){
		List<Map> result = new LinkedList<Map>();
		for(LogCounter counter: counterMap.values()){
			if(counter.count.get()>=mincount){
				result.add(getCounterCopy(counter, reqFields));
			}
		}
		Collections.sort(result, new PropertyComparator("count", false, false));
		return result;
	}
	/**
	 * 获取从未使用过的url
	 * @return
	 */
	public Set<String> getUnusedList() {
		Set<String> result = new TreeSet<String>();
		for(LogCounter counter: counterMap.values()){
			if(counter.count.get()==0){
				result.add(counter.url);
			}
		}
		return result;
	}
	/**
	 * 获取所有注册的资源
	 * @return
	 */
	public Set<String> getAllResources(){
		Set<String> result = new TreeSet<String>(counterMap.keySet());
		return result;
	}
	/**
	 * 获取当前计数的统计信息
	 * @param counter
	 * @return
	 */
	private Map getCounterCopy(LogCounter counter, boolean reqFields){
		Map map = new LinkedHashMap();
		map.put("starttime", new Timestamp(counter.starttime));
		map.put("count", counter.count.get());
		if(reqFields){
			map.put("totaltime", counter.totaltime.get());
			map.put("count2", counter.count2.get());
			map.put("count2Time", counter.count2Time.get());
			int curcount = counter.processing.get();
			map.put("processing", curcount);
			map.put("processed", counter.processed.get());
			if(curcount>0){
				long total = System.currentTimeMillis() * curcount - counter.processTime.get();
				long avgwait = total/curcount;
				map.put("avgwait", avgwait);
			}
		}
		map.put("url", counter.url);
		return map;
	}
	/**
	 * 请求统计，不清理正在处理
	 * @param counter
	 * @param cur
	 * @return
	 */
	private static Map<String, String> getLogMap(LogCounter counter, long cur){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("url", counter.url);
		map.put("starttime", counter.starttime.toString());
		map.put("count", counter.count.toString());
		map.put("totaltime", counter.totaltime.toString());
		map.put("count2", counter.count2.toString());
		map.put("count2Time", counter.count2Time.toString());
		map.put("endtime", ""+cur);
		map.put("processed", counter.processed.toString());
		map.put("processing", counter.processing.toString());
		return map;
	}
	private static void resetCounter(LogCounter counter, long cur){
		counter.starttime = cur;
		counter.count.set(0);
		counter.totaltime.set(0);
		counter.count2.set(0);
		counter.count2Time.set(0);
		counter.processed.set(0);
	}

	
	public static class LogCounter{
		private String type;		//类型：req or job
		private String url;
		private Long starttime;
		private Long endtime;
		private AtomicLong totaltime;		//成功请求时间
		private AtomicInteger count;		//成功次数
		private AtomicInteger count2;		//大于1秒数量
		private AtomicLong count2Time;		//大于1秒时间
		private AtomicInteger processed;	//总共处理过的数量
		private AtomicLong processTime;		//每次请求，processTime += System.currentTimeMillis();
		private AtomicInteger processing;	//当前正在处理数量，请求来时增加，完成后减少
		public String getType() {
			return type;
		}
		public String getUrl() {
			return url;
		}
		public Long getStarttime() {
			return starttime;
		}
		public Long getEndtime() {
			return endtime;
		}
		public Long getTotaltime() {
			return totaltime.get();
		}
		public int getCount() {
			return count.get();
		}
		public int getCount2() {
			return count2.get();
		}
		public int increamentCount2(){
			return count2.incrementAndGet();
		}
		public Long getCount2Time() {
			return count2Time.get();
		}
		public Integer getProcessing() {
			return processing.get();
		}
		private LogCounter(String url, Long starttime){
			this.type= "req";
			this.url = url;
			this.starttime = starttime;
			this.endtime = starttime;
			this.totaltime = new AtomicLong(0L);
			this.count = new AtomicInteger(0);
			this.count2 = new AtomicInteger(0);
			this.count2Time = new AtomicLong(0L);
			this.processing = new AtomicInteger(0);
			this.processed = new AtomicInteger(0);
			this.processTime = new AtomicLong(0L);
		}
		
	}
}

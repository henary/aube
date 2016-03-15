package com.aube.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用来收集Request的参数名称，便于后期处理:规范化参数名称
 */
public class ParamCollectUtils {
	private static boolean collectEnabled = false;	//开放参数名称收集
	private static long enableTime = 0;				//开放到什么时间点
	private static Map<String/*uri*/, Map<String, AtomicInteger/*count*/>/*param names*/> namesMap = new ConcurrentHashMap<>();
	public static void enableCollect(int seconds){
		if(seconds<=0){
			collectEnabled=false;
		}else{
			collectEnabled=true;
			enableTime= System.currentTimeMillis() + seconds*1000;
		}
	}
	public static void addNameIfEnabled(long cur, String uri, Map<String, String> params){
		if(collectEnabled && enableTime > cur){
			Map<String, AtomicInteger> names = namesMap.get(uri);
			if(names==null){
				names = new ConcurrentHashMap<>();
				namesMap.put(uri, names);
			}
			List<String> pnList = new ArrayList<String>(params.keySet());
			for(String key: pnList){
				AtomicInteger count = names.get(key);
				if(count==null){
					count = new AtomicInteger(1);
					names.put(key, count);
				}else{
					count.incrementAndGet();
				}
			}
		}
	}
	/**
	 * 获取统计信息
	 * @return
	 */
	public static List<String> getStats(){
		List<String> uriList = new ArrayList<String>(namesMap.keySet());
		List<String> result = new ArrayList<String>();
		for(String uri: uriList){
			Map<String, AtomicInteger> pnMap = namesMap.get(uri);
			for(Map.Entry<String, AtomicInteger> entry: pnMap.entrySet()){
				result.add(uri + "," + entry.getKey() + "," + entry.getValue());
			}
		}
		return result;
	}
}

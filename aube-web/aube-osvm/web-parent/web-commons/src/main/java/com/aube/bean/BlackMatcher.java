package com.aube.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.aube.constant.AttackConstant;

/**
 * 
 *
 */
public class BlackMatcher {
	private Map<String/*uri or ip*/, Long/*time*/> matcherMap = new HashMap<String, Long>();
	private String ip;
	
	public BlackMatcher(){}
	
	public BlackMatcher(String ip){
		this.ip = ip;
	}
	
	/**
	 * 获取指定uri对应的释放时间
	 * @param uri
	 * @return
	 * @author leo
	 * @addTime 2014年5月20日下午5:37:45
	 */
	public Long get(String uri){
		return matcherMap.get(uri);
	}
	
	/**
	 * 获取默认uri ip对应的释放时间
	 * @return
	 * @author leo
	 * @addTime 2014年5月20日下午5:38:28
	 */
	public Long get(){
		return matcherMap.get(AttackConstant.ACCESS_URL_ALL);
	}

	/**
	 * 给指定uri设定释放时间
	 * @param uri
	 * @param releaseTime
	 * @author leo
	 * @addTime 2014年5月20日下午5:37:21
	 */
	public void put(String uri, Long releaseTime){
		if(StringUtils.isBlank(uri)){
			matcherMap.put(AttackConstant.ACCESS_URL_ALL, releaseTime);
		}else{
			matcherMap.put(uri, releaseTime);
		}
	}
	
	/**
	 * 默认给uri ip设置释放时间
	 * @param releaseTime
	 * @author leo
	 * @addTime 2014年5月20日下午5:36:45
	 */
	public void put(Long releaseTime){
		matcherMap.put(AttackConstant.ACCESS_URL_ALL, releaseTime);
	}
	
	/**
	 * 删除uri
	 * @param uri
	 * @return
	 * @author leo
	 * @addTime 2014年5月20日下午5:37:45
	 */
	public void remove(String uri){
		matcherMap.remove(uri);
	}
	
	/**
	 * 删除默认uri ip
	 * @return
	 * @author leo
	 * @addTime 2014年5月20日下午5:38:28
	 */
	public void remove(){
		matcherMap.remove(AttackConstant.ACCESS_URL_ALL);
	}
	
	public boolean isEmpty(){
		return matcherMap.isEmpty();
	}
	
	public void delOverdue(){
		long currentTime = System.currentTimeMillis();
		Map<String, Long> tmp = new HashMap<String, Long>(matcherMap);
		Iterator<Map.Entry<String, Long>> it = tmp.entrySet().iterator();
		boolean flag = false;
		while (it.hasNext()) {
			Map.Entry<String, Long> entry = it.next();
			if(entry.getValue() < currentTime){
				it.remove();
				flag = true;
			}
		}
		if(flag) matcherMap = tmp;
	}
	
	public Long gainReleaseTime(String uri){
		Long uriReleaseTime = matcherMap.get(uri);
		if(uriReleaseTime!=null){
			return uriReleaseTime;
		}
		//全局
		Long ipReleaseTime = matcherMap.get(AttackConstant.ACCESS_URL_ALL);
		return ipReleaseTime;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Map<String, Long> getMatcherMap() {
		return matcherMap;
	}
}

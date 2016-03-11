package com.aube.web.support;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindingResult;

import com.aube.Config;

public class ViewContextDebugger  {
	private static boolean debugEnabled = false;
	private Set unusedList;
	public static Set<String> ignoreUriList = new HashSet<String>();
	public static List<String> ignoreField;

	public static boolean isDebugEnabled(String uri){
		if(debugEnabled){
			if(!ignoreUriList.isEmpty()){
				for(String key: ignoreUriList){
					if(StringUtils.startsWith(uri, key)){
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public void addProperty(Map model) {
		if(debugEnabled){
			if(unusedList==null) unusedList = new HashSet();
			unusedList.addAll(model.keySet());
		}
	}
	public void remove(String property){
		if(debugEnabled){
			try{
				if(unusedList!=null) {
					unusedList.remove(property);
					//TODO:add missing property
				}
			}catch(Exception e){
			}
		}
	}
	public String getUnUsedProperty(){
		try{
			if(unusedList ==null || unusedList.isEmpty()){
				return null;
			}
			//去除Sprring内置变量
			Iterator pi = unusedList.iterator();
			while(pi.hasNext()){
				String key = "" + pi.next();
				if(key.startsWith(BindingResult.MODEL_KEY_PREFIX)){
					pi.remove();
				}
			}
			if(Config.getPageTools()!=null){
				unusedList.removeAll(Config.getPageTools().keySet());
			}
			unusedList.remove("springMacroRequestContext");
			unusedList.remove(AubeVelocityView.RENDER_XML);
			unusedList.remove(AubeVelocityView.RENDER_JSON);
			unusedList.remove(AubeVelocityView.KEY_HTTP_STATUS);
			unusedList.remove(AubeVelocityView.USE_OTHER_CHARSET);
			unusedList.remove(AubeVelocityView.KEY_IGNORE_TOOLS);
			if(ignoreField!=null){
				unusedList.removeAll(ignoreField);
			}
			//BindingResult.MODEL_KEY_PREFIX
			return StringUtils.join(new TreeSet(unusedList), ",");
		}catch(Exception e){
			return "UnUsedException:" + e.getMessage();
		}
	}

	public Set getUnusedList() {
		return unusedList;
	}

	public void setUnusedList(Set unusedList) {
		this.unusedList = unusedList;
	}

	public static void addIgnoreUri(String ignoreUri) {
		ignoreUriList.add(ignoreUri);
	}

	public static boolean isDebugEnabled() {
		return debugEnabled;
	}

	public static void setDebugEnabled(boolean debugEnabled) {
		ViewContextDebugger.debugEnabled = debugEnabled;
		if(!debugEnabled){
			ignoreUriList = new HashSet<String>();
		}
	}

	public static String getIgnoreUris() {
		return StringUtils.join(ignoreUriList, ",");
	}
}

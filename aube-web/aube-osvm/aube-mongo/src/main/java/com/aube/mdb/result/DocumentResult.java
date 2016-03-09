package com.aube.mdb.result;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.bson.Document;

public class DocumentResult {
	private Document doc=null;
	public DocumentResult(Document doc){
		this.doc=doc;
	}
	
	public String toJson(){
		return this.doc.toJson();
	}
	
	public Map<String,Object> toMap(){
		Map<String,Object> map=new HashMap<>();
		map.putAll(doc);
		return map;
	}
	
	public <T> T toBean(Class<T> pojoClass){
		try {
			T dest=pojoClass.newInstance();
			PropertyUtils.copyProperties(dest, doc);
			return dest;
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException | InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
}

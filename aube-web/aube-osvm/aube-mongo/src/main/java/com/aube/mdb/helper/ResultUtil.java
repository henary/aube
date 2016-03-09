package com.aube.mdb.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.aube.mongo.MongoDataException;
import com.aube.mongo.impl.MongoService3Impl;
import com.aube.util.BeanUtil;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

@SuppressWarnings({"rawtypes","unchecked"})
public class ResultUtil {
	private static final transient AubeLogger dbLogger = LoggerUtils.getLogger(MongoService3Impl.class);
	public static <T> T toBean(Class<T> beanClass, Document doc){
		if(beanClass==null) throw new MongoDataException("please set the mapping bean type");
		Map<String, Class> listProperties = ClassMetaInfo.getWritableListPropertyInfo(beanClass);
		return toBean(beanClass, listProperties, doc);
	}
	public static Map<String,Object> toMap(Document doc){
		HashMap<String,Object> map= new LinkedHashMap<>();
		map.putAll(doc);
		return map;
	}
	public static <T> List<T>  toBeans(Class<T> beanClass, List<Document> result){
		if(beanClass==null) throw new MongoDataException("please set the mapping bean type");
		List<T> list=new ArrayList<>(result.size());
		Map<String, Class> listProperties = ClassMetaInfo.getWritableListPropertyInfo(beanClass);
		for(Document doc:result){
			T b=ResultUtil.toBean(beanClass, listProperties, doc);
			if(b!=null) list.add(b);
		}
		return list;
	}
	public static <T> T toBean(Class<T> beanClass, Map<String, Class> listProperties, Document doc){
		if(beanClass==null) throw new MongoDataException("please set the mapping bean type");
		if(beanClass.equals(Map.class)){
			return (T) toMap(doc);
		}else{
			T dest = (T) BeanUtil.getInstance(beanClass);
			BeanUtil.copyProperties(dest, doc);
			for(Map.Entry<String, Class> entry: listProperties.entrySet()){
				try {
					Object value = doc.get(entry.getKey());
					if(value instanceof List){
						List<Document> docs=(List<Document>) value;
						List list =new ArrayList(0);
						for(Document item:docs){
							if(entry.getValue()==Map.class){
								list.add(toMap(item));
							}else{
								Object bean = BeanUtil.getInstance(entry.getValue());
								BeanUtil.copyProperties(bean, item);
								list.add(bean);
							}
						}
						BeanUtil.set(dest, entry.getKey(), list);
					}
				}catch(Exception e){
					dbLogger.warn(e, 20);
				}
			}
			return dest;
		}
	}
	
}

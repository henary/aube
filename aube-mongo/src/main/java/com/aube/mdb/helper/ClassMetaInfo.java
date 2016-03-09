package com.aube.mdb.helper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.util.ReflectionUtils;

import com.aube.util.BeanUtil;
import com.aube.util.ClassUtil;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

@SuppressWarnings({"rawtypes"})
public class ClassMetaInfo {
	private static ConcurrentMap<Class, Map<String, Class>> writableListPropertyInfo = new ConcurrentHashMap<>();
	private static transient AubeLogger dbLogger = LoggerUtils.getLogger(ClassMetaInfo.class);
	public static Map<String, Class/* 泛型数据 */> getWritableListPropertyInfo(Class clazz) {
		Map<String, Class> result = writableListPropertyInfo.get(clazz);
		if (result == null) {
			result = new HashMap<>();
			List<String> pnList = BeanUtil.getWriteListPropertyNames(clazz);
			if (!pnList.isEmpty()) {// 判断是否有泛型
				for (String pn : pnList) {
					Class genericClass = getListGenericClass(clazz, pn);
					if(genericClass!=null && !BeanUtil.isSimpleProperty(genericClass)){
						//List<String>这种简单类型不算
						result.put(pn, genericClass);
					}
				}
			}
			writableListPropertyInfo.putIfAbsent(clazz, result);
		}
		return result;
	}

	private static Class getListGenericClass(Class clazz, String property) {
		try {
			Field field = ReflectionUtils.findField(clazz, property);
			Type genericFieldType = field.getGenericType();
			if (genericFieldType != null && genericFieldType instanceof ParameterizedType) {
				ParameterizedType aType = (ParameterizedType) genericFieldType;
				Type[] fieldArgTypes = aType.getActualTypeArguments();
				if(fieldArgTypes!=null){
					if(fieldArgTypes.length==1 && fieldArgTypes[0] instanceof Class){
						Class cl =  (Class)fieldArgTypes[0];
						if(ClassUtil.isConcrete(cl)){//非interface、abstract
							return cl;
						}
					}
				}
			}
		} catch (Exception e) {
			dbLogger.warn(e, 10);
		}
		return null;
	}

}

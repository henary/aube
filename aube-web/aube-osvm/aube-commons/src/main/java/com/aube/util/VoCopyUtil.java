package com.aube.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.FastHashMap;

import com.aube.api.vo.BaseVo;
import com.aube.api.vo.VoMap;
import com.aube.constant.ErrorCodeConstant;
import com.aube.dubbo.bytecode.Wrapper;
import com.aube.support.ResultCode;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;
import com.aube.vo.GsonObject;

public class VoCopyUtil {
	private static final transient AubeLogger dbLogger = LoggerUtils.getLogger(VoCopyUtil.class);
	public static Map<String/*srcClass+dstClass*/, List<String>> copyPropsMap = new FastHashMap();
	public static <K, V> VoMap<K, V> toVoMap(Map<K, V> map){
		VoMap result = new VoMap(map.size());
		result.putAll(map);
		return result;
	}
	public static <K, V> List<VoMap<K, V>> toVoMapList(List<Map<K, V>> mapList){
		List<VoMap<K, V>> result = new ArrayList<>(mapList.size());
		for(Map<K, V> map: mapList){
			result.add(toVoMap(map));
		}
		return result;
	}

	public static <S extends BaseVo, T> ResultCode<List<S>> copyListProperties(Class<S> clazz, List<T> itemList) {
		List<S> voItemList = new ArrayList<S>();
		if (CollectionUtils.isEmpty(itemList)){
			return ResultCode.<List<S>>getSuccessReturn(voItemList);
		}
		try {
			for (T item : itemList) {
				if(item==null) continue;
				Class src = item.getClass();
				Wrapper srcWrapper = Wrapper.getWrapper(src);
				Wrapper destWrapper = Wrapper.getWrapper(clazz);
				S itemVo = (S) destWrapper.gainNewInstance();
				List<String> props = getJoinProperties(srcWrapper.getReadPropertyNames(), src, clazz);
				copyInternal(itemVo, item, srcWrapper, destWrapper, props);
				voItemList.add(itemVo);
			}
		}catch(Exception e){
			dbLogger.warn(e, 10);
		}
		return ResultCode.getSuccessReturn(voItemList);
	}

	public static <S extends GsonObject, T> ResultCode<S> copyProperties(Class<S> clazz, T item) {
		if (item == null)
			return ResultCode.getFailure(ErrorCodeConstant.CODE_DATA_ERROR);
		try {
			Wrapper srcWrapper = Wrapper.getWrapper(item.getClass());
			Wrapper destWrapper = Wrapper.getWrapper(clazz);
			S itemVo = (S) destWrapper.gainNewInstance();
			List<String> props = getJoinProperties(srcWrapper.getReadPropertyNames(), item.getClass(), clazz);
			copyInternal(itemVo, item, srcWrapper, destWrapper, props);
			copy2(itemVo, item);
			return ResultCode.getSuccessReturn(itemVo);
		} catch (Exception e) {
			dbLogger.warn(e, 10);
			return ResultCode.getFailure(ErrorCodeConstant.CODE_UNKNOWN_ERROR, e.getMessage());
		}
	}
	private static List<String> getJoinProperties(String[] srcProps, Class src, Class dest){
		String key = src.getCanonicalName() + "2" + dest.getCanonicalName();
		List<String> propList = copyPropsMap.get(key);
		if(propList == null){
			propList = new ArrayList<String>();
			synchronized(src){
				for (String name: srcProps) {
					try{
						PropertyDescriptor descriptor = new PropertyDescriptor(name, dest);
						if(PropertyUtils.getWriteMethod(descriptor)!=null){
							propList.add(name);
						}
					} catch (Exception e) {
						//ignore e.printStackTrace();
					}
				}
				copyPropsMap.put(key, propList);
			}
		}
		return propList;
	}
	/**
	 * 两个Bean之前简单属性copy
	 * 限制：1）dst,src不能是Map，只能是POJO  2）无法级联
	 * @param dst
	 * @param src
	 */
	public static void copy(Object dst, Object src)  {
		Wrapper srcWrapper = Wrapper.getWrapper(src.getClass());
		Wrapper destWrapper = Wrapper.getWrapper(dst.getClass());
		List<String> props = getJoinProperties(srcWrapper.getReadPropertyNames(), src.getClass(), dst.getClass());
		copyInternal(dst, src, srcWrapper, destWrapper, props);
		
	}
	private static <S extends GsonObject, T> void copy2(S dst, T src) throws Exception {
		Wrapper srcWrapper = Wrapper.getWrapper(src.getClass());
		Wrapper destWrapper = Wrapper.getWrapper(dst.getClass());
		List<String> props = getJoinProperties(srcWrapper.getReadPropertyNames(), src.getClass(), dst.getClass());
		copyInternal(dst, src, srcWrapper, destWrapper, props);
	}
	private static void copyInternal(Object dst, Object src, Wrapper srcWrapper, Wrapper destWrapper, List<String> props) {
		for(String name: props){
			try{
				destWrapper.setPropertyValue(dst, name, srcWrapper.getPropertyValue(src, name));
			}catch(Throwable e){
				dbLogger.warn(name, e, 10);
			}
		}
	}
}

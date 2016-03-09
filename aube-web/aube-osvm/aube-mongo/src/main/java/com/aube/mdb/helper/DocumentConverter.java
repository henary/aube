package com.aube.mdb.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;


@SuppressWarnings({"rawtypes","unchecked"})
public class DocumentConverter  implements Converter{
	
	private Class outputDataType=null;
	
	public DocumentConverter(Class outputDataType){
		this.outputDataType=outputDataType;
	}
	
	public Class getOutputDataType() {
		return outputDataType;
	}



	public void setOutputDataType(Class outputDataType) {
		this.outputDataType = outputDataType;
	}



	@Override
	public Object convert(Class type, Object value) {
		Object o;
		try {
			if(outputDataType.isEnum()){
				Method method = outputDataType.getMethod("valueOf",String.class);
				return method.invoke(null,(String)value);
			}
			
			o = outputDataType.newInstance();
			PropertyUtils.copyProperties(o, value);
			return o;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

}

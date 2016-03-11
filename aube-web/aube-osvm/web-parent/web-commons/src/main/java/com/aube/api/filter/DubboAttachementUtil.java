package com.aube.api.filter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.alibaba.dubbo.rpc.RpcContext;
import com.aube.constant.AutherFilterConstant;

public class DubboAttachementUtil {
	private static final ThreadLocal<DubboApiAuth> holder = new ThreadLocal<DubboApiAuth>();
	
	public static DubboApiAuth getDubboApiAuth(){
		return holder.get();
	}
	
	public static void setDubboApiAuth(DubboApiAuth dubboApiAuth){
		 Assert.notNull(dubboApiAuth);
		holder.set(dubboApiAuth);
	}
	
	public static void clearAttachments(){
		holder.remove();
	}
	
	public static Map<String, String> getAttachments(){
		RpcContext context = RpcContext.getContext();
		if(context!=null){
			return new HashMap<String, String>(context.getAttachments());
		}
		return new HashMap<String, String>();
	}
	public static String getTrace(){
		RpcContext context = RpcContext.getContext();
		if(context!=null){
			Map<String,String> params = getAttachments();
			String callip = context.getRemoteHost();
			String systemid = params.get(AutherFilterConstant.SYSTEMID);
			params.put("callip", callip);
			params.put("systemid", systemid);
			return params.toString();
		}
		return null;
	}
}

package com.aube.api.filter;

import org.apache.commons.lang.StringUtils;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.dubbo.rpc.filter.GenericFilter;
import com.aube.Config;
import com.aube.constant.AutherFilterConstant;
import com.aube.support.ResultCode;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;
import com.aube.web.support.ResourceStatsUtil;

/**
 * 客户端Filter，统一包装异常，返回ResultCode
 * 限制条件：Service中所有方法都必须返回ResultCode<?>
 */
public class ResultCodeTransformFilter extends GenericFilter {
	private AubeLogger dbLogger = LoggerUtils.getLogger(getClass(), Config.getServerIp(), Config.SYSTEMID);
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		try{
			//add systemid
			RpcContext context = RpcContext.getContext();
			context.setAttachment(AutherFilterConstant.SYSTEMID, Config.SYSTEMID);
			
			Result result = super.invoke(invoker, invocation);
			if(result.hasException()){
				dbLogger.error(result.getException(), 10);
				ResultCode<?> ret = ResultCode.getFailure(ResultCode.CODE_UNKNOWN_ERROR);
				ret.setException(result.getException());
				String service = StringUtils.substringAfterLast(invoker.getUrl().getPath(), ".");
				//错误统计
				ResourceStatsUtil.getErrorStats().incrementCount("dubbo." + service);
				return new RpcResult(ret);
			}
			return result;
		}catch(Throwable e){
			dbLogger.error(e, 10);
			ResultCode<?> ret = ResultCode.getFailure(ResultCode.CODE_UNKNOWN_ERROR);
			ret.setException(e);
			return new RpcResult(ret);
		}
	}
}

package com.aube.util;

import java.util.HashMap;
import java.util.Map;

import com.aube.json.common.member.MemberInfo;
import com.aube.support.ResultCode;
import com.aube.vo.member.MemberInfoVo;
import com.aube.vo.rongcloud.RongcloudMemberToken;

public class RongcloudUtils {
	
	/**
	 * 融云配置，移到appkey.properties中，放dubbo服务器端
	 */
	private static final String RONGYUN_APPKEY = "uwd1c0sxdbtr1";
	private static final String RONGYUN_APPSECRET = "BU7GLyTM2ntGCJ";
	private static final String RONGYUN_CREATE_GROUP = "https://api.cn.ronghub.com/group/create.json";
	private static final String RONGYUN_GETTOKEN = "https://api.cn.ronghub.com/user/getToken.json";
	
	public static ResultCode<RongcloudMemberToken> getTokenByMember(MemberInfo infoVo, String staticPath) {
		Map<String, String> reqHeader = getReqHeader();
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", infoVo.realId().toString());
		params.put("name", infoVo.getNickname());
		params.put("portraitUri", staticPath + infoVo.getHeadPic());
		HttpResult result = HttpUtils.postUrlAsString(RONGYUN_GETTOKEN, params, reqHeader, "utf-8");
		// {"code":200,"userId":"1","token":"aLoYzFeJGuNrCGVr8+bQXDF0S5Re29je4LGkctcg+3AxWXA1RA8a1Mo1MV0J6OO3MXl0N0b7xy+6hfqPyKI/Uw=="}
		if (result.isSuccess()) {
			RongcloudMemberToken memberToken = JsonUtils.parseObj(result.getResponse(), RongcloudMemberToken.class);
			if (memberToken.isSuccess()) {
				return ResultCode.<RongcloudMemberToken>getSuccessReturn(memberToken);
			} else {
				return ResultCode.<RongcloudMemberToken>getFailure(memberToken.getErrorMessage());
			}
		}
		return ResultCode.<RongcloudMemberToken>getFailure(result.getMsg());
	}
	
	public static ResultCode<Boolean> add2group(String userid, String groupId, String groupName) {
		Map<String, String> reqHeader = getReqHeader();
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userid);
		params.put("groupId", groupId);
		params.put("groupName", groupName);
		HttpResult result = HttpUtils.postUrlAsString(RONGYUN_CREATE_GROUP, params, reqHeader, "utf-8");
		if (result.isSuccess()) {
			RongcloudMemberToken memberToken = JsonUtils.parseObj(result.getResponse(), RongcloudMemberToken.class);
			if (memberToken.isSuccess()) {
				return ResultCode.<Boolean>getSuccessReturn(Boolean.TRUE);
			} else {
				return ResultCode.<Boolean>getFailure(memberToken.getErrorMessage());
			}
		}
		return ResultCode.<Boolean>getFailure(result.getMsg());
	}
	
	
	
	private static Map<String, String> getReqHeader() {
		String nonce = StringUtil.getRandomString(5);
		Long timestamp = System.currentTimeMillis() /1000;
		Map<String, String> reqHeader = new HashMap<String, String>();
		reqHeader.put("App-Key", RONGYUN_APPKEY);
		reqHeader.put("Nonce", nonce);
		reqHeader.put("Timestamp", timestamp + "");
		reqHeader.put("Signature", StringUtil.sha(RONGYUN_APPSECRET + nonce + timestamp, "UTF-8"));
		return reqHeader;
	}
}

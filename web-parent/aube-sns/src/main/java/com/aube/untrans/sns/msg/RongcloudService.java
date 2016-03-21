package com.aube.untrans.sns.msg;

import com.aube.support.ResultCode;
import com.aube.vo.rongcloud.msg.req.RongcloudMsgReqVo;

public interface RongcloudService {

	ResultCode<String> saveRongcloudMsg(RongcloudMsgReqVo reqVo);

}

package com.aube.untrans.sns.msg.impl;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.aube.constans.MongoData;
import com.aube.constants.sns.SnsMessageConstants;
import com.aube.json.msg.MessageByVideoid;
import com.aube.mdb.helper.BuilderUtils;
import com.aube.mdb.operation.Expression;
import com.aube.service.BaseService;
import com.aube.support.ResultCode;
import com.aube.untrans.sns.msg.RongcloudService;
import com.aube.util.DateUtil;
import com.aube.util.JsonUtils;
import com.aube.vo.msg.req.MsgContentDetail;
import com.aube.vo.rongcloud.msg.req.RongcloudMsgReqVo;

/**
 * TODO 消息分表
 *
 */
@Service("rongcloudService")
public class RongcloudServiceImpl extends BaseService implements RongcloudService, InitializingBean {

	private ThreadPoolExecutor executor;
	private static final int threadPoolSize = 2;

	@Override
	public void afterPropertiesSet() throws Exception {
		BlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>();
		executor = new ThreadPoolExecutor(threadPoolSize, threadPoolSize, 0L, TimeUnit.SECONDS, taskQueue);
		executor.allowCoreThreadTimeOut(false);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
	}

	@Override
	public ResultCode<String> saveRongcloudMsg(RongcloudMsgReqVo reqVo) {
		executor.execute(new RongCloudNotifyThread(reqVo));
		return ResultCode.SUCCESS;
	}

	class RongCloudNotifyThread implements Runnable {
		RongcloudMsgReqVo reqVo;

		public RongCloudNotifyThread(RongcloudMsgReqVo reqVo) {
			this.reqVo = reqVo;
		}

		@Override
		public void run() {
			MsgContentDetail detail = JsonUtils.parseObj(reqVo.getContent(), MsgContentDetail.class);
			if (StringUtils.equals(detail.getExtra().getMsgType(), SnsMessageConstants.MESSAGE_TYPE_NORMAL)) {
				MessageByVideoid message = new MessageByVideoid();
				String videoid = reqVo.getToUserId();
				String[] temp = StringUtils.split(reqVo.getToUserId(), "_");
				if (temp != null && temp.length == 2) {
					videoid = temp[0];
				}
				message.set_id(reqVo.getMsgUID());
				message.setMsgId(reqVo.getMsgUID());
				message.setVideoid(videoid);
				message.setContent(detail.getContent());
				message.setMsgType(reqVo.getObjectName());
				message.setChannelType(SnsMessageConstants.RONGCLOUD_CHANNEL_TYPE_2APP_MAP.get(reqVo.getChannelType()));
				message.setSources(SnsMessageConstants.MESSAGE_SOURCE_RONGCLOUD);
				message.setVideoTime(detail.getExtra().getVideoTime());
				message.setHeadImg(detail.getExtra().getHeadImg());
				message.setTimestamp(reqVo.getMsgTimestamp());
				message.setTimestampStr(DateUtil.formatTimestamp(reqVo.getMsgTimestamp()));
				message.setFromUserId(reqVo.getFromUserId());
				message.setToUserId(reqVo.getToUserId());
				message.setNickname(detail.getExtra().getNickname());
				mongoService.saveOrUpdateObject(message, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM);
			} else if (StringUtils.equals(detail.getExtra().getMsgType(), SnsMessageConstants.MESSAGE_TYPE_LIKE)) {
				// addData2Inc

				mongoService.execUpdate(BuilderUtils.prepareUpdate(MessageByVideoid.class)
						.setCondition(new Expression().eq(MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM, detail.getContent())).setUpdateFirst(true)
						.setInsert4NotFind(false).addData2Inc("praiseCount", 1));
			}

		}

	}

}

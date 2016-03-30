package com.aube.constants;

import java.util.EnumSet;

import org.apache.commons.lang.StringUtils;

import com.aube.constant.AubeErrorCodeConstants;
import com.aube.json.video.timeline.TimelineExtraBase;
import com.aube.json.video.timeline.TimelineExtraGoods;
import com.aube.json.video.timeline.TimelineExtraInfo;
import com.aube.json.video.timeline.TimelineExtraMulticamera;
import com.aube.json.video.timeline.TimelineExtraQA;
import com.aube.json.video.timeline.TimelineExtraVS;
import com.aube.json.video.timeline.TimelineExtraVote;
import com.aube.support.ResultCode;

public enum VideoTimelineExtraEnum {
	VS("VS") {

		@Override
		public Class<TimelineExtraVS> getExtraClazz() {
			return TimelineExtraVS.class;
		}

		@Override
		public String getExtraDesc() {
			return "VS";
		}
	},
	QA("QA") {

		@Override
		public Class<TimelineExtraQA> getExtraClazz() {
			return TimelineExtraQA.class;
		}

		@Override
		public String getExtraDesc() {
			return "QA";
		}
	},
	VOTE("VOTE") {
		@Override
		public Class<TimelineExtraVote> getExtraClazz() {
			return TimelineExtraVote.class;
		}

		@Override
		public String getExtraDesc() {
			return "投票";
		}

	},
	INFO("INFO") {
		@Override
		public String toString() {
			return "INFO";
		}

		@Override
		public Class<TimelineExtraInfo> getExtraClazz() {
			return TimelineExtraInfo.class;
		}

		@Override
		public String getExtraDesc() {
			return "信息";
		}

	},
	MULTICAMERA("MULTICAMERA") {
		@Override
		public Class<TimelineExtraMulticamera> getExtraClazz() {
			return TimelineExtraMulticamera.class;
		}

		@Override
		public String getExtraDesc() {
			return "多镜头";
		}

	},
	AD("AD") {
		@Override
		public String getExtraDesc() {
			return "物品";
		}
		@Override
		public Class<TimelineExtraGoods> getExtraClazz() {
			return TimelineExtraGoods.class;
		}
	};
	
	@Override
	public String toString() {
		return tlType;
	}

	public abstract String getExtraDesc();

	public abstract <T extends TimelineExtraBase<T>> Class<T> getExtraClazz();

	public static ResultCode<VideoTimelineExtraEnum> getExtraEnumByTlType(String tlType) {
		EnumSet<VideoTimelineExtraEnum> extraEnumSet = EnumSet.<VideoTimelineExtraEnum> allOf(VideoTimelineExtraEnum.class);
		for (VideoTimelineExtraEnum extraEnum : extraEnumSet) {
			if (StringUtils.equals(extraEnum.toString(), tlType)) {
				return ResultCode.<VideoTimelineExtraEnum> getSuccessReturn(extraEnum);
			}
		}
		return ResultCode.<VideoTimelineExtraEnum> getFailure(AubeErrorCodeConstants.CODE_TL_UNSUPPORT_EXTRA);
	}

	private String tlType;

	VideoTimelineExtraEnum(String tlType) {
		this.tlType = tlType;
	}

	public String getTlType() {
		return tlType;
	}

	public void setTlType(String tlType) {
		this.tlType = tlType;
	}

}

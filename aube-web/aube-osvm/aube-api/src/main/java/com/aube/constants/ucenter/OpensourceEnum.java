package com.aube.constants.ucenter;

public enum OpensourceEnum {
	// 测试
	TEST {
		@Override
		public String toString() {
			return "test";
		}
	},
	// 微信
	WEIXIN {
		@Override
		public String toString() {
			return "weixin";
		}
	};

	@Override
	public abstract String toString();
}

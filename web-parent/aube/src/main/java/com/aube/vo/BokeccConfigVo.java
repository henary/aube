package com.aube.vo;

/**
 * CC 视频的配置信息
 *
 */
public class BokeccConfigVo implements GsonObject<BokeccConfigVo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6722488508239235550L;
	// APPKEY
	private String appkey;
	// APP密钥
	private String appSecret;

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}

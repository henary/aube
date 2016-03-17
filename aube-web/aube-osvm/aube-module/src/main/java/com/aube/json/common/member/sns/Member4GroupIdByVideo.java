package com.aube.json.common.member.sns;

import com.aube.mongo.support.MGObject;

public class Member4GroupIdByVideo extends MGObject<Member4GroupIdByVideo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7244409398439170919L;
	private String groupid;

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

}

package com.aube.mdb;

import com.mongodb.Tag;
import com.mongodb.TagSet;

public class HostInfo {
	private String tagName = null;
	private String tagValue = null;
	private String ip = null;
	private Integer port = null;
	
	public HostInfo(){}
	
	public  HostInfo(String tag,String value){
		this.tagName=tag;
		this.tagValue=value;
	}
	
	public  HostInfo(String ip,Integer port,String tag,String value){
		this.ip=ip;
		this.port=port;
		this.tagName=tag;
		this.tagValue=value;
	}
	
	public  HostInfo(String ipport){
		String[] h=ipport.split(":");
		this.ip=h[0].trim();
		this.port=Integer.parseInt(h[1].trim());
	}

	public String getTagName() {
		return tagName;
	}

	public String getTagValue() {
		return tagValue;
	}

	public String getIp() {
		return ip;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public TagSet getTagSet(){
		return new TagSet(new Tag(tagName, tagValue));
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	
	
}

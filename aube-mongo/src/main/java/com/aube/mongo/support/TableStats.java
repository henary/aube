package com.aube.mongo.support;

public class TableStats {
	private String namespace;		//collect
	private long starttime;			//ͳ��ʱ��
	private int queryCount;			//��ѯ����
	private int updateCount;		//���´���
	private int removeCount;		//ɾ������
	
	public TableStats(){
	}
	public TableStats(String namespace){
		this.starttime = System.currentTimeMillis();
		this.namespace = namespace;
	}
	
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public long getStarttime() {
		return starttime;
	}
	public void setStarttime(long starttime) {
		this.starttime = starttime;
	}
	public int getQueryCount() {
		return queryCount;
	}
	public void setQueryCount(int queryCount) {
		this.queryCount = queryCount;
	}
	public int getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}
	
	public void addQuery(){
		queryCount ++;
	}
	public void addUpdate(){
		updateCount ++;
	}
	public void addRemove(){
		removeCount ++;
	}
	public int total(){
		return queryCount+updateCount + removeCount;
	}
	public void reset(){
		this.starttime = System.currentTimeMillis();
		this.queryCount = 0;
		this.updateCount = 0;
		this.removeCount = 0;
	}
}

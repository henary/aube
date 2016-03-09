package com.aube.mdb.result;

import com.mongodb.client.result.DeleteResult;

public class DeleteRes {
	private DeleteResult result;
	private boolean d1;
	public DeleteRes(DeleteResult result,boolean done){
		this.result=result;
		this.d1=done;
	}
	
	/**
	 * �Ƿ�ɹ�
	 * @return
	 */
	public boolean success(){
		if(d1) return this.result.getDeletedCount()==1;
		return this.result.getDeletedCount()>0&&this.result.wasAcknowledged();
	}
	
	/**
	 * ɾ��������
	 * @return
	 */
	public int deleteCount(){
		return (int) this.result.getDeletedCount();
	}
}

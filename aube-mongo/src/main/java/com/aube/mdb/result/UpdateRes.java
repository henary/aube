package com.aube.mdb.result;

import com.mongodb.client.result.UpdateResult;

public class UpdateRes {
	private UpdateResult result=null;
	
	public UpdateRes(UpdateResult result){
		this.result=result;
	}
	
	 
	 public long updateCount(){
		 return result.getModifiedCount();
	 }
	 
	 public long matchCount(){
		 return result.getMatchedCount();
	 }
	
}

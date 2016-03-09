package com.aube.mdb.operation;

import org.bson.Document;

public class UpdateOperation {
	
	protected Document doc=new Document();
	
	public <Num extends Number> UpdateOperation inc(String field,Num value){
		return this;
	}
	
	public <Num extends Number> UpdateOperation mul(String field,Num value){
		return this;
	}
	
	/**
	 * @param field
	 * @param newFieldName
	 * @return
	 */
	public UpdateOperation renameFiled(String field,String newFieldName){
		return this;
	}
	
	/**
	 * ɾ��ĳ���ֶ�
	 * @param field
	 * @return
	 */
	public UpdateOperation delField(String... field){
		return this;
	}
	
	/**
	 * @param field
	 * @param value
	 * @return
	 */
	public <TItem> UpdateOperation setOnInsert(String field,TItem value){
		return this;
	}
	
	public <TItem> UpdateOperation data(String field,TItem value){
		return this;
	}
	
	
	/**
	 * @param field
	 * @param value
	 * @return
	 */
	public <TItem> UpdateOperation min(String field,TItem value){
		return this;
	}
	
	/**
	 * @param field
	 * @param value
	 * @return
	 */
	public <TItem> UpdateOperation max(String field,TItem value){
		return this;
	}
	
	/**
	 * @return
	 */
	public UpdateOperation currentDate(String Field){
		return this;
	}
	
	
}

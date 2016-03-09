package com.aube.mdb.builder;

import org.apache.commons.lang.StringUtils;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.aube.mdb.operation.Expression;
import com.aube.mdb.operation.Projection;
import com.aube.mongo.MongoDataException;
import com.aube.util.Util4Script;

public class FindBuilder<T> implements Util4Script {
	private Class<T> mapping;
	private Bson condition=null;
	private Bson projection=null;
	private Document sort=null;
	private int from=0;
	private Integer size=null;
	//private Bson ids=null;
	private String collectionName=null;
	
	public FindBuilder(String collectionName){
		this.collectionName=collectionName;
		if(this.collectionName==null) throw new MongoDataException("please check the param,the collectionName is null");
	}
	public FindBuilder(Class<T> mappingClass){
		this.collectionName=mappingClass.getCanonicalName();
		if(this.collectionName==null) throw new MongoDataException("please check the param,don't get mappingClass's canonicalName");
		this.mapping=mappingClass;
	}
	public FindBuilder(String collectionName, Class<T> mappingClass){
		this.collectionName = collectionName;
		if(this.collectionName==null) throw new MongoDataException("please check the param,don't get mappingClass's canonicalName");
		this.mapping=mappingClass;
		
	}
	
	/**
	 * ������������������Ḳ����֮ǰ�����õ�������
	 * @param condition
	 * @return
	 */
	public FindBuilder<T> setCondition(Expression queryExpre){
		this.condition=queryExpre.toBson();
		return this;
	}
	
	public FindBuilder<T> addSort(String field,boolean asc){
		if(StringUtils.isNotBlank(field)){
			if(sort==null){
				sort=new Document(field,asc?1:-1);
			}else{
				sort.put(field, asc?1:-1);
			}
		}
		return this;
	}
	
	public FindBuilder<T> addSort(String[] fields,boolean[] ascs){
		if(fields.length!=ascs.length) throw new MongoDataException(" params length is not equal");
		for(int i=0;i<fields.length;i++){
			addSort(fields[i],ascs[i]);
		}
		return this;
	}
	
	public FindBuilder<T> addSortByMetaTextSocre(){
		if(sort==null){
			sort=new Document("score",new Document("$meta","textScore"));
		}else{
			sort.put("score",new Document("$meta","textScore"));
		}
		return this;
	}
	
	public FindBuilder<T> setFrom(int from){
		if(from<0) from=0;
		this.from=from;
		return this;
	}
	
	public FindBuilder<T> setSize(int limit){
		this.size=limit;
		return this;
	}
	public FindBuilder<T> setIncludeFields(String... fieldNames){
		this.projection = new Projection().addIncludeField(fieldNames).toBson();
		return this;
	}
	
	public FindBuilder<T> setProjection(Projection projection){
		this.projection=projection.toBson();
		return this;
	}
	
	public Class<T> getMapping() {
		return mapping;
	}
	public Bson getQueryCondition(){
		if(condition!=null) return condition;
		return new BsonDocument();
	}
	public Bson getProjection() {
		return projection;
	}
	public Document getSort() {
		return sort;
	}
	public int getFrom() {
		return from;
	}
	public Integer getSize() {
		return size;
	}
	
	public String getCollectionName() {
		return collectionName;
	}

}

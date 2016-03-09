package com.aube.mdb.builder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.bson.Document;

import com.aube.mdb.result.DocumentResult;
import com.mongodb.client.MongoDatabase;

public class MultiDatabaseBuilder {
	private Collection<MongoDatabase> mdbs=null;
	
	public MultiDatabaseBuilder(Collection<MongoDatabase> db){
		this.mdbs=db;
	}
	
	public Map<String,Set<String>> listCollections(){
		Map<String,Set<String>> names=new LinkedHashMap<>();
		for(MongoDatabase database:mdbs){
			Iterator<String> ite= database.listCollectionNames().iterator();
			Set<String> r=new HashSet<>();
			while(ite.hasNext()){
				r.add(ite.next());
			}
			names.put(database.getName(),r);
		}
		return names;
	}
	
	/**
	 * ��ȡcollection��״̬
	 * @return
	 */
	public Map<String,DocumentResult> getCollectionStat(String collectionName){
		assert(collectionName!=null);
		Map<String,DocumentResult> stats=new LinkedHashMap<>();
		for(MongoDatabase database:mdbs){
			Document doc=database.runCommand(new Document("collStats",collectionName));
			stats.put(database.getName(), new DocumentResult(doc));
		}
		return stats;
	}
	
	/**
	 * �ж�һ��collection�Ƿ����
	 * @return
	 */
	public Map<String,Boolean> exists(String collectionName){
		Map<String,Boolean> exits=new LinkedHashMap<>();
		for(MongoDatabase database:mdbs){
			exits.put(database.getName(), database.getCollection(collectionName)!=null);
		}
		return exits;
	}
	
	/**
	 * ��ȡ���ݿ������б��״̬
	 * @return 
	 */
	public Map<String,Map<String,DocumentResult>>  listCollectionStat(){
		Map<String,Map<String,DocumentResult>> allStats=new LinkedHashMap<>();
		for(MongoDatabase database:mdbs){
			Iterator<String> ite=database.listCollectionNames().iterator();
			Map<String,DocumentResult> map=new HashMap<String,DocumentResult>();
			while(ite.hasNext()){
				String cn=ite.next();
				if(cn.startsWith("system.")) continue;//����ϵͳ��
				Document doc=database.runCommand(new Document("collStats",cn));
				map.put(cn, new DocumentResult(doc));
			}
			allStats.put(database.getName(), map);
		}
		return allStats;
	}
	
	/**
	 * ɾ��collection
	 */
	public void drop(String collectionName){
		assert(collectionName!=null);
		for(MongoDatabase database:mdbs){
			database.getCollection(collectionName).drop();
		}
	}
	
	public  Map<String,DocumentResult> listDBStats(){
		Map<String,DocumentResult> dbStats=new LinkedHashMap<>();
		for(MongoDatabase database:mdbs){
			Document doc=database.runCommand(new Document("dbStats",1));
			dbStats.put(database.getName(), new DocumentResult(doc));
		}
		return dbStats;
	}
}

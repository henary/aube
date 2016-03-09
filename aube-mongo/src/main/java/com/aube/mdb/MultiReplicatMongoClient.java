package com.aube.mdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ����
 * @createDate 2015��9��14��
 */
public class MultiReplicatMongoClient {
	private final List<NameGWMongoClient> replicates=new ArrayList<NameGWMongoClient>();
	
	/**
	 * @param replicatName  mongo��Ⱥ������
	 * @param mongoClient   mongo��Ⱥ�����ӳ�
	 */
	public MultiReplicatMongoClient(List<NameGWMongoClient> multiMongo){
		replicates.addAll(multiMongo);
	}
	
	/**
	 * @param replicatName
	 * @return
	 */
	public NameGWMongoClient useReplicat(String replicatName){
		for(int i=0;i<replicates.size();i++){
			if(replicates.get(i).replicateName().equals(replicatName)){
				return replicates.get(i);
			}
		}
		return null;
	}
	
	public List<NameGWMongoClient> listReplicate(){
		return Collections.unmodifiableList(replicates);
	}
}

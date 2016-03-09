package com.aube.mdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 */
public class MultiReplicatMongoClient {
	private final List<NameGWMongoClient> replicates=new ArrayList<NameGWMongoClient>();
	
	/**
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

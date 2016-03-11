package com.aube.mdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 */
public class MultiReplicatMongoClient {
	private final List<NameAubeMongoClient> replicates=new ArrayList<NameAubeMongoClient>();
	
	/**
	 */
	public MultiReplicatMongoClient(List<NameAubeMongoClient> multiMongo){
		replicates.addAll(multiMongo);
	}
	
	/**
	 * @param replicatName
	 * @return
	 */
	public NameAubeMongoClient useReplicat(String replicatName){
		for(int i=0;i<replicates.size();i++){
			if(replicates.get(i).replicateName().equals(replicatName)){
				return replicates.get(i);
			}
		}
		return null;
	}
	
	public List<NameAubeMongoClient> listReplicate(){
		return Collections.unmodifiableList(replicates);
	}
}

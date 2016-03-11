package com.aube.mdb;

import java.util.List;

import com.aube.mdb.config.AubeMongoClientOptions;

public class NameAubeMongoClient extends AubeMongoClient{
	private String replicateName=null;
	
	public NameAubeMongoClient(List<HostInfo> hosts, List<AubeMongoAccount> accounts,
			AubeMongoClientOptions options, String mechanism,String replicateName) {
		super(options, mechanism,hosts, accounts);
		this.replicateName=replicateName;
	}
	
	
	public NameAubeMongoClient(List<HostInfo> hosts, List<AubeMongoAccount> accounts, AubeMongoClientOptions options,String replicateName){
		super(options,"default",hosts,accounts);
		this.replicateName=replicateName;
	}
	
	public NameAubeMongoClient(List<HostInfo> hosts,List<AubeMongoAccount> accounts,String replicateName){
		super(AubeMongoClientOptions.getDefaultInstance(),"default",hosts, accounts);
		this.replicateName=replicateName;
	}
	
	public String replicateName(){
		return this.replicateName;
	}
	
	
}

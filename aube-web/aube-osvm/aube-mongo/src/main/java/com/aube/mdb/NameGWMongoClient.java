package com.aube.mdb;

import java.util.List;

import com.aube.mdb.config.GwMongoClientOptions;

public class NameGWMongoClient extends GWMongoClient{
	private String replicateName=null;
	
	public NameGWMongoClient(List<HostInfo> hosts, List<GWMongoAccount> accounts,
			GwMongoClientOptions options, String mechanism,String replicateName) {
		super(options, mechanism,hosts, accounts);
		this.replicateName=replicateName;
	}
	
	
	public NameGWMongoClient(List<HostInfo> hosts, List<GWMongoAccount> accounts, GwMongoClientOptions options,String replicateName){
		super(options,"default",hosts,accounts);
		this.replicateName=replicateName;
	}
	
	public NameGWMongoClient(List<HostInfo> hosts,List<GWMongoAccount> accounts,String replicateName){
		super(GwMongoClientOptions.getDefaultInstance(),"default",hosts, accounts);
		this.replicateName=replicateName;
	}
	
	public String replicateName(){
		return this.replicateName;
	}
	
	
}

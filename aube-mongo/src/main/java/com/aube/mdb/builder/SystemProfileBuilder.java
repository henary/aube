package com.aube.mdb.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.aube.mdb.HostInfo;
import com.aube.mdb.helper.RowDataProcess;
import com.aube.mdb.operation.Expression;
import com.aube.mdb.result.ScanRes;
import com.mongodb.ReadPreference;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


@SuppressWarnings({"rawtypes","unchecked"})
public class SystemProfileBuilder {
	private Collection<MongoCollection<Document>> profileCollections=null;
	private Collection<HostInfo> hosts=null;
	private Bson condition=null;
	
	public SystemProfileBuilder(Collection<MongoDatabase> databases,Collection<HostInfo> hosts){
		this.profileCollections=new ArrayList<>(databases.size());
		for(MongoDatabase db:databases){
			this.profileCollections.add(db.getCollection("system.profile"));
		}
		this.hosts=hosts;
	}
	
	public SystemProfileBuilder setMinCreateTime(Date ts){
		Expression expre=new Expression().gt("ts",ts);
		if(this.condition!=null)
			expre.bson(this.condition);
		this.condition=expre.toBson();
		return this;
	}
	public SystemProfileBuilder setType(String op){
		Expression expre=new Expression().gt("op",op);
		if(this.condition!=null)
			expre.bson(this.condition);
		this.condition=expre.toBson();
		return this;
	}
	
	public  void find(final RowDataProcess<Map> rowdataProcess){
		for(MongoCollection profileCollection:profileCollections){
			for(final HostInfo host:hosts){
				MongoCollection<Document> collection=profileCollection.withReadPreference(ReadPreference.nearest(host.getTagSet()));
						FindIterable<Document> fi=(condition==null?collection.find():collection.find(condition));
				
				ScanRes<Map> sr=new ScanRes<>(fi.iterator(),Map.class);
				sr.setDataProcessApp(new RowDataProcess<Map>() {
					@Override
					public void process(Map rowData) {
						rowData.put("hostIP", host.getIp());
						rowData.put("hostTag",host.getTagValue());
						rowdataProcess.process(rowData);
					}
				}, Map.class);
				sr.startSyncProcess();
			}
		}
	}

}

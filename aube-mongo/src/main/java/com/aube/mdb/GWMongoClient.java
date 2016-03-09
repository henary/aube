package com.aube.mdb;

import java.util.ArrayList;
import java.util.List;

import com.aube.mdb.builder.MongoAdmin;
import com.aube.mdb.config.GwMongoClientOptions;
import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;

public class GWMongoClient {
	private final MongoReplicateInfo _mongo=new MongoReplicateInfo();
	private MongoDatabase currentDB=null;

	public GWMongoClient(List<String> hosts, List<GWMongoAccount> accounts, GwMongoClientOptions options,String mechanism){
		_mongo.initMongoClient(hosts,accounts,options,mechanism);
		this.currentDB=_mongo.getDefaultMongoDatabase();
	}
	
	public GWMongoClient(GwMongoClientOptions options,String mechanism,List<HostInfo> hosts, List<GWMongoAccount> accounts){
		_mongo.initMongoClient(options,mechanism,hosts,accounts);
		this.currentDB=_mongo.getDefaultMongoDatabase();
	}
	
	/**ʹ��Ĭ�ϵ���֤���õ�¼
	 * @param hosts
	 * @param accounts ��ʽΪ�� db:user:pwd
	 * @param options
	 */
	public GWMongoClient(List<String> hosts, List<String> accounts, GwMongoClientOptions options){
		List<GWMongoAccount> accountList=new ArrayList<>(accounts.size());
		for(String account:accounts){
			accountList.add(new GWMongoAccount(account,":"));
		}
		_mongo.initMongoClient(hosts,accountList,options,null);//��̬��Ϣ��
		this.currentDB=_mongo.getDefaultMongoDatabase();
	}
	
	/**
	 * ʹ��Ĭ�����ã�Ĭ����֤��ʽ��ʼ��mongoClient
	 * @param hosts
	 * @param accounts
	 */
	public GWMongoClient(List<String> hosts,List<String> accounts){
		this(hosts,accounts,null);
	}
	
	/**
	 * ����GWMongoClientʵ���ĵ�ǰ���������ݿ�
	 * @return
	 */
	public MongoDatabase prepareDatabase(){
		return this.currentDB;
	}
	
	public MongoAdmin admin(){
		return new MongoAdmin(_mongo.getMongoDatabases(),_mongo.getHosts());
	}
	
	/**
	 * ����GWMongoClientʵ����ָ�������ݿ�
	 * @param databaseName
	 * @return ָ�������ݿ���������ڣ��򷵻�null�� ֻ֧���������ļ���ע������ݿ⡣
	 */
	public MongoDatabase prepareDatabase(String databaseName){
		return _mongo.getMongoDatabase(databaseName);
	}

	@Deprecated
	public DB prepareDB(){
		return _mongo.getMongoDB(this.currentDB.getName());
	}


}

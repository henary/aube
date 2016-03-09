package com.aube.mdb.config;

import java.util.HashMap;
import java.util.Map;

import javax.net.SocketFactory;

import org.bson.BsonType;
import org.bson.codecs.BsonTypeClassMap;
import org.bson.codecs.DocumentCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.DBDecoderFactory;
import com.mongodb.DBEncoderFactory;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;

public class GwMongoClientOptions{
	
	private  String description;            
    private  ReadPreference readPreference=ReadPreference.secondaryPreferred();
    private  WriteConcern writeConcern;
    private  CodecRegistry codecRegistry;

    private  Integer minConnectionsPerHost;
    private  Integer maxConnectionsPerHost;
    private  Integer threadsAllowedToBlockForConnectionMultiplier;
    private  Integer serverSelectionTimeout;
    private  Integer maxWaitTime;
    private  Integer maxConnectionIdleTime;
    private  Integer maxConnectionLifeTime;

    private  Integer connectTimeout;
    private  Integer socketTimeout;
    private  Boolean socketKeepAlive;
    private  Boolean sslEnabled;
    private  Boolean sslInvalidHostNameAllowed;
    private  Boolean alwaysUseMBeans;
    private  Integer heartbeatFrequency;
    private  Integer minHeartbeatFrequency;
    private  Integer heartbeatConnectTimeout;
    private  Integer heartbeatSocketTimeout;
    private  Integer localThreshold;

    private  String requiredReplicaSetName;
    private  DBDecoderFactory dbDecoderFactory;
    private  DBEncoderFactory dbEncoderFactory;
    private  SocketFactory socketFactory;
    private  Boolean cursorFinalizerEnabled;
//    private  ConnectionPoolSettings connectionPoolSettings;
//    private  SocketSettings socketSettings;
//    private  ServerSettings serverSettings;
//    private  SocketSettings heartbeatSocketSettings;
//    private  SslSettings sslSettings;
    
    public static GwMongoClientOptions getDefaultInstance(){
    	return new GwMongoClientOptions();
    }
    
    public MongoClientOptions toMongoClientOptions(){
    	MongoClientOptions defaultOptions=MongoClientOptions.builder().build();
    	
    	MongoClientOptions.Builder builder=MongoClientOptions.builder();
    	if(description!=null)
    		builder.description(description);
    	if(readPreference!=null)
    		builder.readPreference(readPreference);
    	if(writeConcern!=null) 
    		builder.writeConcern(writeConcern);
    	
    	if(codecRegistry!=null){
    		builder.codecRegistry(this.codecRegistry);
    	}else{
    		CodecRegistry defaultCodecRegistry =defaultOptions.getCodecRegistry();
    	
    		Map<BsonType, Class<?>> replacementsForDefaults=new HashMap<>();
    		replacementsForDefaults.put(BsonType.TIMESTAMP,java.sql.Timestamp.class);
    		BsonTypeClassMap bsonTypeClassMap=new BsonTypeClassMap(replacementsForDefaults);
    		
    		DocumentCodecProvider documentCodecProvider = new DocumentCodecProvider(bsonTypeClassMap,new BsonTimestampTrans());
    		codecRegistry = CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(new SqlTimestampCodec()),
    				                                       CodecRegistries.fromProviders(documentCodecProvider),
    				                                       defaultCodecRegistry);
	    	builder.codecRegistry(codecRegistry);
    	}
    	
    	if(minConnectionsPerHost!=null) 
    		builder.minConnectionsPerHost(minConnectionsPerHost);
    	if(maxConnectionsPerHost!=null) 
    		builder.connectionsPerHost(maxConnectionsPerHost);
    	if(threadsAllowedToBlockForConnectionMultiplier!=null)
    		builder.threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);
    	if(serverSelectionTimeout!=null) 
    		builder.serverSelectionTimeout(serverSelectionTimeout);
    	
    	if(maxWaitTime!=null) 
    		builder.maxWaitTime(maxWaitTime);
    	if(maxConnectionIdleTime!=null) 
    		builder.maxConnectionIdleTime(maxConnectionIdleTime);
    	if(maxConnectionLifeTime!=null) 
    		builder.maxConnectionLifeTime(maxConnectionLifeTime);
    	if(connectTimeout!=null) 
    		builder.connectTimeout(connectTimeout);
    	if(socketTimeout!=null) 
    		builder.socketTimeout(socketTimeout);
    	if(socketKeepAlive!=null)
    		builder.socketKeepAlive(socketKeepAlive);
    	if(sslEnabled!=null) builder.sslEnabled(sslEnabled);
    	if(sslInvalidHostNameAllowed!=null)
    		builder.sslInvalidHostNameAllowed(sslInvalidHostNameAllowed);
    	if(alwaysUseMBeans!=null)
    		builder.alwaysUseMBeans(alwaysUseMBeans);
    	if(heartbeatFrequency!=null) 
    		builder.heartbeatFrequency(heartbeatFrequency);
    	if(minHeartbeatFrequency!=null)
    		builder.minHeartbeatFrequency(minHeartbeatFrequency);
    	if(heartbeatConnectTimeout!=null)
    		builder.heartbeatConnectTimeout(heartbeatConnectTimeout);
    	if(heartbeatSocketTimeout!=null)
    		builder.heartbeatSocketTimeout(heartbeatSocketTimeout);
    	if(localThreshold!=null) 
    		builder.localThreshold(localThreshold);
    	if(requiredReplicaSetName!=null) 
    		builder.requiredReplicaSetName(requiredReplicaSetName);
    	if(dbDecoderFactory!=null){
    		builder.dbDecoderFactory(dbDecoderFactory);
    	}else{
    		
    	}
    	
    	if(dbEncoderFactory!=null){
    		builder.dbEncoderFactory(dbEncoderFactory);
    	}else{
    		
    	}
    	if(socketFactory!=null)
    		builder.socketFactory(socketFactory);
    	if(cursorFinalizerEnabled!=null) 
    		builder.cursorFinalizerEnabled(cursorFinalizerEnabled);
//    	if(connectionPoolSettings!=null) builder.connectionPoolSettings(connectionPoolSettings);
//    	if(socketSettings!=null) builder.socketSettings(socketSettings);
//    	if(serverSettings!=null) builder.serverSettings(serverSettings);
//    	if(heartbeatSocketSettings!=null) builder.heartbeatSocketSettings(heartbeatSocketSettings);
//    	if(sslSettings!=null) options.sslSettings(sslSettings);
    	
    	return builder.build();
    	
    }
    
	
	public void setDescription(String description) {
		this.description = description;
	}
	public void setReadPreference(ReadPreference readPreference) {
		this.readPreference = readPreference;
	}
	public void setWriteConcern(WriteConcern writeConcern) {
		this.writeConcern = writeConcern;
	}
	public void setCodecRegistry(CodecRegistry codecRegistry) {
		this.codecRegistry = codecRegistry;
	}
	public void setMinConnectionsPerHost(Integer minConnectionsPerHost) {
		this.minConnectionsPerHost = minConnectionsPerHost;
	}
	public void setMaxConnectionsPerHost(Integer maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}
	public void setThreadsAllowedToBlockForConnectionMultiplier(
			Integer threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}
	public void setServerSelectionTimeout(Integer serverSelectionTimeout) {
		this.serverSelectionTimeout = serverSelectionTimeout;
	}
	public void setMaxWaitTime(Integer maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}
	public void setMaxConnectionIdleTime(Integer maxConnectionIdleTime) {
		this.maxConnectionIdleTime = maxConnectionIdleTime;
	}
	public void setMaxConnectionLifeTime(Integer maxConnectionLifeTime) {
		this.maxConnectionLifeTime = maxConnectionLifeTime;
	}
	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	public void setSocketKeepAlive(Boolean socketKeepAlive) {
		this.socketKeepAlive = socketKeepAlive;
	}
	public void setSslEnabled(Boolean sslEnabled) {
		this.sslEnabled = sslEnabled;
	}
	public void setSslInvalidHostNameAllowed(Boolean sslInvalidHostNameAllowed) {
		this.sslInvalidHostNameAllowed = sslInvalidHostNameAllowed;
	}
	public void setAlwaysUseMBeans(Boolean alwaysUseMBeans) {
		this.alwaysUseMBeans = alwaysUseMBeans;
	}
	public void setHeartbeatFrequency(Integer heartbeatFrequency) {
		this.heartbeatFrequency = heartbeatFrequency;
	}
	public void setMinHeartbeatFrequency(Integer minHeartbeatFrequency) {
		this.minHeartbeatFrequency = minHeartbeatFrequency;
	}
	public void setHeartbeatConnectTimeout(Integer heartbeatConnectTimeout) {
		this.heartbeatConnectTimeout = heartbeatConnectTimeout;
	}
	public void setHeartbeatSocketTimeout(Integer heartbeatSocketTimeout) {
		this.heartbeatSocketTimeout = heartbeatSocketTimeout;
	}
	public void setLocalThreshold(Integer localThreshold) {
		this.localThreshold = localThreshold;
	}
	public void setRequiredReplicaSetName(String requiredReplicaSetName) {
		this.requiredReplicaSetName = requiredReplicaSetName;
	}
	public void setDbDecoderFactory(DBDecoderFactory dbDecoderFactory) {
		this.dbDecoderFactory = dbDecoderFactory;
	}
	public void setDbEncoderFactory(DBEncoderFactory dbEncoderFactory) {
		this.dbEncoderFactory = dbEncoderFactory;
	}
	public void setSocketFactory(SocketFactory socketFactory) {
		this.socketFactory = socketFactory;
	}
	public void setCursorFinalizerEnabled(Boolean cursorFinalizerEnabled) {
		this.cursorFinalizerEnabled = cursorFinalizerEnabled;
	}
//	public void setConnectionPoolSettings(
//			ConnectionPoolSettings connectionPoolSettings) {
//		this.connectionPoolSettings = connectionPoolSettings;
//	}
//	public void setSocketSettings(SocketSettings socketSettings) {
//		this.socketSettings = socketSettings;
//	}
//	public void setServerSettings(ServerSettings serverSettings) {
//		this.serverSettings = serverSettings;
//	}
//	public void setHeartbeatSocketSettings(SocketSettings heartbeatSocketSettings) {
//		this.heartbeatSocketSettings = heartbeatSocketSettings;
//	}
//	public void setSslSettings(SslSettings sslSettings) {
//		this.sslSettings = sslSettings;
//	}
	
	
	
}

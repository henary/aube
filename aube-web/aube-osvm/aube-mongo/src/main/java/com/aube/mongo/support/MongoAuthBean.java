package com.aube.mongo.support;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.mongodb.MongoCredential;

public class MongoAuthBean extends AbstractFactoryBean<MongoCredential> {
	public String userName;
	public String password;
	public String database;
	public MongoAuthBean(){
	}
	@Override
	public Class<?> getObjectType() {
		return MongoCredential.class;
	}

	@Override
	protected MongoCredential createInstance() throws Exception {
		return MongoCredential.createCredential(userName, database, password.toCharArray());
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setDatabase(String database) {
		this.database = database;
	}

}

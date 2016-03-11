package com.aube.mdb;

public class AubeMongoAccount {
	private String database = null;
	private String user = null;
	private String password = null;
	
	public AubeMongoAccount(String database,String userName,String pwd){
		this.database=database;
		this.user=userName;
		this.password=pwd;
	}
	
	/**
	 * �ù��췽ʽ��Ҫ��Ϊ���ַ�����ʽ����ʹ�á�
	 * 
	 * @param accountInfo  ���볤��Ϊ3����������Ϣ����Ϊ database,username,password.
	 */
	protected AubeMongoAccount(String[] accountInfo){
		assert(accountInfo==null||accountInfo.length!=3);
		this.database=accountInfo[0];
		this.user=accountInfo[1];
		this.password=accountInfo[2];
	}
	
	/**
	 * �ù��췽ʽ��Ҫ��Ϊ���ַ�����ʽ����ʹ�á�
	 * 
	 * @param accountInfo  ���볤��Ϊ3����������Ϣ����Ϊ database,username,password.
	 */
	protected AubeMongoAccount(String accountInfo,String separator){
		this(accountInfo.split(separator));
	}
	
	public AubeMongoAccount(){}

	public String getDatabase() {
		return database;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

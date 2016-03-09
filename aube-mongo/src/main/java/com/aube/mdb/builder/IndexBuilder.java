package com.aube.mdb.builder;

import org.bson.Document;

import com.mongodb.client.model.IndexOptions;

public class IndexBuilder {
	
	private IndexOptions indexOptions=new IndexOptions();
	private String collectionName=null;
	private Document index=null;
	
	public IndexBuilder(String collectionName){
		this.collectionName=collectionName;
		this.index = new Document();
	}
	
	public IndexBuilder setIndexName(String indexName){
		indexOptions.name(indexName);
		return this;
	}
	
	public IndexBuilder addIndexField(String... fields){
		for(String field:fields){
			index.append(field, 1);
		}
		return this;
	}
	public IndexBuilder addIndexField(String field,boolean asc){
		index.append(field,asc?1:-1);
		return this;
	}
	
	public IndexBuilder setUnique(boolean unique){
		indexOptions.unique(unique);
		return this;
	}
	
	/**
	 * �Ƿ��̨����
	 * @param br
	 * @return
	 */
	public IndexBuilder setBackground(boolean br){
		indexOptions.background(br);
		return this;
	}

	public IndexOptions getIndexOptions() {
		return indexOptions;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public Document getIndex() {
		return index;
	}
}

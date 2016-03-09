package com.aube.mdb.operation;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Projections;

public class Projection {
	protected final List<Bson> returnResult=new ArrayList<Bson>();
	
    public  Projection addIncludeField(final String... fieldNames) {
    	returnResult.add(Projections.include(fieldNames));
        return this;
    }
   
    public  Projection addIncludeField(final List<String> fieldNames) {
    	returnResult.add(Projections.include(fieldNames));
        return this;
    }

    public  Projection addExcludeField(final String... fieldNames) {
    	returnResult.add(Projections.exclude(fieldNames));
        return this;
    }

    
    public  Projection addExcludeField(final List<String> fieldNames) {
    	returnResult.add(Projections.exclude(fieldNames));
        return this;
    }
    
    public Projection addFields(Map<String,Integer> fields){
    	if(fields==null||fields.isEmpty()) return this;
    	List<String> exclude=new ArrayList<>();
    	List<String> include=new ArrayList<>();
    	for(Map.Entry<String, Integer> entry:fields.entrySet()){
    		if(entry.getValue()<0) 
    			exclude.add(entry.getKey());
    		else{
    			if(entry.getValue()>0)
    				include.add(entry.getKey());
    		}
    	}
    	if(!exclude.isEmpty()) addExcludeField(exclude);
    	if(!include.isEmpty()) addIncludeField(include);
    	return this;
    }
   
    public  Projection addExcludeId() {
    	returnResult.add(Projections.excludeId());
        return this;
    }

   
    public  Projection addPosition4Array(final String fieldName) {
    	returnResult.add(Projections.elemMatch(fieldName));
        return this;
    }

   
    /**
     * ָ��array����ЩԪ�ر����ء�
     * @param fieldName
     * @param condition
     * @return
     */
    public  Projection addElemMatch4Array(final String fieldName, final Expression condition) {
        returnResult.add(Projections.elemMatch(fieldName, condition.toBson()));
        return this;
    }

   
    /**
     * �����ı�����
     * @param fieldName
     * @return
     */
    public  Projection addMetaTextScore(final String fieldName) {
    	returnResult.add(Projections.metaTextScore(fieldName));
        return this;
    }

   
    /**
     * 
     * @param fieldName
     * @param limit
     * @return
     */
    public  Projection addSlice4Array(final String fieldName, final int limit) {
    	returnResult.add(Projections.slice(fieldName, limit));
        return this;
    }

    
    public  Projection addSlice4Array(final String fieldName, final int from, final int limit) {
    	returnResult.add(Projections.slice(fieldName,from,limit));
        return this;
    }
    
    
    public Bson toBson(){
    	return Projections.fields(returnResult);
    }
    
//    public  Projection fields(final Bson... projections) {
//        return fields(asList(projections));
//    }

    
//    public static Bson fields(final List<Bson> projections) {
//        notNull("sorts", projections);
//        return new Bson() {
//            @Override
//            public <TDocument> BsonDocument toBsonDocument(final Class<TDocument> documentClass, final CodecRegistry codecRegistry) {
//                BsonDocument combinedDocument = new BsonDocument();
//                for (Bson sort : projections) {
//                    BsonDocument sortDocument = sort.toBsonDocument(documentClass, codecRegistry);
//                    for (String key : sortDocument.keySet()) {
//                        combinedDocument.remove(key);
//                        combinedDocument.append(key, sortDocument.get(key));
//                    }
//                }
//                return combinedDocument;
//            }
//        };
//    }

//    private static Bson combine(final List<String> fieldNames, final BsonValue value) {
//        BsonDocument document = new BsonDocument();
//        for (String fieldName : fieldNames) {
//            document.remove(fieldName);
//            document.append(fieldName, value);
//        }
//        return document;
//    }
//	
	
}

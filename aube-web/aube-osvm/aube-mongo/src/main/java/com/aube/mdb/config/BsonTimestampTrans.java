package com.aube.mdb.config;

import org.bson.BsonTimestamp;
import org.bson.Transformer;

public class BsonTimestampTrans implements Transformer {

	@Override
	public Object transform(Object objectToTransform) {
		if(objectToTransform instanceof BsonTimestamp){
			BsonTimestamp bt=(BsonTimestamp)objectToTransform;
			return new java.sql.Timestamp(bt.getTime()*1000L+bt.getInc());
		}else{
			return objectToTransform;
		}
	}

}

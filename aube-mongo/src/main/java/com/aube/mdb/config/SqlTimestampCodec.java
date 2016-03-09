package com.aube.mdb.config;

import java.sql.Timestamp;

import org.bson.BsonReader;
import org.bson.BsonTimestamp;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class SqlTimestampCodec implements Codec<java.sql.Timestamp>{

	@Override
	public void encode(BsonWriter writer, Timestamp value,
			EncoderContext encoderContext) {
		long m=value.getTime();
		writer.writeTimestamp(new BsonTimestamp((int)(m/1000L), (int)(m%1000L)));
	}

	@Override
	public Class<Timestamp> getEncoderClass() {
		return java.sql.Timestamp.class;
	}

	@Override
	public Timestamp decode(BsonReader reader, DecoderContext decoderContext) {
		BsonTimestamp bt=reader.readTimestamp();
		return new java.sql.Timestamp(bt.getTime()*1000L+bt.getInc());
	}

}

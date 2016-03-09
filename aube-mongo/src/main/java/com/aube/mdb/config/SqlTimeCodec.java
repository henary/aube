package com.aube.mdb.config;

import java.sql.Time;

import org.bson.BsonReader;
import org.bson.BsonTimestamp;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class SqlTimeCodec implements  Codec<java.sql.Time> {
	@Override
	public void encode(BsonWriter writer, Time value,
			EncoderContext encoderContext) {
		long m=value.getTime();
		writer.writeTimestamp(new BsonTimestamp((int)(m/1000L), (int)(m%1000L)));
		
	}

	@Override
	public Class<Time> getEncoderClass() {
		return java.sql.Time.class;
	}

	@Override
	public Time decode(BsonReader reader, DecoderContext decoderContext) {
		BsonTimestamp bt=reader.readTimestamp();
		return new java.sql.Time(bt.getTime()*1000L+bt.getInc());
	}
}

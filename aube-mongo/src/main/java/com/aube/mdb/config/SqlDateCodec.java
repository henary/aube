package com.aube.mdb.config;

import java.sql.Date;

import org.bson.BsonReader;
import org.bson.BsonTimestamp;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class SqlDateCodec implements Codec<java.sql.Date> {

	@Override
	public void encode(BsonWriter writer, Date value,
			EncoderContext encoderContext) {
		long m=value.getTime();
		writer.writeTimestamp(new BsonTimestamp((int)(m/1000L), (int)(m%1000L)));
	}

	@Override
	public Class<Date> getEncoderClass() {
		return java.sql.Date.class;
	}

	@Override
	public Date decode(BsonReader reader, DecoderContext decoderContext) {
		BsonTimestamp bt=reader.readTimestamp();
		return new java.sql.Date(bt.getTime()*1000L+bt.getInc());
	}
}

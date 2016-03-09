package com.aube.mdb.config;

import java.util.HashMap;
import java.util.Map;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;

@SuppressWarnings({"rawtypes","unchecked"})
public class GwCodecRegistry implements CodecRegistry{
	
	private CodecRegistry sysCodecRegistry=null;
	private Map<Class,Codec> gwCodec=new HashMap<>();
	
	public GwCodecRegistry(CodecRegistry codecRegistry){
		this.sysCodecRegistry=codecRegistry;
		registryCodec(new SqlTimestampCodec());
		registryCodec(new SqlTimeCodec());
		registryCodec(new SqlDateCodec());
	}
	
	@Override
	public <T> Codec<T> get(Class<T> clazz) {
		Codec<T> codec=gwCodec.get(clazz);
		System.out.println(clazz.getName()+"=="+(codec==null));
		
		return codec==null?this.sysCodecRegistry.get(clazz):codec;
	}
	
	public <T> void registryCodec(Codec<T> codec){
		gwCodec.put(codec.getClass(), codec);
	}
}

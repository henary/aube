package com.aube.util;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;

public class GoogleCache<K, V> implements Cache<K, V>, Serializable{

	private static final long serialVersionUID = -231460062536441961L;
	private long starttime = System.currentTimeMillis();
	private Cache<K, V> cache;
	private CacheStats startCacheStats;
	
	public GoogleCache(long maxinumSize){
		this.cache = CacheBuilder.newBuilder().maximumSize(maxinumSize).recordStats().build();
		this.startCacheStats = cache.stats();
	}
	/**
	 * @param maxinumSize
	 * @param duration
	 * @param unit
	 */
	public GoogleCache(long maxinumSize, long duration, TimeUnit unit){
		this.cache = CacheBuilder.newBuilder().maximumSize(maxinumSize).expireAfterWrite(duration, unit).recordStats().build();
		this.startCacheStats = cache.stats();
	}
	
	@Override
	public V getIfPresent(Object key) {
		return cache.getIfPresent(key);
	}

	@Override
	public V get(K key, Callable<? extends V> valueLoader)  throws ExecutionException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
		return cache.getAllPresent(keys);
	}

	@Override
	public void put(K key, V value) {
		cache.put(key, value);
		
	}

	@Override
	public void putAll(Map<? extends K,? extends V> m) {
		cache.putAll(m);
	}

	@Override
	public void invalidate(Object key) {
		cache.invalidate(key);
	}

	@Override
	public void invalidateAll(Iterable<?> keys) {
		cache.invalidateAll(keys);
	}

	@Override
	public void invalidateAll() {
		cache.invalidateAll();
	}

	@Override
	public long size() {
		return cache.size();
	}

	@Override
	public CacheStats stats() {
		return cache.stats();
	}
	
	@Override
	public ConcurrentMap<K, V> asMap() {
		return cache.asMap();
	}

	@Override
	public void cleanUp() {
		cache.cleanUp();
	}
	
	public long getStarttime() {
		return starttime;
	}
	
	public CacheStats getStartCacheStats(){
		return startCacheStats;
	}
	
	public void resetCacheStats(CacheStats cacheStats) {
		startCacheStats = cacheStats;
		starttime = System.currentTimeMillis();
	}
}

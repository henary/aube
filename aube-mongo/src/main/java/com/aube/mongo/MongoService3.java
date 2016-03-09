package com.aube.mongo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aube.mdb.builder.DeleteBuilder;
import com.aube.mdb.builder.DistinctBuilder;
import com.aube.mdb.builder.FindBuilder;
import com.aube.mdb.builder.InsertBuilder;
import com.aube.mdb.builder.UpdateBuilder;
import com.aube.mdb.operation.Expression;
import com.aube.mdb.result.DeleteRes;
import com.aube.mdb.result.FindRes;
import com.aube.mdb.result.UpdateRes;
import com.aube.mongo.support.MGObject;
import com.aube.mongo.support.MongoRowCallback;
import com.aube.support.ResultCode;

@SuppressWarnings({ "rawtypes"})
public interface MongoService3 {
	<T extends MGObject> T getObjectById(Class<T> clazz, String idName, Serializable id);

	<T extends MGObject> List<T> getObjectList(Class<T> clazz);

	<T extends MGObject> List<T> getObjectList(Class<T> clazz, Expression params, String orderField, boolean asc,
			int from, int maxnum);

	<T extends MGObject> List<T> getObjectList(Class<T> clazz, String orderField, boolean asc);

	<T extends MGObject> List<T> getObjectList(Class<T> clazz, Expression params);

	<T extends MGObject> List<T> getObjectListByNs(String namespace, Class<T> clazz, Expression params);

	<T extends MGObject> List<T> getObjectList(Class<T> clazz, String orderField, boolean asc, int from, int maxnum);

	<T extends MGObject> List<T> getObjectListByNs(String namespace, Class<T> clazz, Expression params,
			String orderField, boolean asc, int from, int maxnum);

	<T extends MGObject> List<T> getObjectList(Class<T> clazz, Expression params, String[] orderField, boolean[] asc,
			int from, int maxnum);

	<T extends MGObject> List<T> getObjectList(String namespace, Class<T> clazz, Expression params, String[] orderField,
			boolean[] asc, int from, int maxnum);

	List<Map> find(String namespace);

	List<Map> find(String namespace, int from, int maxnum);

	List<Map> find(String namespace, Expression params);

	List<Map> find(String namespace, Expression params, String orderField, boolean asc);

	List<Map> find(String namespace, Expression params, int from, int maxnum);

	List<Map> find(String namespace, Expression params, String orderField, boolean asc, int from, int maxnum);

	List<Map> find(String namespace, Expression params, Map<String, Integer> fields, String orderField, boolean asc,
			int from, int maxnum);

	List<Map> find(String namespace, Expression params, Map<String, Integer> fields, String[] orderField, boolean[] asc,
			int from, int maxnum);

	List<Map> find(String namespace, Expression params, String[] orderField, boolean[] asc, int from, int maxnum);

	Map findOne(String namespace, Expression params);

	Map findById(String namespace, String idName, Serializable idValue);

	<T extends MGObject> int getObjectCount(Class<T> clazz);

	<T extends MGObject> int getObjectCount(Class<T> clazz, Expression params);

	int getCount(String namespace);

	int getCount(String namespace, Expression params);

	<T> List<T> getDistinctPropertyList(String namespace, Expression params, String propertyname, Class<T> clazz);

	// ~~~~~~~~~~~~~~~~~~~~~~modify method ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	void addMapList(List<Map> mapList, String idName, String namespace);

	<T extends MGObject> void saveOrUpdateObject(T bean, String idName);

	<T extends MGObject> void addObject(T o, String idName);

	<T extends MGObject> boolean removeObject(T o, String idName);

	<T extends MGObject> boolean removeObjectById(Class<T> clazz, String idName, Serializable id);

	boolean removeObjectById(String namespace, String idName, Serializable id);

	<T extends MGObject> int removeObjectList(Collection<T> entityList, String idName);

	<T extends MGObject> int removeObjectList(Class<T> clazz, String idName, List<? extends Serializable> idList);

	<T extends MGObject> int removeObjectList(Class<T> clazz, Expression params);

	int removeObjectList(String namespace, Expression params);

	void saveOrUpdateMap(Map map, String idName, String namespace);

	void addMap(Map map, String idName, String namespace);

	List<Map<String, String>> getAllTables();

	<T extends MGObject> void saveOrUpdateObjectList(List<T> beanList, String idName);

	<T extends MGObject> void addObjectList(List<T> beanList, String idName);

	ResultCode<Integer> processData(String namespace, Expression params, MongoRowCallback callback,
			boolean exitOnError);

	// ~~~~~~~~~~~~~~~~~~~mongo manage~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	void enableStats(boolean enableStats);

	Set<String> getCollections();

	Map getCollectionStat(String collectionName);

	List<Map> getIndexesByNamespace(String namespace);

	void createIndex(String namespace, String fields);

	void dropIndex(String namespace, String indexname);

	int copyCollection(String fromCollection, String toCollection);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	<T> FindRes<T> execQuery(FindBuilder<T> fb);

	UpdateRes execUpdate(UpdateBuilder ub);

	void execInsert(InsertBuilder ib);

	DeleteRes execDelete(DeleteBuilder del);

	<T> ResultCode<Integer> process(FindBuilder<T> fb, MongoRowCallback callback, boolean exitOnError);

	<T> List<T> execDistinct(DistinctBuilder dis, Class<T> fieldType);
}

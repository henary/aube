package com.aube.mongo.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;

import com.aube.mdb.GWMongoClient;
import com.aube.mdb.builder.DeleteBuilder;
import com.aube.mdb.builder.DistinctBuilder;
import com.aube.mdb.builder.FindBuilder;
import com.aube.mdb.builder.IndexBuilder;
import com.aube.mdb.builder.InsertBuilder;
import com.aube.mdb.builder.UpdateBuilder;
import com.aube.mdb.helper.BuilderUtils;
import com.aube.mdb.operation.Expression;
import com.aube.mdb.operation.Projection;
import com.aube.mdb.result.DeleteRes;
import com.aube.mdb.result.FindRes;
import com.aube.mdb.result.UpdateRes;
import com.aube.mongo.MongoDataException;
import com.aube.mongo.MongoService3;
import com.aube.mongo.support.MGObject;
import com.aube.mongo.support.MongoRowCallback;
import com.aube.mongo.support.MongoStats;
import com.aube.support.ResultCode;
import com.aube.util.BeanUtil;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;


@SuppressWarnings({"rawtypes","unchecked"})
public class MongoService3Impl implements MongoService3, InitializingBean {
	private MongoDatabase currentDB = null;
	private static final transient AubeLogger dbLogger = LoggerUtils.getLogger(MongoService3Impl.class);
	
	public MongoService3Impl(GWMongoClient mongoClient){
		this.currentDB = mongoClient.prepareDatabase();
	}
	
	public MongoService3Impl(GWMongoClient mongoClient, String databaseName){
		this.currentDB = mongoClient.prepareDatabase(databaseName);
	}
	
	@Override
	public void enableStats(boolean enable) {
		MongoStats.setEnableStats(enable);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (currentDB == null) {
			throw new IllegalStateException("currentDB must be set!");
		}
	}

	@Override
	public <T extends MGObject> T getObjectById(Class<T> clazz, String idName, Serializable id) {
		return  execQuery(BuilderUtils.prepareFind(clazz)
				.setCondition(new Expression().eq(idName, id)).setSize(1))
				.toBean();
	}

	@Override
	public <T extends MGObject> List<T> getObjectList(Class<T> clazz) {
		return execQuery(BuilderUtils.prepareFind(clazz)).toBeans();
	}

	@Override
	public <T extends MGObject> List<T> getObjectList(Class<T> clazz, String orderField, boolean asc) {
		return execQuery(BuilderUtils.prepareFind(clazz)
				.addSort(orderField,asc)).toBeans();
	}

	@Override
	public <T extends MGObject> List<T> getObjectList(Class<T> clazz,
			String orderField, boolean asc, int from, int maxnum) {
		return execQuery(BuilderUtils.prepareFind(clazz)
				.addSort(orderField, asc).setFrom(from).setSize(maxnum)).toBeans();
	}

	@Override
	public <T extends MGObject> void saveOrUpdateObject(T bean, String idName) {
		Map beanMap = getObjectMap(bean);
		Object id = beanMap.get(idName);
		if(id==null){
			throw new MongoDataException("mongodb: id can't be null!");
		}
		execUpdate(BuilderUtils.prepareUpdate(bean.getClass())
			.setCondition(new Expression().eq(idName, id))// ���null����Ҫ��bean��ȡֵ
			.setUpdateFirst(true).setInsert4NotFind(true).addData(beanMap));
	}

	@Override
	public <T extends MGObject> void addObject(T bean, String idName) {
		if (bean == null){
			return;
		}
		Map beanMap = getObjectMap(bean);
		Serializable id = (Serializable) beanMap.get(idName);
		if (id == null) {
			throw new MongoDataException("id can not be null!");
		}
		
		execInsert(BuilderUtils.prepareInsert(bean.getClass().getCanonicalName())
		           .addData4Bean(beanMap));
	}
	
	private Map getObjectMap(MGObject bean) {
		Map beanMap = BeanUtil.getBeanMap(bean);
		if(beanMap.containsKey("_id")){//�����û�ID
			Object _id = BeanUtil.get(bean, "_id");
			if(_id==null){
				beanMap.remove("_id");
			}else if(_id instanceof ObjectId){
				beanMap.put("_id", _id);
			}
		}else	if(bean.realId()!=null){
			beanMap.put("_id", bean.realId());
		}
		return beanMap;
	}

	@Override
	public <T extends MGObject> boolean removeObject(T o, String idName) {
		Object id = BeanUtil.get(o, idName);
		if (id != null) {
			return execDelete(BuilderUtils.prepareDelete(o.getClass())
					.setCondition(new Expression().eq(idName, id)).setDeleteOne(true)).success();
		}
		return false;
	}

	@Override
	public <T extends MGObject> boolean removeObjectById(Class<T> clazz, String idName, Serializable id) {
		return execDelete(BuilderUtils.prepareDelete(clazz).setCondition(new Expression().eq(idName,id))
				.setDeleteOne(true)).success();
	}

	@Override
	public boolean removeObjectById(String namespace, String idName, Serializable id) {
		return execDelete(BuilderUtils.prepareDelete(namespace).setCondition(new Expression().eq(idName,id))
				.setDeleteOne(true)).success();
	}

	@Override
	public <T extends MGObject> int removeObjectList(Collection<T> entityList, String idName) {
		if(entityList.isEmpty()) return 0;
		Class<? extends MGObject> clazz = entityList.iterator().next().getClass();
		List idList = BeanUtil.getBeanPropertyList(entityList, idName, true);
		return removeObjectList(clazz, idName, idList);
	}

	@Override
	public <T extends MGObject> int removeObjectList(Class<T> clazz, String idName, List<? extends Serializable> idList) {
		return execDelete(BuilderUtils.prepareDelete(clazz).setCondition(new Expression().in(idName,idList)))
						.deleteCount();
	}

	@Override
	public <T extends MGObject> int removeObjectList(Class<T> clazz, Expression params) {
		return execDelete(BuilderUtils.prepareDelete(clazz).setCondition(params)).deleteCount();
	}

	@Override
	public int removeObjectList(String namespace, Expression params) {
		return execDelete(BuilderUtils.prepareDelete(namespace).setCondition(params)).deleteCount();
	}

	@Override
	public void saveOrUpdateMap(Map map, String idName, String namespace) {
		Object id = map.get(idName);
		if (id == null){
			throw new MongoDataException("mongodb: id can't be null!");
		}
		
		execUpdate(BuilderUtils.prepareUpdate(namespace).setCondition(new Expression().eq(idName,id))
				.setUpdateFirst(true).setInsert4NotFind(true).setData(map));
	}

	@Override
	public void addMap(Map map, String idName, String namespace) {
		execInsert(BuilderUtils.prepareInsert(namespace).addData4Bean(map));
	}

	@Override
	public List<Map> find(String namespace) {
		return execQuery(BuilderUtils.prepareFind(namespace)).toMapList();
	}

	@Override
	public List<Map<String, String>> getAllTables() {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Set<String> names = getCollections();
		for (String namespace : names) {
			if (StringUtils.startsWith(namespace, "system."))
				continue;
			Map data = new HashMap();
			data.put(namespace, "" + getCount(namespace));
			result.add(data);
		}
		return result;
	}

	@Override
	public List<Map> find(String namespace, Expression params, String orderField, boolean asc) {
		return execQuery(BuilderUtils.prepareFind(namespace).setCondition(params)
				          .addSort(orderField, asc)).toMapList();
	}


	@Override
	public List<Map> find(String namespace, Expression params, String[] orderField, boolean[] asc, int from, int maxnum) {
		return execQuery(BuilderUtils.prepareFind(namespace).setCondition(params)
		          .addSort(orderField, asc).setFrom(from).setSize(maxnum)).toMapList();
	}

	@Override
	public List<Map> find(String namespace, int from, int maxnum) {
		return execQuery(BuilderUtils.prepareFind(namespace)
				.setFrom(from).setSize(maxnum))
				.toMapList();
	}

	@Override
	public List<Map> find(String namespace, Expression params, int from, int maxnum) {
		return execQuery(BuilderUtils.prepareFind(namespace)
				.setCondition(params).addSort("_id", false).setFrom(from).setSize(maxnum))
				.toMapList();
	}

	@Override
	public Map findOne(String namespace, Expression params) {
		return execQuery(BuilderUtils.prepareFind(namespace)
				.setCondition(params).setSize(1))
				.toMap();
	}
	@Override
	public Map findById(String namespace, String idName, Serializable idValue) {
		return execQuery(BuilderUtils.prepareFind(namespace)
				.setCondition(new Expression().eq(idName, idValue)).setSize(1))
				.toMap();
	}

	@Override
	public <T extends MGObject> void saveOrUpdateObjectList(List<T> beanList, String idName) {
		if (beanList.isEmpty()) return;
		
		Class clazz = beanList.get(0).getClass();
		String namespace = clazz.getCanonicalName();
		for (T bean : beanList) {
			Map beanMap = getObjectMap(bean);
			Object id = beanMap.get(idName);
			if (id == null){
				throw new MongoDataException("mongodb: id can't be null!");
			}
			execUpdate(BuilderUtils.prepareUpdate(namespace)
			           .setCondition(new Expression().eq(idName,id))
			           .setUpdateFirst(true)
			           .setInsert4NotFind(true)
			           .setData(beanMap));
		}
	}

	@Override
	public <T extends MGObject> void addObjectList(List<T> beanList, String idName) {
		if(beanList==null||beanList.isEmpty()) return ;
		Class<T> c=(Class<T>) beanList.get(0).getClass();
		InsertBuilder<Map> insert = BuilderUtils.prepareInsert(c.getCanonicalName());
		for(T bean: beanList){
			Map beanMap = getObjectMap(bean);
			Serializable id = (Serializable) beanMap.get(idName);
			if (id == null) {
				throw new MongoDataException("id can not be null!");
			}
			insert.addData4Bean(beanMap);
		}
		execInsert(insert);
	}

	@Override
	public void addMapList(List<Map> mapList, String idName, String namespace) {
		execInsert(BuilderUtils.prepareInsert(namespace).setData4Bean(mapList));
	}

	@Override
	public int getCount(String namespace) {
		return count(namespace);
	}
	
	@Override
	public List<Map> find(String namespace, Expression params) {
		return execQuery(BuilderUtils.prepareFind(namespace)
				.setCondition(params))
				.toMapList();
	}

	@Override
	public List<Map> find(String namespace, Expression params,
			String orderField, boolean asc, int from, int maxnum) {
		return execQuery(BuilderUtils.prepareFind(namespace)
				.setCondition(params)
				.addSort(orderField, asc).setFrom(from).setSize(maxnum))
				.toMapList();
	}

	@Override
	public List<Map> find(String namespace, Expression params,
			Map<String, Integer> fields, String orderField, boolean asc, int from,
			int maxnum) {
		return execQuery(BuilderUtils.prepareFind(namespace).setCondition(params)
				          .setProjection(new Projection().addFields(fields))
				          .addSort(orderField, asc)
				          .setFrom(from).setSize(maxnum)).toMapList();
	}

	@Override
	public List<Map> find(String namespace, Expression params, Map<String, Integer> fields, 
			String[] orderField, boolean[] asc, int from, int maxnum) {
		return execQuery(BuilderUtils.prepareFind(namespace)
							.setCondition(params)
				         .setProjection(new Projection().addFields(fields))
				         .addSort(orderField, asc).setFrom(from).setSize(maxnum)
				).toMapList();
	}

	@Override
	public <T extends MGObject> List<T> getObjectList(Class<T> clazz, Expression params) {
		return execQuery(BuilderUtils.prepareFind(clazz).setCondition(params)).toBeans();
	}

	@Override
	public <T extends MGObject> List<T> getObjectList(Class<T> clazz, Expression params, String orderField, 
			boolean asc, int from, int maxnum) {
		return execQuery(BuilderUtils.prepareFind(clazz)
				.setCondition(params)
				.addSort(orderField, asc).setFrom(from).setSize(maxnum))
				.toBeans();
	}

	@Override
	public <T extends MGObject> List<T> getObjectListByNs(String namespace, Class<T> clazz, Expression params) {
		return execQuery(BuilderUtils.prepareFind(namespace, clazz)
				.setCondition(params))
				.toBeans();
	}

	@Override
	public <T extends MGObject> List<T> getObjectListByNs(String namespace, Class<T> clazz, 
			Expression params, String orderField, boolean asc, int from, int maxnum) {
		return execQuery(BuilderUtils.prepareFind(namespace, clazz)
				.setCondition(params)
				.addSort(orderField, asc).setFrom(from).setSize(maxnum))
				.toBeans();
	}

	@Override
	public <T extends MGObject> int getObjectCount(Class<T> clazz, Expression params) {
		return count(BuilderUtils.prepareFind(clazz)
				.setCondition(params));
	}

	@Override
	public int getCount(String namespace, Expression params) {
		return count(BuilderUtils.prepareFind(namespace)
				.setCondition(params));
	}

	@Override
	public <T> List<T> getDistinctPropertyList(String namespace, Expression params, String propertyname, Class<T> clazz) {
		return execDistinct(
				BuilderUtils.prepareDistinct(namespace)
				.setDistinctField(propertyname)
				.setCondition(params),clazz);
	}

	@Override
	public int copyCollection(String fromCollection, String toCollection) {
		int total=0;
		Iterator<Document> ite=currentDB.getCollection(fromCollection).find().iterator();
		if(ite.hasNext()){
			MongoCollection<Document> target=currentDB.getCollection(toCollection);
			while(ite.hasNext()){
				target.insertOne(ite.next());
				total++;
			}
		}
		return total;
	}

	@Override
	public <T extends MGObject> List<T> getObjectList(Class<T> clazz,
			Expression params, String[] orderField, boolean[] asc, int from, int maxnum) {
		return execQuery(BuilderUtils.prepareFind(clazz).setCondition(params).addSort(orderField, asc)
		           .setFrom(from).setSize(maxnum)).toBeans();
	}

	@Override
	public <T extends MGObject> List<T> getObjectList(String namespace,
			Class<T> clazz, Expression params, String[] orderField,
			boolean[] asc, int from, int maxnum) {
		return execQuery(BuilderUtils.prepareFind(namespace, clazz).setCondition(params).addSort(orderField, asc)
		           .setFrom(from).setSize(maxnum)).toBeans();
	}

	@Override
	public ResultCode<Integer> processData(String namespace, Expression params, MongoRowCallback callback, boolean exitOnError) {
		return process(BuilderUtils.prepareFind(namespace).setCondition(params), callback, exitOnError);
	}

	/**
	 * @return
	 */
	@Override
	public Set<String> getCollections(){
		Iterator<String> ite= currentDB.listCollectionNames().iterator();
		Set<String> r=new HashSet<>();
		while(ite.hasNext()){
			r.add(ite.next());
		}
		return r;
	}

	@Override
	public List<Map> getIndexesByNamespace(String namespace) {
		return listIndexes(namespace).toMapList();
	}

	@Override
	public void createIndex(String namespace, String fields) {
		if (StringUtils.isNotBlank(fields)) {
			String indexname = StringUtils.replace(fields, ",", "_");
			String[] arrs = StringUtils.split(fields, ",");
			
			createIndex(BuilderUtils.prepareIndex(namespace).setIndexName(indexname).addIndexField(arrs));
		}
	}

	@Override
	public void dropIndex(String namespace, String indexname) {
		dropIndex(BuilderUtils.prepareIndex(namespace).setIndexName(indexname));
	}

	@Override
	public <T extends MGObject> int getObjectCount(Class<T> clazz) {
		return count(clazz);
	}
	
	@Override
	public Map getCollectionStat(String collectionName) {
		Document doc = currentDB.runCommand(new Document("collStats", collectionName));
		return doc;
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~CURD~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	@Override
	public UpdateRes execUpdate(UpdateBuilder ub){
		MongoCollection<Document> mc=this.currentDB.getCollection(ub.getCollectionName());
		Bson filter = ub.getQueryCondition();
		
		UpdateResult ur=null;
		
		Document data=new Document();
		
		if(!ub.getSource().isEmpty()){
			data.append("$set",ub.getSource());
		}
		if(ub.getOtherSource()!=null && !ub.getOtherSource().isEmpty()){
			if(data.isEmpty()){
				data.putAll(ub.getOtherSource());
			}else{//�ϲ�setԪ��
				if(ub.getOtherSource().get("$set")!=null){
					data.get("$set", Map.class).putAll((Map) ub.getOtherSource().remove("$set"));
				}
				data.putAll(ub.getOtherSource());
			}
		}
		if(ub.isUpdateMany()){
			ur=mc.updateMany(filter,data, ub.getUpdateOptions());
		}else{
			ur=mc.updateOne(filter,data, ub.getUpdateOptions());
		}
		MongoStats.addOp(ub.getCollectionName(), MongoStats.OP_UPDATE);
		return new UpdateRes(ur);
	}
	@Override
	public <T> FindRes<T> execQuery(FindBuilder<T> fb){	
		MongoCollection<Document> mc=this.currentDB.getCollection(fb.getCollectionName());
		Bson c= fb.getQueryCondition();
		FindIterable<Document> fi = (c==null?mc.find(): mc.find(c));
		MongoStats.addOp(fb.getCollectionName(), MongoStats.OP_QUERY);
		if(fb.getProjection()!=null) {
			fi.projection(fb.getProjection());
		}
		if(fb.getSort()!=null) {
			fi.sort(fb.getSort());
		}
		if(fb.getFrom()>0) {
			fi.skip(fb.getFrom());
		}
		if(fb.getSize()!=null && fb.getSize()>0) {
			fi.limit(fb.getSize());
		}
		
		return new FindRes<T>(fi.iterator(), fb.getMapping());
	}
	@Override
	public <T> List<T> execDistinct(DistinctBuilder dis, Class<T> fieldType){
		DistinctIterable<T> result = currentDB.getCollection(dis.getCollectionName()).distinct(dis.getDistincField(), fieldType);
		Iterator<T> ite=result.filter(dis.getQueryCondition()).iterator();
		ArrayList<T> list=new ArrayList<T>();
		while(ite.hasNext()){
			list.add(ite.next());
		}
		return list;
	}
	@Override
	public <T> ResultCode<Integer> process(FindBuilder<T> fb, MongoRowCallback callback, boolean exitOnError){
		MongoCollection<Document> mc= currentDB.getCollection(fb.getCollectionName());
		FindIterable<Document> fi=null;
		Bson c = fb.getQueryCondition();
		fi=mc.find(c);
		
		if(fb.getProjection()!=null) {
			fi.projection(fb.getProjection());
		}
		if(fb.getSort()!=null) fi.sort(fb.getSort());
		if(fb.getFrom()>0) fi.skip(fb.getFrom());
		if(fb.getSize()!=null && fb.getSize()>0) fi.limit(fb.getSize());
		MongoCursor<Document> docs = fi.iterator();
		MongoStats.addOp(fb.getCollectionName(), MongoStats.OP_QUERY);
		int count = 0;
		if(docs!=null && docs.hasNext()){
			while(docs.hasNext()){
				try {
					callback.processRow(docs.next());
					count++;
				} catch (Exception e) {
					dbLogger.warn(LoggerUtils.getExceptionTrace(e, 20));
					if (exitOnError) {
						return ResultCode.<Integer>getFailureReturn(count);
					}
				}
			}
		}
		return ResultCode.<Integer>getSuccessReturn(count);
	}
	@Override
	public DeleteRes execDelete(DeleteBuilder del){
		Bson c= del.getQueryCondition();
		
		MongoCollection<Document> mc = currentDB.getCollection(del.getCollectionName());
		DeleteResult result=null;
		if(del.isDeleteOne()){
			result=mc.deleteOne(c);
		}else{
			result=mc.deleteMany(c);
		}
		MongoStats.addOp(del.getCollectionName(), MongoStats.OP_REMOVE);
		return new DeleteRes(result, del.isDeleteOne());
	}
	@Override
	public void execInsert(InsertBuilder ib){
		MongoCollection<Document> mc = currentDB.getCollection(ib.getCollectionName());
		if(ib.getSources().size()==1){
			Document doc = ib.getTop();
			mc.insertOne(doc);
		}else{
			mc.insertMany(ib.getSources(), ib.getInsertManyOptions());
		}
		MongoStats.addOp(ib.getCollectionName(), MongoStats.OP_UPDATE);
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~CURD end~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	//~~~~~~~~~~~~~~~~~~~~private start ~~~~~~~~~~~~~~~~~~~~~~~
	private int count(String collectionName){
		MongoCollection<Document> mc=currentDB.getCollection(collectionName);
		return (int)mc.count();
	}
	private int count(Class clazz){
		MongoCollection<Document> mc=currentDB.getCollection(clazz.getCanonicalName());
		return (int)mc.count();
	}
	private <T> int count(FindBuilder<T> fb){
		MongoCollection<Document> mc=currentDB.getCollection(fb.getCollectionName());
		return (int)mc.count(fb.getQueryCondition());
	}

	
	//~~~~~~~~~~~~~manage~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private void createIndex(IndexBuilder idx){
		currentDB.getCollection(idx.getCollectionName()).createIndex(idx.getIndex(), idx.getIndexOptions());
	}
	
	private void dropIndex(IndexBuilder idx){
		currentDB.getCollection(idx.getCollectionName()).dropIndex(idx.getIndexOptions().getName());
	}
	
	private FindRes listIndexes(String collectionName){
		Iterator<Document> ite = currentDB.getCollection(collectionName).listIndexes().iterator();
		return new FindRes<>(ite);
	}

}

package com.aube.mongo.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.aube.json.mongo.MaintainInfo;
import com.aube.mdb.operation.Expression;
import com.aube.mongo.MongoAdminService;
import com.aube.mongo.MongoService3;
import com.aube.util.DateUtil;


@SuppressWarnings({"rawtypes"})
public class MongoAdminServiceImpl implements MongoAdminService{
	private static final int minScan = 5000;

	private MongoService3 mongoService3;
	public void setMongoService3(MongoService3 mongoService) {
		this.mongoService3 = mongoService;
	}

	@Override
	public List<Map> getSlowestQuery(String op, Integer mills) {
		if (mills == null) {
			mills = 150;
		}
		Expression qry = new Expression().gt("millis", mills);
		if(StringUtils.isNotBlank(op)){
			qry.eq("op", "query");
		}
		List<Map> result = mongoService3.find("system.profile", qry);
		return result;
	}

	@Override
	public Map<String, Integer> getSlowestQueryForJob(Integer ms, Integer multiple, Integer min) {
		if (ms == null) {
			ms = 200;
		}
		if (multiple == null) {
			multiple = 10;
		}
		Date opTime = DateUtil.addMinute(new Date(), -min);

		Expression query = new Expression();
		query.gt("ts", opTime).gt("millis", ms).eq("op", "query");

		List<Map> queryResult = mongoService3.find("system.profile", query);
		Map<String, Integer> result = new HashMap<String, Integer>();
		for (Map map : queryResult) {
			String ns = String.valueOf(map.get("ns"));
			ns = StringUtils.substring(ns, StringUtils.indexOf(ns, '.')+1);//��ȡdbname
			if (map.get("nreturned")==null || map.get("keysExamined")==null && map.get("docsExamined")==null) {
				return result; //������resultHelper(result, ns);
			} else{
				int nreturned = map.get("nreturned")==null?0:Integer.parseInt(""+map.get("nreturned"));
				int keysExamined =  map.get("keysExamined")==null?0:Integer.parseInt(""+map.get("keysExamined"));//index entries scanned 
				int docsExamined = map.get("docsExamined")==null?0:Integer.parseInt(""+map.get("docsExamined"));//document item scanned
				keysExamined = Math.max(keysExamined, docsExamined);
				
				if(keysExamined > minScan/*>5000*/) {
					nreturned +=1;
					keysExamined +=1;
					boolean multi = keysExamined/nreturned > multiple;
					if(multi){//�ж��Ƿ���ȫ��ɨ
						MaintainInfo mi = getMaintainInfoByName(ns);
						if(mi==null || mi.getRowcount()==null || mi.getRowcount()/keysExamined < 4){
							resultHelper(result, ns);
						}
					}
				}
			}
		}

		return result;
	}

	private void resultHelper(Map<String, Integer> result, String ns) {
		if (result.containsKey(ns)) {
			result.put(ns, result.get(ns) + 1);
		} else {
			result.put(ns, 1);
		}
	}

	@Override
	public Map getCollectionStat(String collectionName) {
		Map result = mongoService3.getCollectionStat(collectionName);
		return result;
	}

	@Override
	public List<MaintainInfo> addNewCollections() {
		Set<String> collectionNames = mongoService3.getCollections();
		List<MaintainInfo> newTables = new ArrayList<MaintainInfo>();
		for (String collectionName : collectionNames) {
			if(collectionName.startsWith("system.")){//ignore system
				continue;
			}
			MaintainInfo findOne = mongoService3.getObjectById(MaintainInfo.class, MaintainInfo.FIELD_NAME, collectionName);
			if (findOne == null) {
				MaintainInfo info = new MaintainInfo(collectionName);
				mongoService3.addObject(info, MaintainInfo.FIELD_NAME);
				newTables.add(info);
			}
		}
		//find all
		List<MaintainInfo> allMaintainedCollections = mongoService3.getObjectList(MaintainInfo.class);

		//build the collections name set
		Set<String> allMaintainedCollectionNames = new HashSet<>();
		for (MaintainInfo maintainInfo : allMaintainedCollections) {
			allMaintainedCollectionNames.add(maintainInfo.getName());
		}
		//remove the delete collections from maintain tables [FIXME howto find name directly]
		allMaintainedCollectionNames.removeAll(collectionNames);
		for (String name : allMaintainedCollectionNames) {
			mongoService3.removeObjectById(MaintainInfo.class, MaintainInfo.FIELD_NAME, name);
		}
		return newTables;
	}

	@Override
	public List<MaintainInfo> getMaintainInfoList(String system) {
		Expression query = new Expression();
		if (StringUtils.isNotBlank(system)) {
			query.eq(MaintainInfo.FIELD_SYSTEM, system);
		}
		List<MaintainInfo> result = mongoService3.getObjectList(MaintainInfo.class, query, "name", true, 0, 2000);
		return result;
	}

	@Override
	public void updateMaintainInfo(MaintainInfo info) {
		if (info.getCreatetime() == null) {
			info.setCreatetime(new Timestamp(System.currentTimeMillis()));
		}
		if(info.getUpdatetime()==null || info.getUpdatetime().before(DateUtil.getCurDate())){
			info.setUpdatetime(new Date());
		}
		if(!info.getName().startsWith("system.")){
			Map stats = mongoService3.getCollectionStat(info.getName());
			if(stats!=null){
				info.setRowcount(Long.valueOf(""+stats.get("count")));
				info.setIndexcount(Integer.valueOf(stats.get("nindexes")+""));
				if(stats.get("avgObjSize")!=null){
					info.setAvgObjSize(Double.valueOf(""+stats.get("avgObjSize")).longValue());
				}else{
					info.setAvgObjSize(0l);
				}
				if(stats.get("storageSize")!=null){
					info.setStorageSize(Double.valueOf("" + stats.get("storageSize")).longValue());
				}
			}
		}
		mongoService3.saveOrUpdateObject(info, MaintainInfo.FIELD_NAME);
	}

	@Override
	public MaintainInfo getMaintainInfoByName(String name) {
		return mongoService3.getObjectById(MaintainInfo.class, "name", name);
	}

}

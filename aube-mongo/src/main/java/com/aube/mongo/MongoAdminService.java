package com.aube.mongo;

import java.util.List;
import java.util.Map;

import com.aube.json.mongo.MaintainInfo;

@SuppressWarnings({"rawtypes"})
public interface MongoAdminService {
	//Map<String, Object> serverStatusMap();
	List<Map> getSlowestQuery(String op, Integer mills);
	Map<String, Integer> getSlowestQueryForJob(Integer ms, Integer multiple, Integer min);
	Map getCollectionStat(String collectionName);
	/**
	 * for job used
	 */
	List<MaintainInfo> addNewCollections();
	List<MaintainInfo> getMaintainInfoList(String system);
	MaintainInfo getMaintainInfoByName(String name);
	void updateMaintainInfo(MaintainInfo info);

}

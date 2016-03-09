package com.aube.mongo;

import java.util.Map;

public interface CollManager {
	/**
	 * @return Map(class.canonicalName,fieldname)
	 */
	Map<String, String> getPkeyMap();
	void validateColl(String collname);
	String getPkey(String collname);

}

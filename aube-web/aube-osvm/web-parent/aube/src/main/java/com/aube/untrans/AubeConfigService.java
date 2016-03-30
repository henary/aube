package com.aube.untrans;

import com.aube.support.ResultCode;

public interface AubeConfigService {

	ResultCode<String> getAubeConfig(String id);

}

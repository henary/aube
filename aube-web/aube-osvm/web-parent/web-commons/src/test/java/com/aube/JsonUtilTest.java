package com.aube;

import org.junit.Test;

import com.aube.resp.vo.DataRespVo;
import com.aube.util.JsonUtils;

public class JsonUtilTest {
	@Test
	public void dataRespVo() {
		DataRespVo<String> respVo = new DataRespVo<String>();
		respVo.setResult("success");
		System.out.println(JsonUtils.writeObjectToJson(respVo));
	}
}

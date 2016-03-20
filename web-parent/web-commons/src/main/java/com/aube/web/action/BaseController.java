package com.aube.web.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aube.Config;
import com.aube.constant.ErrorCodeConstant;
import com.aube.resp.vo.DataRespVo;
import com.aube.support.ResultCode;
import com.aube.util.JsonUtils;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

public class BaseController {
	protected transient final AubeLogger logger = LoggerUtils.getLogger(this.getClass());

	@Autowired
	@Qualifier("config")
	protected Config config;

	public <T> String showJsonReturn(DataRespVo<T> respVo) {
		return JsonUtils.writeObjectToJson(respVo);
	}

	/**
	 * 成功返回结果
	 * 
	 * @param result
	 * @return {"errCode":"错误码","errMsg":"错误提示"}
	 */
	public <T> String showJsonSuccessResult(T result) {
		DataRespVo<T> respVo = new DataRespVo<T>(result);
		return JsonUtils.writeObjectToJson(respVo);
	}

	/**
	 * 错误返回的结构
	 * 
	 * @param errcode
	 * @return {"errCode":"错误码","errMsg":"错误提示"}
	 */
	public <T> String showJsonErrorResult(String errcode) {
		DataRespVo<T> respVo = new DataRespVo<T>(errcode);
		return JsonUtils.writeObjectToJson(respVo);
	}

	/**
	 * 错误返回的结构，带返回结果的
	 * 
	 * @param errcode
	 * @return {"errCode":"错误码","errMsg":"错误提示", "result":返回结果}
	 */
	public <T> String showJsonErrorResult(String errcode, T result) {
		DataRespVo<T> respVo = new DataRespVo<T>(errcode);
		respVo.setResult(result);
		return JsonUtils.writeObjectToJson(respVo);
	}

	public <T> ResultCode<T> getReqVo(HttpServletRequest request, Class<T> clazz) {
		String encode = "utf-8";
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(request.getInputStream(), encode));
			String result = "";
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();
			T t = JsonUtils.parseObj(result, clazz);
			return ResultCode.<T> getSuccessReturn(t);
		} catch (IOException e) {
			logger.error(e, 20);
			return ResultCode.<T> getFailure(ErrorCodeConstant.CODE_UNKNOWN_ERROR, e.getMessage());
		}
	}
}

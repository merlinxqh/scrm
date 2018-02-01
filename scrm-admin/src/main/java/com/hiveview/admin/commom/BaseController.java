package com.hiveview.admin.commom;

import com.hiveview.common.api.BaseEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器基类
 * @author leo
 */
public abstract class BaseController {
	public Logger logger = LoggerFactory.getLogger(getClass());
	

	public HttpServletRequest getRequest(){
		return RequestContextHolder.getRequest();
	}
	
	public HttpServletResponse getResponse(){
		return RequestContextHolder.getResponse();
	}


	/**获取指定参数*/
	public String getString(String name) {
		return getString(name, null);
	}

	public String getString(String name, String defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr == null || "".equals(resultStr)
				|| "null".equals(resultStr) || "undefined".equals(resultStr)) {
			return defaultValue;
		} else {
			return resultStr;
		}
	}
	
	public int getInt(String name) {
		return getInt(name, 0);
	}

	public int getInt(String name, int defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return Integer.parseInt(resultStr);
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public BigDecimal getBigDecimal(String name) {
		return getBigDecimal(name, null);
	}
	
	public BigDecimal getBigDecimal(String name, BigDecimal defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return BigDecimal.valueOf(Double.parseDouble(resultStr));
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * 设置 操作人信息
	 * @param dto
	 * @param <T>
	 */
	public <T extends BaseEntityDto> void putOperatorInfo(T dto){
		if(StringUtils.isEmpty(dto.getId())){
			dto.setCreateBy(SystemUserUtils.getCurrentUser().getUsername());
			dto.setCreateDate(new Date());
		}
		dto.setLastUpdateBy(SystemUserUtils.getCurrentUser().getUsername());
		dto.setLastUpdateDate(new Date());
	}


	/**
	 * 获取request参数map
	 * @return
	 */
	protected Map<String,String> getRequestMap(){
		return RequestContextHolder.currentParam.get();
	}

	/**
	 * 返回拓展 参数
	 * @param keys
	 * @return
	 */
	public Map<String,Object> getExtMap(String... keys){
        Map<String,Object> map = new HashMap<>();
        for(String key:keys){
        	if(StringUtils.hasText(key)){
				map.put(key, getString(key));
			}
		}
		return map;
	}

}

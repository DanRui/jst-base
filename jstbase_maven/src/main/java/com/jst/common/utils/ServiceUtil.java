package com.jst.common.utils;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jst.type.DataType;
import com.jst.util.PropertyUtil;
import com.jst.util.WebServiceUtil;

public class ServiceUtil {
	
	
	private static final Log log = LogFactory.getLog(ServiceUtil.class);
	
	private static final String SERVICE_URL_PREFIX = PropertyUtil.getPropertyValue("cache.message.serviceurl");
	
	//目标命名空间
	private static final String TARGET_NAMESPACE = PropertyUtil.getPropertyValue("cache.message.namespace");
	
	/**
	 * @see 调用接口
	 * @param serviceName
	 * @param interfaceName
	 * @param paramValues
	 * @param dataType
	 * @return String
	 * @throws Exception
	 */
	public static String invokeInterface(String serviceName, String interfaceName, Object[] paramValues, DataType dataType) throws Exception {
		Object[] object = Arrays.copyOf(paramValues, paramValues.length + 1);
		
		object[object.length - 1] = null == dataType ? DataType.XML.toString() : dataType.toString();
		
		try {
			return (String) WebServiceUtil.invokeInterface(SERVICE_URL_PREFIX + serviceName, TARGET_NAMESPACE, interfaceName, null, object, new Class[]{String.class}, true, null)[0];
		} catch (Exception e) {
			log.error("invokeInterface error: " + e);
			
			throw e;
		}
	}
}

package com.jst.common.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jst.common.model.DictType;
import com.jst.common.model.SysDict;
import com.jst.config.ObjectSerializeConfig;
import com.jst.util.JsonUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class ConvertJsonUtil {
	
	public ConvertJsonUtil(ObjectSerializeConfig config){
		serializeConfig =  config;
	}

	
	private static ObjectSerializeConfig serializeConfig;
	
	public static String serialize(Object object){
		return _getXStream().toXML(object);
	}
	
	private static XStream _getXStream(){
		XStream xStream =  new XStream() ;
		Map<Class<?>, String> objectAlias = serializeConfig.getObjectAlias();

		for (Iterator<Class<?>> it = objectAlias.keySet().iterator(); it.hasNext();) {
			Class<?> clazz = it.next();

			xStream.alias(objectAlias.get(clazz), clazz);
		}
		return xStream;
	}
	
	public static <T> T deserialize(String objectString) {
		return (T) _getXStream().fromXML(objectString);
	}
	
	
	public static void main(String[] args) {
		List<DictType> list = new ArrayList<DictType>();
		DictType dictType = new DictType();
		Set<SysDict> set = new HashSet<SysDict>();
		SysDict sysDict = new SysDict();
		sysDict.setDictCode("asd");
		sysDict.setDictName("这是SysDictName");
		set.add(sysDict);
		dictType.setSysDicts(set);
		list.add(dictType);
		ObjectSerializeConfig config = new ObjectSerializeConfig();
		config.setObjectAlias(SysDict.class, "sys_dict");
		config.setObjectAlias(DictType.class, "dict_type");
		String aa = getInstance(config).serialize(list);
		System.out.println(aa);
		
		
		System.out.println(JsonUtil.parse(list).toString());
		
		Object o = deserialize(aa);
		
		System.out.println("bbb");
		
	}

	public static ConvertJsonUtil getInstance(ObjectSerializeConfig config){
		return new ConvertJsonUtil(config);
	}
}

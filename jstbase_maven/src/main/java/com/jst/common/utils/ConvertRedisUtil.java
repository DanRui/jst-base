package com.jst.common.utils;

import java.util.List;

import com.jst.common.model.SysDict;

import net.sf.json.JSONObject;

/**
 * 
 * @author Administrator
 *
 */
public class ConvertRedisUtil {
	
	
	
	/**
	 * 
	 * @param sysDictStr
	 * @return
	 * @throws Exception
	 */
	public static String getDictName(String sysDictStr) throws Exception{
		SysDict sysDict = (SysDict)JSONObject.toBean(JSONObject.fromObject(sysDictStr),SysDict.class);
		return sysDict.getDictName();
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static SysDict convertDictType(String value) throws Exception{
		SysDict sysDict = (SysDict)JSONObject.toBean(JSONObject.fromObject(value),SysDict.class);
		return sysDict;
	}
	
	public List<SysDict>  convertSysDictList(String str) throws Exception{
		/*Set<String> list =  getRedis().zrangeByScore(Constants.CURRENT_APPCODE+SPLIT_STR+SYS_DICT_LIST+SPLIT_STR+dictType, "+inf", "-inf");
		List<SysDict> sysList = new ArrayList<SysDict>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			String s = (String) iterator.next();
			sysList.add((SysDict)JSONObject.toBean(JSONObject.fromObject(s),SysDict.class));
		}*/
		return null;
	}
	
}

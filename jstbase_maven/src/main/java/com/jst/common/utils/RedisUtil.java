package com.jst.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.axis2.databinding.types.soapencoding.Array;

import com.jst.common.model.DictType;
import com.jst.common.model.SysDict;
import com.jst.platformClient.utils.Constants;
import com.jst.util.JsonUtil;
import com.jst.util.PropertyUtil;
import com.jst.util.StringUtil;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

/**
 * 用于调用Redis缓存工具类
 * @author Administrator
 *
 */
public class RedisUtil {
	
	private static final String JOIN_STR = ".";
	
	private static Jedis jedis =null;
	
	public static final String SPLIT_STR = ".";
	
	/**
	 * 学员综合信息类型
	 */
	public static final String STU_INFO = "STUDENTINFOMATION";
	
	/**
	 *	字典集合类型
	 */
	public static final String SYS_DICT_LIST = "SYS_DICT_LIST";
	
	/**
	 * 字典类型
	 */
	public static final String SYS_DICT = "SYS_DICT";
	
	/**
	 * 信息发布类型
	 */
	public static final String INFO_DIFFUSION = "INFO_DIFFUSION";
	
	/**
	 * 获取Redis链接。
	 * @return
	 */
	public static Jedis getRedis(){
		if(null== jedis){
			String redisIp = PropertyUtil.getPropertyValue("redis.ip");
			String redisPort = PropertyUtil.getPropertyValue("redis.port");
			Integer port = 0;
			if(StringUtil.isNotEmpty(redisPort)){
				port = Integer.parseInt(redisPort);
			}
			try {
				jedis = new Jedis(redisIp, port);
				jedis.connect();
			} catch (Throwable e){
				System.out.println(e);
			}
		}
		return jedis;
	}
	
	/**
	 * 设置项目缓存到Redis
	 * @param type 存储数据的类型（如，系统权限，学员信息，等等，请勿使用中文名称）
	 * @param key  
	 * @return
	 * @throws Exception
	 */
	public static boolean setValue(String type,String key,String value) throws Exception{
		if(null == jedis ){
			getRedis();
		}
		if(StringUtil.isEmpty(value)){
			return false;
		}
		
		String setKey = Constants.CURRENT_APPCODE+JOIN_STR+type+JOIN_STR+key;
		jedis.set(setKey, value);
		jedis.close();
		return false;
	}
	
	/**
	 * 获取当前项目中缓存
	 * @param type  存储数据的类型（如，系统权限，学员信息，等等，请勿使用中文名称）
	 * @param key   
	 * @return
	 * @throws Exception
	 */
	public static String getValue(String type,String key) throws Exception{
		if(null == jedis ){
			getRedis();
		}
		String getKey = Constants.CURRENT_APPCODE+JOIN_STR+type+JOIN_STR+key;
		String value = jedis.get(getKey);
		jedis.close();
		return value;
	}
	
	/**
	 * 获取学员综合信息
	 * @param idCard 学员身份证号码
	 * @return
	 * @throws Exception
	 */
	public static String getStudentInfo(String idCard) throws Exception{
		return getRedis().get(Constants.CURRENT_APPCODE + SPLIT_STR + STU_INFO+SPLIT_STR+idCard);
	}
	
	/**
	 * 根据字典类型与值获取字典的名称
	 * @param dictValue
	 * @return
	 * @throws Exception
	 */
	public static String getDictNameByDictValue(String dictType, String dictValue) throws Exception{
		/*String value = getRedis().get(Constants.CURRENT_APPCODE+SPLIT_STR+SYS_DICT+SPLIT_STR+dictType+SPLIT_STR+dictValue);
		SysDict sysDict = (SysDict)JSONObject.toBean(JSONObject.fromObject(value),SysDict.class);
		return sysDict.getDictName();*/
		return getRedis().get(Constants.CURRENT_APPCODE+SPLIT_STR+SYS_DICT+SPLIT_STR+dictType+SPLIT_STR+dictValue);
	}
	
	/**
	 * 根据字典类型 获取字典所有值
	 * @param dictType
	 * @return
	 * @throws Exception
	 */
	public static String getSysDictList(String dictType) throws Exception{
		/*Set<String> list =  getRedis().zrangeByScore(Constants.CURRENT_APPCODE+SPLIT_STR+SYS_DICT_LIST+SPLIT_STR+dictType, "+inf", "-inf");
		List<SysDict> sysList = new ArrayList<SysDict>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			String s = (String) iterator.next();
			sysList.add((SysDict)JSONObject.toBean(JSONObject.fromObject(s),SysDict.class));
		}*/
		return getRedis().get(Constants.CURRENT_APPCODE+SPLIT_STR+SYS_DICT_LIST+SPLIT_STR+dictType);
	}
	
	/**
	 * 根据信息发布类型及页数 获取分页的Json数据
	 * @param type
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public static String getInfoList(String type ,Integer page) throws Exception{
		String key = Constants.CURRENT_APPCODE+SPLIT_STR+INFO_DIFFUSION+SPLIT_STR+type;
		if(null!=page){
			key+=SPLIT_STR+page;
		}
		return getRedis().get(key);
	}
	
	/**
	 * 获取单条的信息发布信息的Json字符串
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static String getInfo(String id) throws Exception{
		String key = Constants.CURRENT_APPCODE+SPLIT_STR+INFO_DIFFUSION+SPLIT_STR+id;
		return getRedis().get(key);
	}
	
	/**
	 * 根据字典code获取字典的名称
	 * @param dictType	字典类型
	 * @param dictCode	字典code
	 * @return
	 * @throws Exception
	 */
	public static String getDictNameByDictCode(String dictType,String dictCode) throws Exception{
		/*String value = getRedis().get(Constants.CURRENT_APPCODE+SPLIT_STR+SYS_DICT+SPLIT_STR+dictType+SPLIT_STR+dictCode);
		SysDict sysDict = (SysDict)JSONObject.toBean(JSONObject.fromObject(value),SysDict.class);
		return sysDict.getDictName();*/
		return  getRedis().get(Constants.CURRENT_APPCODE+SPLIT_STR+SYS_DICT+SPLIT_STR+dictType+SPLIT_STR+dictCode);
	}
	
	
	
	public static void main(String[] args) {
		
		/*DictType d = new DictType();
		d.setRemark("aaa");

		getRedis().set("ZZJG.STUDENTINFO.PREPAYNO.430523", 		JsonUtil.parse(d).toString());*/
		
		SortingParams sp = new SortingParams();
		sp.by("withscores");
		sp.get("ZZJG.STUDENTINFO.*.430523");
		List<String> list = getRedis().sort("ZZJG.STUDENTINFO.INFORMATION", sp);
		String d = list.get(3);
		DictType dt = (DictType)JSONObject.toBean(JSONObject.fromObject(d),DictType.class);
		System.out.println("asdasd");
	}
	
}

package com.jst.common.system;

import java.util.HashMap;
import java.util.Map;

public class LogConstants {
	
	//当前操作开始时间
	public final static String CURRENT_START_TIME = "current_start_time";
	
	//当前服务名称
	public final static String SERVICE_NAME = "serviceName";
	
	//当前调用的Web 或者WebService 方法
	public final static String  WEB_SERVICE_METHOD= "webservicemethod";
	
	//当前Web中的 sessionInfo 信息
	public final static String WEB_SESSION_INFO = "websessioninfo";
	
	//当前的Web (可用于WebService)来源信息
	public final static String WEB_TERMINAL_INFO = "webterminalinfo";
		
	//当前回话中的terminal，
	public final static String CURRENT_TERMINAL= "currentterminal";
	
	//当前会话中的session信息
	public final static String CURRENT_SESSION = "currentsession";
	
	//当前调用的 Web或WebService参数信息
	public final static String WEB_SERVICE_ARGS= "webserviceargs";
	
	//当前方法的模块名称
	public final static String MDL_CODE= "mdlcode";
	
	//当前方法的权限信息
	public final static String MDL_PRVG= "mdlprvg";
	
	//当前操作用户代码
	public final static String USER_CODE = "usercode" ;
	
	//当前操作用户的名称
	public final static String USER_NAME = "username";
	
	//当前访问的IP地址来源
	public final static String IP_STR = "ipstr";
	
	//操作类型名称
	public final static String OPE_TYPE_NAME = "opetypename";
	
	//操作对象名称
	public final static String OBJ_TYPE_NAME = "objtypename";
	
	public static Map<Long,Map<String,Object>> threadMap= new HashMap<Long, Map<String,Object>>();
	
	private static Long getId(){
		Thread thread = Thread.currentThread();
		Long threadId = thread.getId();
		return threadId;
	}
	
	public static void put(String key,Object value){
		Long threadId = getId();
		if(threadMap.containsKey(threadId)){
			threadMap.get(threadId).put(key, value);
		}else{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(key, value);
			threadMap.put(threadId, map);
		}
		
	}
	
	public static Object remove(String key,Object value){
		Long threadId = getId();
		if(threadMap.containsKey(threadId)){
			return threadMap.get(threadId).remove(key);
		}else{
			return null;
		}
	}
	
	public static Object get(String key){
		Long threadId = getId();
		if(threadMap.containsKey(threadId)){
			return threadMap.get(threadId).get(key);
		}
		return null;
	}
	
	public static void clear(){
		Long threadId = getId();
		if(threadMap.containsKey(threadId)){
			threadMap.remove(threadId);
		}
		
	}
}

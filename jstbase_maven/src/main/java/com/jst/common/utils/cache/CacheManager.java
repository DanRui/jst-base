package com.jst.common.utils.cache;

import java.util.concurrent.ConcurrentHashMap;

public class CacheManager {
	
    //默认的键
	public static  String[] INIT_KEYS ={};
	public static  ConcurrentHashMap<String, Object> cach = new ConcurrentHashMap<String, Object>();
	public static  ConcurrentHashMap<String ,ValueProvider>  valueProviderMap  = new ConcurrentHashMap<String ,ValueProvider>();
	public static void addToValueProviderMap(String key,ValueProvider vp){
		valueProviderMap.put(key, vp);
	}
	public static ConcurrentHashMap<String,ValueProvider>  getValueProviderMap(){
		return valueProviderMap;
	}
	;
	//初始化默认的键
	/*static{	
		for(String key:INIT_KEYS){
			get(key);		
		}
		
	}*/

	
	public static <T> T put(String key ,T value){	
		cach.put(key, value);
		return value;
		
	}
	
	public static Object get (String key ,ValueProvider vp){
		if(vp==null){
			return null;
		}
		valueProviderMap.put(key, vp);
		cach.put(key,  vp.getValue(key));
		return vp.getValue(key);
	}
	
     public static  Object get (String key ){	 
    	 if(cach.get(key)!=null){
    		 return cach.get(key);
    	 }else if(valueProviderMap.get(key)!=null){
    		 return get(key,valueProviderMap.get(key)); 		 
    	 } 
		return  null;
		
	}
     public static void refresh(String key){		
			get(key,valueProviderMap.get(key));	
	}
     public static void refreshAll(){
 		for(String key:valueProviderMap.keySet()){			
 			get(key,valueProviderMap.get(key));
 		}
 	}
	
}

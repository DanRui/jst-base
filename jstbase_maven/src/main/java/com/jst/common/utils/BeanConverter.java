package com.jst.common.utils;

import java.lang.reflect.Field;


import com.jst.common.utils.reflect.ReflectionUtils;

public class BeanConverter {
	/*
	 * 将src 对象中的值赋给to，isBySrc参数决定是否根据src中的属性对to中的同属性进行赋值
	 * true表示，是，false 表示否
	 * 	 */
	public static <T> T convertBean(Object src, T to,boolean isBySrc){
		Field [] fields ;
		if(isBySrc){
			fields= src.getClass().getDeclaredFields();
		}else{
			fields= to.getClass().getDeclaredFields();
		}		
		for(Field field:fields){
			String propertyName = field.getName();
			Object value= ReflectionUtils.invokeGetterMethod(src, propertyName);
			ReflectionUtils.invokeSetterMethod(to, propertyName, value);		
		}
	   return to;
	}

}

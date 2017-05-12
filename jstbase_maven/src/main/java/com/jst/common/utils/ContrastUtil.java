package com.jst.common.utils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jst.util.StringUtil;

/**
 * 用于获取实体对象中的属性或值，及用于相同对象之间的对比
 * @author Administrator
 *
 */
public class ContrastUtil {
private static final Log log = LogFactory.getLog(ContrastUtil.class);
	
	private static final String GET_METHOD_PREFIX = "get";
	private static final String SET_METHOD_PREFIX = "set";
	
	
	/**
	 * @see 获取方法名称
	 * @param methodPrefix
	 * @param fieldName
	 * @return
	 */
	public static String getMethodName(String methodPrefix, String fieldName){
		return methodPrefix + StringUtil.firstLetterToUpperCase(fieldName); 
	}
	
	/**
	 * @see 校验方法是否存在
	 * @param entity
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static boolean hasMethod(Object entity, String methodName) throws Exception{
		Method[] methods = null;
		
		boolean have = false;
		
		try{
			methods = entity.getClass().getDeclaredMethods();
			
			for(Method method : methods){
				if(methodName.equals(method.getName())){
					have = true;
					
					break;
				}
			}
		}catch(Exception e){
			log.error("hasMethod error：", e);
			
			throw e;
		}
		
		return have;
	}
	
	/**
	 * @see 校验当前方法其方法体上是否包含注解
	 * @param method
	 * @return
	 */
	public static boolean hasAnnotation(Method method){
		return null != method.getAnnotations() && method.getAnnotations().length > 0;
	}
	
	/**
	 * @see 校验当前方法其方法体上是否包含特定的注解
	 * @param method
	 * @param annotationClass
	 * @return
	 */
	public static boolean hasAnnotation(Method method, Class<? extends Annotation> annotationClass){
		return method.isAnnotationPresent(annotationClass);
	}
	
	/**
	 * @see 获取当前实体类所有声明的字段
	 * @param entityClass
	 * @return
	 */
	public Field[] getDeclaredFields(Class<?> entityClass){
		return entityClass.getDeclaredFields();
	}
	
	/**
	 * @see 获取当前实体类所有声明的字段
	 * @param entity
	 * @return
	 */
	public Field[] getDeclaredFields(Object entity){
		return entity.getClass().getDeclaredFields();
	}
	
	/**
	 * 根据方法名获取字段名
	 * @param methodName
	 * @return
	 */
	public static String getFieldName(String methodName){
		if(methodName.startsWith(SET_METHOD_PREFIX)){
			return methodName.substring(methodName.indexOf(SET_METHOD_PREFIX) + SET_METHOD_PREFIX.length());
		}
		
		if(methodName.startsWith(GET_METHOD_PREFIX)){
			return methodName.substring(methodName.indexOf(GET_METHOD_PREFIX) + GET_METHOD_PREFIX.length());
		}
		
		return null;
	}
	
	/**
	 * 根据方法获取字段名
	 * @param method
	 * @return
	 */
	public static String getFieldName(Method method){
		return getFieldName(method.getName());
	}
	
	public static String getPropertyName(Class<?> entityClass, String columnName){
		String propertyName = null;
		
		for(Method method : entityClass.getDeclaredMethods()){
			if(hasAnnotation(method, Column.class) && columnName.toLowerCase().equals(method.getAnnotation(Column.class).name().toLowerCase())){
				propertyName = StringUtil.firstLetterToLowerCase(getFieldName(method));
				
				break;
			}
		}
		
		return propertyName;
	}
	
	/**
	 * @see 获取字段类型
	 * @param entity
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static Class<?> getFieldType(Class<?> entityClass, String fieldName) throws Exception {
		
		try{
			return entityClass.getMethod(getMethodName(GET_METHOD_PREFIX, fieldName)).getReturnType();
		}catch(Exception e){
			log.error("getFieldType", e);
			
			throw e;
		}
	}
	
	/**
	 * @see 获取字段类型
	 * @param entity
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static Class<?> getFieldType(Object entity, String fieldName) throws Exception {
		
		try{
			return getFieldType(entity.getClass(), fieldName);
		}catch(Exception e){
			log.error("getFieldType", e);
			
			throw e;
		}
	}
	
	/**
	 * @see 设置实体属性值
	 * @param entity
	 * @param fieldName
	 * @param fieldValue
	 * @throws Exception
	 */
	public static void setFieldValue(Object entity, String fieldName, Object fieldValue) throws Exception {
		
		try{
			entity.getClass().getMethod(getMethodName(SET_METHOD_PREFIX, fieldName), getFieldType(entity, fieldName)).invoke(entity, fieldValue);
		}catch(Exception e){
			log.error("setFieldValue", e);
			
			throw e;
		}
	}
	
	/**
	 * @see 获取实体属性值
	 * @param entity
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static Object getFieldValue(Object entity, String fieldName) throws Exception {
		
		try{
			return entity.getClass().getMethod(getMethodName(GET_METHOD_PREFIX, fieldName)).invoke(entity);
		}catch(Exception e){
			log.error("getFieldValue", e);
			
			throw e;
		}
		
	}
	
	/**
	 * @see 获取实体类对应表名
	 * @param entityClass
	 * @return
	 */
	public static String getTableName(Class<?> entityClass){
		return entityClass.getAnnotation(Table.class).name();
	}
	
	/**
	 * @see 获取实体类对应表名
	 * @param entity
	 * @return
	 */
	public static String getTableName(Object entity){
		return getTableName(entity.getClass());
	}
	
	/**
	 * @see 获取实体类字段对应列名
	 * @param method
	 * @return
	 */
	public static String getColumnName(Method method){
		return method.getAnnotation(Column.class).name();
	}

	/**
	 * @see 获取实体类主键对应列名
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static String getPrimaryKeyName(Object entity) throws Exception{
		String primaryKeyName = null;
		try{
			
			 
			 
			 Field [] fields = entity.getClass().getDeclaredFields();
			 
			 for (int i = 0; i < fields.length; i++) {
				 PropertyDescriptor pd = new PropertyDescriptor(fields[i].getName(),entity.getClass());
				 Method method = pd.getReadMethod();
				 if(method.isAnnotationPresent(Id.class)){
					 primaryKeyName = fields[i].getName();
					 break;
				 }
				
			}
			 
			/*for(Method method : entity.getClass().getDeclaredMethods()){
				String methodName = method.getName();
				System.out.println(methodName);
				if(methodName.startsWith(SET_METHOD_PREFIX)){
					continue;
				}
				
				if(method.isAnnotationPresent(Id.class)){
					primaryKeyName = getColumnName(method);
					
					break;
				}
			}*/
		}catch(Exception e){
			throw e;
		}
		
		return primaryKeyName;
	}
	
	/**
	 * @see 获取实体类主键对应列值
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static String getPrimaryKeyValue(Object entity) throws Exception{
		String primaryKeyValue = null;
		
		try{
			
			
			Field [] fields = entity.getClass().getDeclaredFields();
			 
			 for (int i = 0; i < fields.length; i++) {
				 PropertyDescriptor pd = new PropertyDescriptor(fields[i].getName(),entity.getClass());
				 Method method = pd.getReadMethod();
				 if(method.isAnnotationPresent(Id.class)){
					 
					 primaryKeyValue = String.valueOf(method.invoke(entity));
					 break;
				 }
				
			}
			/*for(Method method : entity.getClass().getDeclaredMethods()){
				String methodName = method.getName();
				
				if(methodName.startsWith(SET_METHOD_PREFIX)){
					continue;
				}
				
				if(method.isAnnotationPresent(Id.class)){
					primaryKeyValue = String.valueOf(method.invoke(entity));
					
					break;
				}
			}*/
		}catch(Exception e){
			throw e;
		}
		
		return primaryKeyValue;
	}
	
	/**
	 * @see 说明：将实体各属性的值克隆至目标实体,仅克隆属性,各指针指向独立内存地址
	 *      条件：两个具有完全相同属性的实体或同一实体类的不同实例
	 * @param src
	 * @param target
	 */
	public static void cloneEntity(Object src, Object target) throws Exception{
		
		try{
			Method[] srcMethods = src.getClass().getDeclaredMethods();
			
			for(Method srcMethod : srcMethods){
				String methodName = srcMethod.getName();
				
				if(methodName.startsWith(SET_METHOD_PREFIX)){
					continue;
				}
				
				setFieldValue(target, getFieldName(srcMethod), srcMethod.invoke(src));
			}
		}catch(Exception e){
			log.error("cloneEntity error：", e);
			
			throw e;
		}
	}
	
	/**
	 * @see 克隆实体所有属性，并返回该实体类新的实例
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static Object cloneEntity(Object src) throws Exception{
		Object target = null;
		
		try{
			target = src.getClass().newInstance();
			
			cloneEntity(src, target);
		}catch(Exception e){
			log.error("cloneEntity error：", e);
			
			throw e;
		}
		
		return target;
	}
	
	/**
	 * @see 将目标实体个属性克隆至目标实体,部分或完全克隆
	 * @param src
	 * @param target
	 * @throws Exception
	 */
	public static void clonePart(Object src, Object target) throws Exception{
		
		try{
			Method[] srcMethods = src.getClass().getDeclaredMethods();
			
			for(Method srcMethod : srcMethods){
				String methodName = srcMethod.getName();
				
				if(methodName.startsWith(SET_METHOD_PREFIX)){
					continue;
				}
				
				if(hasMethod(target, methodName)){
					setFieldValue(target, getFieldName(srcMethod), srcMethod.invoke(src));
				}
			}
		}catch(Exception e){
			log.error("clonePart error：", e);
			
			throw e;
		}
	}
	
	/**
	 * @see 获取同一实体类的两个不同实例的区别
	 *      应该还有判断不足之处，但就当前系统
	 *      以满足功能
	 * @param oldObejct
	 * @param newObject
	 * @return
	 * @throws Exception
	 */
	public static String getChange(Object oldObejct, Object newObject) throws Exception {
		StringBuffer buffer = null;
		
		try{
			Method[] oldMethods = oldObejct.getClass().getDeclaredMethods();
			
			String tableName = getTableName(oldObejct);
			String primaryKeyName = getPrimaryKeyName(oldObejct);
			String primaryKeyValue = getPrimaryKeyValue(oldObejct);
			
			buffer = new StringBuffer();
			
			buffer.append("表名：" + tableName);
			buffer.append("\n");
			buffer.append("主键：" + primaryKeyName);
			buffer.append("\n");
			buffer.append("键值：" + primaryKeyValue);
			buffer.append("\n");
			
			for(Method oldMethod : oldMethods){
				String methodName = oldMethod.getName();
				
				if(methodName.startsWith(SET_METHOD_PREFIX)){
					continue;
				}
				
				if(hasAnnotation(oldMethod, OneToOne.class)
						|| hasAnnotation(oldMethod, OneToMany.class)
						|| hasAnnotation(oldMethod, ManyToOne.class)
						|| hasAnnotation(oldMethod, ManyToMany.class)){
					continue;
				}
				
				Object oldValue = oldMethod.invoke(oldObejct);
				Object newValue = newObject.getClass().getMethod(methodName).invoke(newObject);
				
				if(null == oldValue || null == newValue){
					Object value = null != oldValue ? oldValue : (null != newValue ? newValue : null); 
					
					if(null != value && !"".equals(value)){
						buffer.append("字段：" + getColumnName(oldMethod));
						buffer.append(StringUtil.addSpace(1));
						
						if(value instanceof Date){
							String datePattern = null;
							
							if(DateUtil.containsTime((Date)value)){
								datePattern = DateUtil.TIMESTAMPS_PATTERN;
							}else{
								datePattern = DateUtil.DATE_PATTERN;
							}
							
							if(null != oldValue){
								buffer.append("原值：" + DateUtil.format((Date)oldValue, datePattern));
							}else{
								buffer.append("原值：" + oldValue);
							}
							
							buffer.append(StringUtil.addSpace(1));
							
							if(null != newValue){
								buffer.append("原值：" + DateUtil.format((Date)newValue, datePattern));
							}else{
								buffer.append("原值：" + newValue);
							}
							
							buffer.append("\n");
						}else{
							buffer.append("原值：" + oldValue);
							buffer.append(StringUtil.addSpace(1));
							buffer.append("新值：" + newValue);
							buffer.append("\n");
						}
					}
				}else{
					if(!String.valueOf(newValue).equals(String.valueOf(oldValue))){
						buffer.append("字段：" + getColumnName(oldMethod));
						buffer.append(StringUtil.addSpace(1));
						
						if(oldValue instanceof Date){
							String datePattern = null;
							
							if(DateUtil.containsTime((Date)oldValue)){
								datePattern = DateUtil.TIMESTAMPS_PATTERN;
							}else{
								datePattern = DateUtil.DATE_PATTERN;
							}
							
							buffer.append("原值：" + DateUtil.format((Date)oldValue, datePattern));
							buffer.append(StringUtil.addSpace(1));
							buffer.append("新值：" + DateUtil.format((Date)newValue, datePattern));
							buffer.append("\n");
						}else{
							buffer.append("原值：" + oldValue);
							buffer.append(StringUtil.addSpace(1));
							buffer.append("新值：" + newValue);
							buffer.append("\n");
						}
					}
				}
			}
		}catch(Exception e){
			log.error("getChange error: ", e);
			
			throw e;
		}
		
		return buffer.toString();
	}
	
	/**
	 * 对比同对象内的属性改变信息
	 * @param oldObject
	 * @param newObject
	 * @return
	 * @throws Exception
	 */
	public static String contrast(Object oldObject,Object newObject) throws Exception{
		StringBuffer buffer = new StringBuffer();
		Field[] field = oldObject.getClass().getDeclaredFields();
		String tableName = getTableName(oldObject.getClass());
		String primaryKeyName = getPrimaryKeyName(oldObject);
		String primaryKeyValue = getPrimaryKeyValue(oldObject);
		
		buffer = new StringBuffer();
		
		buffer.append("表名：" + tableName);
		buffer.append("\n");
		buffer.append("主键：" + primaryKeyName);
		buffer.append("\n");
		buffer.append("键值：" + primaryKeyValue);
		buffer.append("\n");
		for (int i = 0; i < field.length; i++) {
			Field oldf = field[i];
			oldf.setAccessible(true);
			Field newf = newObject.getClass().getDeclaredField(oldf.getName());
			newf.setAccessible(true);
			Object oldValue = oldf.get(oldObject);
			Object newValue = newf.get(newObject);
			if(oldValue != newValue){
				buffer.append("字段：" + oldf.getName());
				buffer.append(StringUtil.addSpace(1));
				if(oldValue instanceof Date){
					String datePattern = null;
					
					if(DateUtil.containsTime((Date)oldValue)){
						datePattern = DateUtil.TIMESTAMPS_PATTERN;
					}else{
						datePattern = DateUtil.DATE_PATTERN;
					}
					
					if(null != oldValue){
						buffer.append("原值：" + DateUtil.format((Date)oldValue, datePattern));
					}else{
						buffer.append("原值：" + oldValue);
					}
					
					buffer.append(StringUtil.addSpace(1));
					
					if(null != newValue){
						buffer.append("新值：" + DateUtil.format((Date)newValue, datePattern));
					}else{
						buffer.append("新值：" + newValue);
					}
					
					buffer.append("\n");
				}else{
					buffer.append("原值：" + oldValue);
					buffer.append(StringUtil.addSpace(1));
					buffer.append("新值：" + newValue);
					buffer.append("\n");
				}
			}
		}
		return buffer.toString();
	}
}

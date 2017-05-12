/**
 * 
 */
package com.jst.common.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author
 * 
 */
public class ServiceFactory {

	static ApplicationContext context;

	public static Object getService(String serviceName) {
		if (context == null) {
			context = new ClassPathXmlApplicationContext(
					"applicationContext.xml");
		}

		return context.getBean(serviceName);

	}
	

	public static Object getService(String serviceName, Object para) {
		if (context == null) {
			context = new ClassPathXmlApplicationContext(
					"applicationContext.xml");
		}

		return context.getBean(serviceName);
	}

	/*
	 * public static ILog getLogService(Class className) { return new
	 * LogService(className); }
	 * 
	 * 
	 * public static ILog getLogService(String theCategoryName) { return new
	 * LogService(theCategoryName); }
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * try{ IUser iUser=(IUser)getService("platform_user");
		 * iUser.checkLogin("test", "test123"); }catch(Exception e){
		 * e.printStackTrace(); }
		 */
	}
}

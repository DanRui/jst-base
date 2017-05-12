package com.jst.common.utils.web;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {
	
	
	public static String  getIP(HttpServletRequest request){
		return request.getRemoteAddr() ; 
		
	}
	
	

}

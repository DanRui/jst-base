package com.jst.common.json;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class WriterUtil {
	public static void getJSONString(HttpServletResponse response,
	    String jsonString) throws IOException {
	   response.setContentType("text/html");
	   response.setCharacterEncoding("utf-8");
	   response.setHeader("Pragma", "no-cache");
	   response.setHeader("Cache-Control", "no-cache, must-revalidate");
	   response.setHeader("Pragma", "no-cache");
	   PrintWriter out = response.getWriter();
	   out.write(jsonString);
	   out.flush();
	   out.close();
	}
	
	public static void writeJSONString(HttpServletResponse response,
		    String jsonString) throws IOException {
		   response.setContentType("text/html");
		   response.setCharacterEncoding("utf-8");
		   response.setHeader("Pragma", "no-cache");
		   response.setHeader("Cache-Control", "no-cache, must-revalidate");
		   response.setHeader("Pragma", "no-cache");
		   PrintWriter out = response.getWriter();		   
		   out.write(jsonString);
		   out.flush();
		   out.close();
		}
}

package com.jst.common.jsp;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Administrator * 
 */
public class ServletBase extends HttpServlet {
	
	private static final Log log = LogFactory.getLog(ServletBase.class);
	public boolean debugFlag = false;
	public String operation = null;
	public String historyURL = null;
	public String listURL = null; 
	public String nextURL = null;
	public String loginURL=null;
	public String sessionUserCode=null;
	public String sessionUserName=null;
	public String sessionDeptCode=null;
	public String sessionDeptName=null;
	
	
	
	/**
	 * 
	 * @param req
	 * @param res
	 * @param nextURL 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void servletPage(HttpServletRequest req, HttpServletResponse res,
			String nextURL) throws ServletException, IOException {
		
		
		RequestDispatcher requestdispatch=this.getServletConfig().getServletContext().getRequestDispatcher(nextURL);
		log.debug("servletPage nextURL" + nextURL);
		requestdispatch.forward(req,res);

		
		
	}

	public void goPage(HttpServletRequest req, HttpServletResponse res,
			String nextURL) throws ServletException, IOException {
		// RequestDispatcher disp = req.getRequestDispatcher(nextURL);
		log.debug("servletPage goPage:��" + nextURL);

		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		response.sendRedirect(nextURL);

	}

	public void errorPage(HttpServletRequest req, HttpServletResponse res,
			Exception e, String info) throws ServletException, IOException {
		req.setAttribute("info", info);
		req.setAttribute("Exception", e);
		servletPage(req, res, "/err.jsp");
		// goPage(req, res, "/err.jsp");
	}
	
	public String toGBK(String inputStr) throws Exception{
		
		if(inputStr==null)
			return "";
		else
			return new  String(inputStr.getBytes("ISO8859-1"),"GBK");
	}
	
public String toUTF(String inputStr) throws Exception{
		
		if(inputStr==null)
			return "";
		else
			return new  String(inputStr.getBytes("ISO8859-1"),"utf-8");
	}

}

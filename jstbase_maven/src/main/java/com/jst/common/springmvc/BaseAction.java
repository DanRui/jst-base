package com.jst.common.springmvc;


import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jst.config.ObjectSerializeConfig;
import com.jst.handler.MessageHandler;
import com.jst.common.utils.page.Page;
import java.util.List;
import java.util.Map;

import com.jst.util.EncryptUtil;
import com.jst.util.JsonUtil;
import com.jst.common.hibernate.PropertyFilter;
import com.jst.util.PropertyUtil;
import com.jst.util.StringUtil;

import net.sf.json.JSONObject;

public abstract class BaseAction {
	private static final Log log = LogFactory.getLog(BaseAction.class);
	
	public static final String REDIRECT = "redirect:";

	public static String clientId;

	public static String clientPwd;

	public static String logremark;
	
	public static String sessionInfo;
	
	public static String terminalInfo;

	public BaseAction(){
		clientId = PropertyUtil.getPropertyValue("username");
		clientPwd = PropertyUtil.getPropertyValue("password");
		if(StringUtil.isNotEmpty(clientPwd)){
			try {
				clientPwd = EncryptUtil.encryptMD5(clientPwd);
			} catch (Exception e) {
				log.error("BaseAction init clientpwd md5 is Error:"+e,e);
			}
		}
	}

	public Session getCurrentSession(){
		Subject subject = SecurityUtils.getSubject();
		return subject.getSession();
	}
	
	/**
	 * 将page转为dataGrid中所需数据
	 * @param page
	 * @return
	 */
	public String writerPage(Page pageData)throws Exception{
		JSONObject jsonObject = new JSONObject();
		if (pageData != null && pageData.getResult() != null) {
			jsonObject.put("rows", pageData.getResult());
		}else{
			jsonObject.put("rows", new ArrayList());
		}
		jsonObject.put("pageNo", pageData.getPageNo());
		jsonObject.put("pageSize", pageData.getPageSize());
		jsonObject.put("total", pageData.getTotalCount());
		return jsonObject.toString();
	}
	
	public ObjectSerializeConfig initPage(MessageHandler handler,Page page,List<PropertyFilter> filters) throws Exception{
		ObjectSerializeConfig serializeConfig = new ObjectSerializeConfig();
		serializeConfig.setObjectAlias(Page.class, "page");
		serializeConfig.setObjectAlias(List.class, "filters");
		serializeConfig.setObjectAlias(PropertyFilter.class, "propertyFilter");
		serializeConfig.setExcludeField(Page.class, "result");
		serializeConfig.setRemoveClass(false);
		handler.addHeadParam(page, serializeConfig);
		handler.addHeadParam(filters, serializeConfig);
		return serializeConfig;
	}
	
	/**
	 * 根据web.properties获取页面返回地址 
	 * @param page web.properties中的key值
	 * @return
	 */
	public String getReturnPage(String page){
		return PropertyUtil.getPropertyValue(page);
	}
	
	public String redirectPage(String page){
		return REDIRECT+getReturnPage(page);
	}
	
}

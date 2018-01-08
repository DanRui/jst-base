package com.jst.common.system.aspect.impl;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.model.BaseModel;
import com.jst.common.model.SystemLog;
import com.jst.common.service.ISystemLogService;
import com.jst.common.system.LogConstants;
import com.jst.common.system.aspect.IAspect;
import com.jst.common.utils.ContrastUtil;
import com.jst.platformClient.utils.Constants;
import com.jst.util.DateUtil;
import com.jst.util.JsonUtil;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.hibernate.Session;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

public class ServiceAspect
		implements IAspect, Serializable
{
	private static final Log log = LogFactory.getLog(ServiceAspect.class);
	@Resource(name="systemLogService")
	private ISystemLogService systemLogService;

	public SystemLog systemLog;
	public Date startTime;
	public Date endTime;
	private String remark;
	private static String opera;
	private static Object deleteId;
	private static Object deleteObj;
	private Long startdate;
	private Map<String, Object> queryMap;
	private Map<String, Object> updateMap;
	private Map<Long, Boolean> hasExecute;

	public void doBefore(JoinPoint jp)
	{
		if (null == this.hasExecute) {
			this.hasExecute = new HashMap();
		}
		this.systemLog = new SystemLog();
		this.queryMap = new HashMap();
		this.updateMap = new HashMap();

		this.startdate = Long.valueOf(System.currentTimeMillis());
	}

	public Object doServiceAround(ProceedingJoinPoint pjp)
			throws Throwable
	{
		Long busStartTime = (Long)(LogConstants.get("current_start_time") == null ? Long.valueOf(System.currentTimeMillis()) : LogConstants.get("current_start_time"));
		Object xb = null;
		if (!AopUtils.isAopProxy(pjp.getTarget())) {
			xb = pjp.getTarget();
		} else {
			xb = getProxyTargetObject(pjp.getTarget());
		}
		Thread thread = Thread.currentThread();
		thread.getId();
		String paramStr = "";
		this.startTime = DateUtil.getCurrentDate();
		Date date;
		try
		{
			JSONArray ja = new JSONArray();
			if (null != pjp.getArgs()) {
				for (Object p : pjp.getArgs()) {
					if ((p instanceof Date))
					{
						date = (Date)p;
						String dStr = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
						ja.add(dStr);
					}
					else
					{
						ja.add(p);
					}
				}
			}
			paramStr = ja.toString();
		}
		catch (Exception localException1) {}
		boolean isError = false;
		String errorMsg = "";
		Object o = null;
		Throwable bb = null;
		try
		{
			o = pjp.proceed();
		}
		catch (Throwable e)
		{
			bb = e;
			isError = true;
			errorMsg = getStackTrace(e);
		}
		try
		{
			if ((this.hasExecute.containsKey(Long.valueOf(thread.getId()))) && (((Boolean)this.hasExecute.remove(Long.valueOf(thread.getId()))).booleanValue())) {
				try
				{
					Object mdlPrvg = LogConstants.get("mdlprvg");
					if ((null != mdlPrvg) && (!"QUERY".equals(mdlPrvg.toString())) && (!"VIEW".equals(mdlPrvg.toString())))
					{
						this.systemLog.setUseTime(Long.valueOf(System.currentTimeMillis() - this.startdate.longValue()));
						this.endTime = DateUtil.getCurrentDate();
						this.systemLog.setAppCode(Constants.CURRENT_APPCODE);

						this.systemLog.setMac(LogConstants.get("mac") == null ? null : LogConstants.get("mac").toString());
						this.systemLog.setBusUserTime(System.currentTimeMillis() - busStartTime.longValue());
						this.systemLog.setWebServiceName(LogConstants.get("serviceName") == null ? null : LogConstants.get("serviceName").toString());
						this.systemLog.setWebServiceMethod(LogConstants.get("webservicemethod") == null ? null : LogConstants.get("webservicemethod").toString());
						this.systemLog.setWebSession(LogConstants.get("websessioninfo") == null ? null : LogConstants.get("websessioninfo").toString());
						this.systemLog.setWebTerminal(LogConstants.get("webterminalinfo") == null ? null : LogConstants.get("webterminalinfo").toString());
						this.systemLog.setServiceTerminal(LogConstants.get("currentterminal") == null ? null : LogConstants.get("currentterminal").toString());
						this.systemLog.setServiceSession(LogConstants.get("currentsession") == null ? null : LogConstants.get("currentsession").toString());
						this.systemLog.setWebServiceArgs(LogConstants.get("webserviceargs") == null ? null : LogConstants.get("webserviceargs").toString());
						this.systemLog.setOpeUserName(LogConstants.get("username") == null ? null : LogConstants.get("username").toString());
						this.systemLog.setOpearterType(Integer.valueOf(!isError ? 0 : 1));
						this.systemLog.setErrorMsg(errorMsg);
						this.systemLog.setOpeType(LogConstants.get("mdlprvg") == null ? null : LogConstants.get("mdlprvg").toString());
						this.systemLog.setObjType(LogConstants.get("mdlcode") == null ? null : LogConstants.get("mdlcode").toString());
						this.systemLog.setOpeUserCode(LogConstants.get("usercode") == null ? null : LogConstants.get("usercode").toString());
						this.systemLog.setOpeTime(this.startTime);
						this.systemLog.setOpeIp(LogConstants.get("ipstr") == null ? null : LogConstants.get("ipstr").toString());
						this.systemLog.setServiceArgs(paramStr);
						this.systemLog.setStartTime(this.startTime);
						this.systemLog.setEndTime(this.endTime);
						this.systemLog.setBussRun(opera);
						this.systemLog.setMethodName(pjp.getSignature().getName());
						this.systemLog.setServiceName(xb.getClass().getName());
						this.systemLogService.save(this.systemLog);
					}
				}
				catch (Exception e)
				{
					log.error("ServiceAspect doServiceAround is Error" + e, e);
				}
			} else {
				this.hasExecute.put(Long.valueOf(thread.getId()), Boolean.valueOf(true));
			}
		}
		catch (Exception e)
		{
			log.error("ServiceAspect doServiceAround saveLog is Error:" + e, e);
		}
		if (null != bb) {
			throw bb;
		}
		return o;
	}

	public Object doAround(ProceedingJoinPoint pjp)
			throws Throwable
	{
		BaseDAO bDAO = null;
		if (!AopUtils.isAopProxy(pjp.getTarget()))
		{
			if ((pjp.getTarget() instanceof BaseDAO)) {
				bDAO = (BaseDAO)pjp.getTarget();
			}
		}
		else
		{
			Object j = getProxyTargetObject(pjp.getTarget());
			if ((j instanceof BaseDAO)) {
				bDAO = (BaseDAO)j;
			}
		}
		try
		{
			Object beforeObj = null;
			Object[] a = pjp.getArgs();
			if (null != bDAO)
			{
				if ("update".equals(pjp.getSignature().getName())) {
					for (int i = 0; i < a.length; i++) {
						if ((a[i] instanceof BaseModel))
						{
							String currentClassName = a[i].getClass().getSimpleName();
							Object newObject = ContrastUtil.cloneEntity(a[i]);
							this.updateMap.put(currentClassName, newObject);
							if ((null != this.queryMap) && (this.queryMap.containsKey(currentClassName)))
							{
								opera = ContrastUtil.getChange(this.queryMap.get(currentClassName), newObject);
							}
							else
							{
								Field field = newObject.getClass().getDeclaredField("id");
								field.setAccessible(true);
								Object id = field.get(newObject);
								if (null != id)
								{
									Object oldObject = bDAO.get(id);
									bDAO.getSession().evict(oldObject);
									opera = ContrastUtil.contrast(oldObject, newObject);
								}
							}
						}
					}
				}
				if (("delete".equals(pjp.getSignature().getName())) &&
						(null != a) && (a.length == 1))
				{
					deleteId = a[0];
					deleteObj = bDAO.get(deleteId);
					bDAO.getSession().evict(deleteObj);
					this.remark = JsonUtil.parse(deleteObj).toString();
				}
			}
		}
		catch (Exception e)
		{
			log.error("ServiceAspect dao doAround is Error:" + e, e);
		}
		Object o = pjp.proceed();
		return o;
	}

	private static Object getProxyTargetObject(Object proxy)
			throws Exception
	{
		Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
		h.setAccessible(true);
		AopProxy aopProxy = (AopProxy)h.get(proxy);
		Field advised = aopProxy.getClass().getDeclaredField("advised");
		advised.setAccessible(true);
		return ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();
	}

	private static String getStackTrace(Throwable t)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try
		{
			t.printStackTrace(pw);
			return sw.toString();
		}
		finally
		{
			pw.close();
		}
	}
}

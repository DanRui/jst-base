package com.jst.common.system.aspect.impl;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

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
import com.jst.util.PropertyUtil;

/*@Aspect
@Component*/
/**
 * 用于切入日志
 * @author Administrator
 *
 */
public class ServiceAspect implements IAspect,Serializable{
	
	private static final Log log = LogFactory.getLog(ServiceAspect.class);
	
	/**
	 * 日志记录操作类
	 */
	@Resource(name = "systemLogService")
	private ISystemLogService systemLogService;
	
	/**
	 * 日志记录对象
	 */
	public SystemLog systemLog;
	
	
	/**
	 * 开始时间
	 */
	public Date startTime;
	
	/**
	 * 结束时间
	 */
	public Date endTime;
	
	/**
	 * 日志描述
	 */
	private String remark;
	
	/**
	 * 存储对象改变状态
	 */
	private static String opera;
	
	/**
	 * 删除对象的额ID
	 */
	private static Object deleteId;
	
	/**
	 * 删除对象的集合
	 */
	private static Object deleteObj;
	
	
	/**
	 * 方法开始时的时间戳
	 */
	private Long startdate;
	
	/**
	 * 查询对象，用于对比改变值
	 */
	private Map<String,Object> queryMap;
	
	/**
	 * 修改对象，用于对比改变值
	 */
	private Map<String,Object> updateMap;
	
	/**
	 * 用于防止二次存储及二次调用
	 */
	private Map<Long,Boolean> hasExecute ;

	/**
	 * 定义业务操作类的切入规则
	 */
	/*@Pointcut("execution(* com.jst.test.service.*.*(..))")
	private void aspectMethod() {
		
	}*/
	
	/**
	 * 定义数据库操作类的切入规则
	 */
	/*@Pointcut("execution(* com.jst.test.dao.*.*(..)) or execution(* com.jst.common.hibernate.*.*(..))")
	private void aspectDAOMethod(){
		
	} */
	
	/*@Pointcut("execution(* com.jst.test.webservice.*.*(..))")
	private void aspectWebServiceMethod(){
	}*/

	/**
	 * 前置切入，用于初始化
	 */
	 //声明前置通知  
   // @Before("aspectMethod()")  
    public void doBefore(JoinPoint jp) {
    	systemLog = new SystemLog();
    	queryMap = new HashMap<String,Object>();
    	updateMap = new HashMap<String,Object>();
    	hasExecute = new HashMap<Long, Boolean>();;
		startdate = System.currentTimeMillis();
    }
    
  /*  @Before("aspectWebServiceMethod()")
    public void webServiceBefore(JoinPoint jp){
    	
    }*/
  
    /**
     * 后置切入，用于对方法返回后的操作
     */
    //声明后置通知  
    /*@AfterReturning(pointcut = "aspectMethod()")  
    public void doAfterReturning(JoinPoint jp) {  
    }  */
    
    /**
     * 异常切入，
     * @param e
     */
    //声明例外通知  
    /*@AfterThrowing(pointcut = "aspectMethod()", throwing = "e")  
    public void doAfterThrowing(Exception e) {  
    }
  
    //声明最终通知  
    @After("aspectMethod()")  
    public void doAfter() {  
        System.out.println("最终通知");  
    }  */
    
    /**
     * 针对Service的环绕切入
     * @param pjp
     * @return
     * @throws Throwable
     */
    //@Around("aspectMethod()")  
    public Object doServiceAround(ProceedingJoinPoint pjp) throws Throwable{
    	Long busStartTime = (Long)(LogConstants.get(LogConstants.CURRENT_START_TIME)==null?System.currentTimeMillis():LogConstants.get(LogConstants.CURRENT_START_TIME));
    	Object xb = null;
    	//用于判断是代理类还是实现类，
    	if(!AopUtils.isAopProxy(pjp.getTarget())){
    		xb = pjp.getTarget();
    	}else{
    		xb = getProxyTargetObject(pjp.getTarget());
    	}
    	Thread thread = Thread.currentThread();
    	thread.getId();
    	String paramStr = JsonUtil.parse(pjp.getArgs()).toString();
    	startTime = DateUtil.getCurrentDate();
    	boolean isError = false;
    	String errorMsg = "";
    	Object o = null;
    	Throwable bb = null;
    	try {
			o = pjp.proceed();
		} catch (Throwable e) {
			bb = e;
			isError = true;
			errorMsg = getStackTrace(e);
		}
    	if(hasExecute.containsKey(thread.getId())&&hasExecute.remove(thread.getId())){
	    	
    	}else{
    		/**
    		 * 开始记录日志
    		 */
    		systemLog.setUseTime(System.currentTimeMillis() - startdate);
	     	endTime = DateUtil.getCurrentDate();
	    	systemLog.setAppCode(Constants.CURRENT_APPCODE);
	    	//systemLog.setBussRun(bussRun);
	        systemLog.setBusUserTime(System.currentTimeMillis() - busStartTime);
	        systemLog.setWebServiceName(LogConstants.get(LogConstants.SERVICE_NAME)==null?null:LogConstants.get(LogConstants.SERVICE_NAME).toString());
	        systemLog.setWebServiceMethod(LogConstants.get(LogConstants.WEB_SERVICE_METHOD)==null?null:LogConstants.get(LogConstants.WEB_SERVICE_METHOD).toString());
	        systemLog.setWebSession(LogConstants.get(LogConstants.WEB_SESSION_INFO)==null?null:LogConstants.get(LogConstants.WEB_SESSION_INFO).toString());
	        systemLog.setWebTerminal(LogConstants.get(LogConstants.WEB_TERMINAL_INFO)==null?null:LogConstants.get(LogConstants.WEB_TERMINAL_INFO).toString());
	        systemLog.setServiceTerminal(LogConstants.get(LogConstants.CURRENT_TERMINAL)==null?null:LogConstants.get(LogConstants.CURRENT_TERMINAL).toString());
	        systemLog.setServiceSession(LogConstants.get(LogConstants.CURRENT_SESSION)==null?null:LogConstants.get(LogConstants.CURRENT_SESSION).toString());
	        systemLog.setWebServiceArgs(LogConstants.get(LogConstants.WEB_SERVICE_ARGS)==null?null:LogConstants.get(LogConstants.WEB_SERVICE_ARGS).toString());
	        systemLog.setOpeUserName(LogConstants.get(LogConstants.USER_NAME)==null?null:LogConstants.get(LogConstants.USER_NAME).toString());
	        systemLog.setOpearterType(!isError?0:1);
	        systemLog.setErrorMsg(errorMsg);
	    	systemLog.setOpeType(LogConstants.get(LogConstants.MDL_CODE)==null?null:LogConstants.get(LogConstants.MDL_CODE).toString());
	    	systemLog.setObjType(LogConstants.get(LogConstants.MDL_PRVG)==null?null:LogConstants.get(LogConstants.MDL_PRVG).toString());
	    	systemLog.setOpeUserCode(LogConstants.get(LogConstants.USER_CODE)==null?null:LogConstants.get(LogConstants.USER_CODE).toString());
	    	systemLog.setOpeTime(startTime);
	    	systemLog.setOpeIp(LogConstants.get(LogConstants.IP_STR)==null?null:LogConstants.get(LogConstants.IP_STR).toString());
	    	systemLog.setServiceArgs(paramStr);
	    	systemLog.setStartTime(startTime);
	    	systemLog.setEndTime(endTime);
	    	systemLog.setBussRun(opera);
	    	systemLog.setMethodName(pjp.getSignature().getName());
	    	systemLog.setServiceName(xb.getClass().getName());
	    	Serializable id = systemLogService.save(systemLog);
	    	if(null!=id){
	    		hasExecute.put(thread.getId(), true);
	    	}
    	}
    	if(null!=bb){
    		throw bb;
    	}
    	//t.commit();
    	return o;
    }
    
  /*  @Around("aspectWebServiceMethod()")
    public Object doWebServiceAround(ProceedingJoinPoint pjp) throws Throwable{
    	
    	Object o = pjp.proceed();
    	systemLogService.save(systemLog);
    	return o;
    }*/
  
    /**
     * 针对与DAO层的方法切入
     * @param pjp
     * @return
     * @throws Throwable
     */
    //声明环绕通知  
   // @Around("aspectDAOMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    	BaseDAO bDAO = (BaseDAO)pjp.getTarget();
  	  Object beforeObj = null;
    	Object [] a = pjp.getArgs();
  	//systemLog.setMethodName(pjp.getSignature().getName());
  	
      if("update".equals(pjp.getSignature().getName())){
      
      	for (int i = 0; i < a.length; i++) {
				if(a[i] instanceof BaseModel){
					String currentClassName = a[i].getClass().getSimpleName();
					Object newObject = ContrastUtil.cloneEntity(a[i]);
					updateMap.put(currentClassName, newObject);
					
					if(null!=queryMap&&queryMap.containsKey(currentClassName)){
						opera = ContrastUtil.getChange(queryMap.get(currentClassName), newObject);
					}else{
						Field field = newObject.getClass().getDeclaredField("id");
						field.setAccessible(true);
						Object id = field.get(newObject);
						if(null!=id){
							Object oldObject = bDAO.get(id);
							bDAO.getSession().evict(oldObject);
							opera = ContrastUtil.contrast(oldObject, newObject);
						}
					}
				}
			}
      }
      if("delete".equals(pjp.getSignature().getName())){
      	if(null!=a && a.length==1){
      		deleteId = a[0];
      		deleteObj = bDAO.get(deleteId);
				bDAO.getSession().evict(deleteObj);
				remark = JsonUtil.parse(deleteObj).toString();
				//opera = ContrastUtil.contrast(oldObject, newObject);
      	}
      }
      
      Object o = pjp.proceed();  
        return o;  
    }  
    
    
    /**
     * 获取代理类中的实现类
     * @param proxy
     * @return
     * @throws Exception
     */
    private static Object getProxyTargetObject(Object proxy) throws Exception {  
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");  
        h.setAccessible(true);  
        AopProxy aopProxy = (AopProxy) h.get(proxy);  
        Field advised = aopProxy.getClass().getDeclaredField("advised");  
        advised.setAccessible(true);  
        return  ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();  
    }  
    
    /**
     * 获取异常值栈中的异常信息
     * @param t
     * @return
     */
    private static String getStackTrace(Throwable t)
    {
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    try{
		    t.printStackTrace(pw);
		    return sw.toString();
	    } finally {
	    	pw.close();
	    }
    }
	
}

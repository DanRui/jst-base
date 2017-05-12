package com.jst.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jst.common.model.BaseModel;

@Entity
@Table(name = "T_OPE_LOG")
public class SystemLog extends BaseModel implements Serializable{
	
	
	private Long logId;				//序号
	
	private String appCode;			//应用系统代码
	
	private String opeType;			//操作类型,指：添加、删除、修改等
	
	private String opeTypeName;		//操作类型名称
	
	private String objTypeName;		//操作对象名称
	
	private String objType;			//操作对象代码,实际上也是模块代码

	private String objId;			//对象序号
	
	private String opeUserCode;		//操作人员代码
	
	private String opeUserName;		//操作人员姓名
	
	private Date opeTime;			//操作时间
	
	private String opeIp;			//操作IP
	
	private String remark;			//备注
	
	private String invokePage;		//页面本身
	
	private String triggerPage;		//页面本身
	
	private String mac;				//物理地址
	
	private long busUserTime;		//业务所用时间 ，从WebService拦截器开始，调用业务操作Service结束进行计算
	
	private String webServiceName;		//调用的WebService名称
	
	private String webServiceMethod;	//调用的WebService Method名称
	
	private String webSession;			//Web端的session信息
	
	private String webTerminal;			//Web端的terminal信息
	
	private String serviceTerminal;		//调用的Webservice Terminal信息
	
	private String serviceSession;		//调用的WebService Session信息
	
	private Date startTime;			//调用服务开始时间
	
	private Date endTime;			//调用服务结束时间，计算方式从进入WebService拦截器开始，调用业务操作Service返回结束
	
	private String serviceName;		//所调用的业务实现类名称
	
	private String methodName;		//所调用的业务实现类中的方法名称
	
	private Long useTime;		//从调用业务操作Service开始，到执行完成进行计算
	
	private String webServiceArgs;	//调用WebService所传入的参数
	
	private String serviceArgs;		//调用业务操作类的传入的参数
	
	private String bussRun;			//业务运行过程中所产生的数据库操作记录
	
	private Integer opearterType;	//系统运行状态，0，正常，1.异常
	
	private String errorMsg;		//异常信息

	@SequenceGenerator(name = "generator",sequenceName = "SEQ_SYSTEM_LOG_ID",allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "LOG_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	@Column(name = "APP_CODE")
	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	@Column(name = "OBJ_TYPE")
	public String getOpeType() {
		return opeType;
	}

	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}

	@Column(name = "OPE_TYPE")
	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	@Column(name = "OBJ_ID")
	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	@Column(name = "OPE_USER_CODE")
	public String getOpeUserCode() {
		return opeUserCode;
	}

	public void setOpeUserCode(String opeUserCode) {
		this.opeUserCode = opeUserCode;
	}

	@Column(name = "OPE_USER_NAME")
	public String getOpeUserName() {
		return opeUserName;
	}

	public void setOpeUserName(String opeUserName) {
		this.opeUserName = opeUserName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPE_TIME")
	public Date getOpeTime() {
		return opeTime;
	}

	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}

	@Column(name = "OPE_IP")
	public String getOpeIp() {
		return opeIp;
	}

	public void setOpeIp(String opeIp) {
		this.opeIp = opeIp;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "INVOKE_PAGE")
	public String getInvokePage() {
		return invokePage;
	}

	public void setInvokePage(String invokePage) {
		this.invokePage = invokePage;
	}

	@Column(name = "TRIGGER_PAGE")
	public String getTriggerPage() {
		return triggerPage;
	}

	public void setTriggerPage(String triggerPage) {
		this.triggerPage = triggerPage;
	}

	@Column(name = "MAC")
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Column(name = "BUSINESS_USE_TIME")
	public long getBusUserTime() {
		return busUserTime;
	}

	public void setBusUserTime(long busUserTime) {
		this.busUserTime = busUserTime;
	}

	@Column(name = "WEBSERVICE_NAME")
	public String getWebServiceName() {
		return webServiceName;
	}

	public void setWebServiceName(String webServiceName) {
		this.webServiceName = webServiceName;
	}

	@Column(name = "WEBSERVICE_METHOD")
	public String getWebServiceMethod() {
		return webServiceMethod;
	}

	public void setWebServiceMethod(String webServiceMethod) {
		this.webServiceMethod = webServiceMethod;
	}

	@Column(name = "WEB_SESSION")
	public String getWebSession() {
		return webSession;
	}

	public void setWebSession(String webSession) {
		this.webSession = webSession;
	}

	@Column(name = "WEB_TERMINAL")
	public String getWebTerminal() {
		return webTerminal;
	}

	public void setWebTerminal(String webTerminal) {
		this.webTerminal = webTerminal;
	}

	@Column(name = "SERVICE_TERMINAL")
	public String getServiceTerminal() {
		return serviceTerminal;
	}

	public void setServiceTerminal(String serviceTerminal) {
		this.serviceTerminal = serviceTerminal;
	}

	@Column(name = "SERVICE_SESSION")
	public String getServiceSession() {
		return serviceSession;
	}

	public void setServiceSession(String serviceSession) {
		this.serviceSession = serviceSession;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "SERVICE_NAME")
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Column(name = "METHOD_NAME")
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Column(name = "USE_TIME")
	public Long getUseTime() {
		return useTime;
	}

	public void setUseTime(Long useTime) {
		this.useTime = useTime;
	}

	@Column(name = "WEBSERVICE_ARGS")
	public String getWebServiceArgs() {
		return webServiceArgs;
	}

	public void setWebServiceArgs(String webServiceArgs) {
		this.webServiceArgs = webServiceArgs;
	}

	@Column(name = "SERVICE_ARGS")
	public String getServiceArgs() {
		return serviceArgs;
	}

	public void setServiceArgs(String serviceArgs) {
		this.serviceArgs = serviceArgs;
	}

	@Column(name = "BUSINESS_RUN")
	public String getBussRun() {
		return bussRun;
	}

	public void setBussRun(String bussRun) {
		this.bussRun = bussRun;
	}

	@Column(name = "OPERATER_TYPE")
	public Integer getOpearterType() {
		return opearterType;
	}

	public void setOpearterType(Integer opearterType) {
		this.opearterType = opearterType;
	}
	
	@Column(name = "ERROR_MSG")
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Column(name = "OPE_TYPE_NAME")
	public String getOpeTypeName() {
		return opeTypeName;
	}

	public void setOpeTypeName(String opeTypeName) {
		this.opeTypeName = opeTypeName;
	}

	@Column(name = "OBJ_TYPE_NAME")
	public String getObjTypeName() {
		return objTypeName;
	}

	public void setObjTypeName(String objTypeName) {
		this.objTypeName = objTypeName;
	}
		
	
	
	
}

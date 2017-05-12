package com.jst.common.test.person;

import java.util.Date;


import com.jst.common.model.BaseModel;

/**
 * Person entity. @author MyEclipse Persistence Tools
 */

public class Person extends BaseModel implements java.io.Serializable {

	// Fields

	private Integer id;
	private String idCard;
	private String personName;
	private String sex;
	private Date birthday;
	private String address;
	private String residenceNo;
	private String residenceAddress;
	private String personTel;
	private String personMobile;
	private String email;
	private String qq;
	private String relativesName;
	private String relativesTel;
	private byte[] photo;
	private Date entryTime;
	private String deptName;
	private String postName;
	private Integer salary;
	private String workTel;
	private String workMobile;
	private Date contractBeginTime;
	private Date contractEndTime;
	private String remark;
	private String schoolCode;
	private String schoolName;
	private String state;
	private String inputUserName;
	private Date inputTime;
	private String updateUserName;
	private Date updateTime;

	// Constructors

	/** default constructor */
	public Person() {
	}

	/** minimal constructor */
	public Person(String idCard, String personName, String schoolCode,
			String state) {
		this.idCard = idCard;
		this.personName = personName;
		this.schoolCode = schoolCode;
		this.state = state;
	}

	/** full constructor */
	public Person(String idCard, String personName, String sex, Date birthday,
			String address, String residenceNo, String residenceAddress,
			String personTel, String personMobile, String email, String qq,
			String relativesName, String relativesTel, byte[] photo,
			Date entryTime, String deptName, String postName, Integer salary,
			String workTel, String workMobile, Date contractBeginTime,
			Date contractEndTime, String remark, String schoolCode,
			String schoolName, String state, String inputUserName,
			Date inputTime, String updateUserName, Date updateTime) {
		this.idCard = idCard;
		this.personName = personName;
		this.sex = sex;
		this.birthday = birthday;
		this.address = address;
		this.residenceNo = residenceNo;
		this.residenceAddress = residenceAddress;
		this.personTel = personTel;
		this.personMobile = personMobile;
		this.email = email;
		this.qq = qq;
		this.relativesName = relativesName;
		this.relativesTel = relativesTel;
		this.photo = photo;
		this.entryTime = entryTime;
		this.deptName = deptName;
		this.postName = postName;
		this.salary = salary;
		this.workTel = workTel;
		this.workMobile = workMobile;
		this.contractBeginTime = contractBeginTime;
		this.contractEndTime = contractEndTime;
		this.remark = remark;
		this.schoolCode = schoolCode;
		this.schoolName = schoolName;
		this.state = state;
		this.inputUserName = inputUserName;
		this.inputTime = inputTime;
		this.updateUserName = updateUserName;
		this.updateTime = updateTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPersonName() {
		return this.personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getResidenceNo() {
		return this.residenceNo;
	}

	public void setResidenceNo(String residenceNo) {
		this.residenceNo = residenceNo;
	}

	public String getResidenceAddress() {
		return this.residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public String getPersonTel() {
		return this.personTel;
	}

	public void setPersonTel(String personTel) {
		this.personTel = personTel;
	}

	public String getPersonMobile() {
		return this.personMobile;
	}

	public void setPersonMobile(String personMobile) {
		this.personMobile = personMobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getRelativesName() {
		return this.relativesName;
	}

	public void setRelativesName(String relativesName) {
		this.relativesName = relativesName;
	}

	public String getRelativesTel() {
		return this.relativesTel;
	}

	public void setRelativesTel(String relativesTel) {
		this.relativesTel = relativesTel;
	}

	public byte[] getPhoto() {
		return this.photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public Date getEntryTime() {
		return this.entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPostName() {
		return this.postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public Integer getSalary() {
		return this.salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getWorkTel() {
		return this.workTel;
	}

	public void setWorkTel(String workTel) {
		this.workTel = workTel;
	}

	public String getWorkMobile() {
		return this.workMobile;
	}

	public void setWorkMobile(String workMobile) {
		this.workMobile = workMobile;
	}

	public Date getContractBeginTime() {
		return this.contractBeginTime;
	}

	public void setContractBeginTime(Date contractBeginTime) {
		this.contractBeginTime = contractBeginTime;
	}

	public Date getContractEndTime() {
		return this.contractEndTime;
	}

	public void setContractEndTime(Date contractEndTime) {
		this.contractEndTime = contractEndTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSchoolCode() {
		return this.schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getSchoolName() {
		return this.schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getInputUserName() {
		return this.inputUserName;
	}

	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public String getUpdateUserName() {
		return this.updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
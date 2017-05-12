package com.jst.common.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "T_DICT_TYPE" )
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="com.jst.common.model.DictType")
public class DictType implements Serializable {

	private static final long serialVersionUID = 1841914404389820660L;
	
	private String typeCode;
	private String typeName;
	private char state;
	private Integer sortId;
	private String remark;
	private String remark1;
	private String remark2;
	
	private Set<SysDict> sysDicts;

	public DictType() {
	}

	public DictType(String typeCode, String typeName, char state) {
		this.typeCode = typeCode;
		this.typeName = typeName;
		this.state = state;
	}

	public DictType(String typeCode, String typeName, char state,
			Integer sortId, String remark) {
		this.typeCode = typeCode;
		this.typeName = typeName;
		this.state = state;
		this.sortId = sortId;
		this.remark = remark;
	}

	@Id
	@Column(name = "TYPE_CODE", unique = true, nullable = false, length = 30)
	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "TYPE_NAME", nullable = false, length = 50)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "STATE", nullable = false, length = 1)
	public char getState() {
		return this.state;
	}

	public void setState(char state) {
		this.state = state;
	}

	@Column(name = "SORT_ID", precision = 22, scale = 0)
	public Integer getSortId() {
		return this.sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="DICT_TYPE", referencedColumnName="TYPE_CODE")
	@Where(clause="state = '1'")
	@OrderBy(clause="sortId asc")
	@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="com.jst.common.model.DictType.sysDicts")
	public Set<SysDict> getSysDicts() {
		return sysDicts;
	}

	public void setSysDicts(Set<SysDict> sysDicts) {
		this.sysDicts = sysDicts;
	}

	@Column(name = "REMARK1")
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@Column(name = "REMARK2")
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	
	
}

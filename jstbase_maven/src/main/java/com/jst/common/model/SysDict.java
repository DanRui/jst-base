package com.jst.common.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "T_SYS_DICT")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="com.jst.common.model.SysDict")
public class SysDict implements Serializable {

	private static final long serialVersionUID = 8402814146952292780L;
	
	private Integer dictId;
	private String dictType;
	private String dictCode;
	private String dictName;
	private String dictValue;
	private char state;
	private Integer sortId;
	private String remark;

	public SysDict() {
	}

	public SysDict(Integer dictId, String dictType, String dictCode,
			String dictName, String dictValue, char state) {
		this.dictId = dictId;
		this.dictType = dictType;
		this.dictCode = dictCode;
		this.dictName = dictName;
		this.dictValue = dictValue;
		this.state = state;
	}

	public SysDict(Integer dictId, String dictType, String dictCode,
			String dictName, String dictValue, char state, Integer sortId,
			String remark) {
		this.dictId = dictId;
		this.dictType = dictType;
		this.dictCode = dictCode;
		this.dictName = dictName;
		this.dictValue = dictValue;
		this.state = state;
		this.sortId = sortId;
		this.remark = remark;
	}

	@Id
	@Column(name = "DICT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getDictId() {
		return this.dictId;
	}

	public void setDictId(Integer dictId) {
		this.dictId = dictId;
	}

	@Column(name = "DICT_TYPE", nullable = false, length = 30)
	public String getDictType() {
		return this.dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	@Column(name = "DICT_CODE", nullable = false, length = 50)
	public String getDictCode() {
		return this.dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	@Column(name = "DICT_NAME", nullable = false, length = 500)
	public String getDictName() {
		return this.dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	@Column(name = "DICT_VALUE", nullable = false, length = 30)
	public String getDictValue() {
		return this.dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
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

}

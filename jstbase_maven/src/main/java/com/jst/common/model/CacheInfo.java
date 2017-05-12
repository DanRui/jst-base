package com.jst.common.model;


import java.io.Serializable;

public class CacheInfo implements Serializable {

	private static final long serialVersionUID = -4287241523387487837L;

	private String cacheObjName;
	private String entityName;
	private Class<?> entityClass;
	private String regionName;
	private String cacheStrategy;
	private long elementCountInMemory;
	private long elementCountOnDisk;
	private long elementSizeInMemory;

	public String getCacheObjName() {
		return cacheObjName;
	}

	public void setCacheObjName(String cacheObjName) {
		this.cacheObjName = cacheObjName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCacheStrategy() {
		return cacheStrategy;
	}

	public void setCacheStrategy(String cacheStrategy) {
		this.cacheStrategy = cacheStrategy;
	}

	public long getElementCountInMemory() {
		return elementCountInMemory;
	}

	public void setElementCountInMemory(long elementCountInMemory) {
		this.elementCountInMemory = elementCountInMemory;
	}

	public long getElementCountOnDisk() {
		return elementCountOnDisk;
	}

	public void setElementCountOnDisk(long elementCountOnDisk) {
		this.elementCountOnDisk = elementCountOnDisk;
	}

	public long getElementSizeInMemory() {
		return elementSizeInMemory;
	}

	public void setElementSizeInMemory(long elementSizeInMemory) {
		this.elementSizeInMemory = elementSizeInMemory;
	}

}

package com.jst.common.service;

import java.io.Serializable;
import java.util.List;

import com.jst.common.model.CacheInfo;
import com.jst.common.model.DictType;
import com.jst.common.model.Menu;
import com.jst.common.model.SysDict;
import com.jst.platformClient.entity.UserPrvg;

/**
 * 
 * <p>
 * Title: CacheService.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author lee
 * @date 2015年3月5日
 * @version 1.0
 */
public interface CacheService {

	public List<DictType> getDictType();

	public List<SysDict> getSysDict();

	public List<Menu> getMenuListByUserPrvg(List<UserPrvg> userPrvgList);

	public DictType getDictTypeByTypeCode(String typeCode);

	public List<SysDict> getSysDictListByDictType(String typeCode);

	public SysDict getSysDictByDictTypeAndCode(String typeCode, String dictCode);

	public SysDict getSysDictByDictTypeAndValue(String typeCode, String dictValue);

	public List<CacheInfo> getCacheInfo() throws Exception;

	public void synchronizeEntity(String entityClassName, Serializable identifier);

	public void synchronizeEntity(Class<?> entityClass, Serializable identifier);

	public void evict(String... cacheObjNames) throws Exception;

	public void evictAll();

	
	public List<Menu> getMenu();
	
}

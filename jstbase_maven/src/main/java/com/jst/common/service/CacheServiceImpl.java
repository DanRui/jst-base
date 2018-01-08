package com.jst.common.service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.jst.common.utils.ConvertRedisUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cache.internal.StandardQueryCache;
import org.hibernate.cache.spi.UpdateTimestampsCache;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jst.common.dao.IDictTypeDAO;
import com.jst.common.dao.IMenuDAO;
import com.jst.common.dao.ISysDictDAO;
import com.jst.common.model.CacheInfo;
import com.jst.common.model.DictType;
import com.jst.common.model.Menu;
import com.jst.common.model.SysDict;
import com.jst.common.utils.ConvertJsonUtil;
import com.jst.common.utils.RedisUtil;
import com.jst.config.ObjectSerializeConfig;
import com.jst.platformClient.entity.UserPrvg;
import com.jst.serializer.ObjectSerializer;
import com.jst.type.DataType;
import com.jst.util.JsonUtil;
import com.jst.util.StringUtil;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import redis.clients.jedis.Jedis;

@Service("cacheService")
@SuppressWarnings({ "unchecked", "unused" })
public class CacheServiceImpl implements CacheService {
	
	private static final Log log = LogFactory.getLog(CacheServiceImpl.class);

	private static final String ENTITY_PACKAGE_NAME = "model";
	private static final String ENTITY_CLASS_PATH_PREFIX = "com.jst.common.model";

	private static final String STANDARD_QUERY_CACHE = StandardQueryCache.class.getName();
	private static final String UPDATE_TIMESTAMPS_CACHE = UpdateTimestampsCache.class.getName();
	
	private static final String SYSTEM_DICTIONARY_DCITTYPE = "SYSTEM_DICTIONARY_DICTTYPE";
	
	private static final String SYSTEM_DICTIONARY_SYSDICT = "SYSTEM_DICTIONARY_SYSDICT";
	
	public static final String SYSTEM_MENU = "SYSTEM_MENU";

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private IDictTypeDAO dictTypeDao;

	@Autowired
	private ISysDictDAO sysDictDao;

	@Autowired
	private IMenuDAO menuDao;

	public List<DictType> getDictType() {
		log.debug("CacheServiceImpl getDictType start");
		ObjectSerializeConfig config = new ObjectSerializeConfig();
		config.setObjectAlias(List.class, "list");
		config.setObjectAlias(DictType.class, "dict_type");
		config.setObjectAlias(Set.class, "set_list");
		config.setObjectAlias(HashSet.class, "hash_list");
		config.setObjectAlias(SysDict.class, "sys_dict");
		try {
			String key = "DICT_TYPE_LIST";
			if(StringUtil.isNotEmpty(RedisUtil.getValue(SYSTEM_DICTIONARY_DCITTYPE,key))){
				String dictList = RedisUtil.getValue(SYSTEM_DICTIONARY_DCITTYPE,key);
				Object c=  ConvertJsonUtil.getInstance(config).deserialize(dictList);
				if(null!=c){
					return (List<DictType>)((List)c).get(0);
				}
			}else{
				log.debug("redis db connect is error");
			}
		} catch (Exception e) {
			log.error("CacheServiceImpl getDictType is Error:"+e,e);
		}
		
		//dictTypeDao.setCacheQueries(true);
		// dictTypeDao.setQueryCacheRegion(DictType.class.getName());

		List<DictType> dictTypeList = dictTypeDao.find("from DictType where state = '1' order by sortId");
		log.debug("CacheServiceImpl getDcitType is end");
		return dictTypeList;
	}

	public List<SysDict> getSysDict() {
		log.debug("CacheServiceImpl getSysDict start");
		//sysDictDao.setCacheQueries(true);
		// sysDictDao.setQueryCacheRegion(SysDict.class.getName());

		List<SysDict> sysDictList = sysDictDao.find("from SysDict where state = '1' order by sortId");
		//sysDictDao.setCacheQueries(false);

		log.debug("CacheServiceImpl getSysDict end");
		return sysDictList;
	}

	public DictType getDictTypeByTypeCode(String typeCode) {
		DictType dictType = null;

		List<DictType> dictTypeList = getDictType();

		for (int i = 0; i < dictTypeList.size(); i++) {
			if (typeCode.equals(dictTypeList.get(i).getTypeCode())) {
				dictType = dictTypeList.get(i);

				break;
			}
		}

		return dictType;
	}

	public List<SysDict> getSysDictListByDictType(String typeCode) {
		return new ArrayList<SysDict>(getDictTypeByTypeCode(typeCode).getSysDicts());
	}

	public SysDict getSysDictByDictTypeAndCode(String typeCode, String dictCode) {
		SysDict sysDict = null;

		List<SysDict> sysDictList = getSysDictListByDictType(typeCode);

		for (int i = 0; i < sysDictList.size(); i++) {
			if (dictCode.equals(sysDictList.get(i).getDictCode())) {
				sysDict = sysDictList.get(i);

				break;
			}
		}

		return sysDict;
	}

	public SysDict getSysDictByDictTypeAndValue(String typeCode, String dictValue) {
		SysDict sysDict = null;

		List<SysDict> sysDictList = getSysDictListByDictType(typeCode);

		for (int i = 0; i < sysDictList.size(); i++) {
			if (dictValue.equals(sysDictList.get(i).getDictValue())) {
				sysDict = sysDictList.get(i);

				break;
			}
		}

		return sysDict;
	}

	public List<CacheInfo> getCacheInfo() throws Exception {
		List<CacheInfo> cacheInfoList = new ArrayList<CacheInfo>();

		/* hibernate */

		String[] regionNames = sessionFactory.getStatistics().getSecondLevelCacheRegionNames();

		for (String regionName : regionNames) {
			SecondLevelCacheStatistics cacheStatistics = sessionFactory.getStatistics().getSecondLevelCacheStatistics(regionName);

			CacheInfo cacheInfo = new CacheInfo();

			cacheInfo.setCacheObjName(getCacheObjName(regionName));
			cacheInfo.setEntityName(getEntityName(regionName));
			cacheInfo.setEntityClass(Class.forName(getEntityClassName(regionName)));
			cacheInfo.setRegionName(regionName);
			cacheInfo.setCacheStrategy(regionName);
			cacheInfo.setElementCountInMemory(cacheStatistics.getElementCountInMemory());
			cacheInfo.setElementCountOnDisk(cacheStatistics.getElementCountOnDisk());
			cacheInfo.setElementSizeInMemory(cacheStatistics.getSizeInMemory());

			cacheInfoList.add(cacheInfo);
		}

		return cacheInfoList;
	}

	public void synchronizeEntity(String entityClassName, Serializable identifier) {
		evictEntity(entityClassName, identifier);
	}

	public void synchronizeEntity(Class<?> entityClass, Serializable identifier) {
		synchronizeEntity(entityClass.getName(), identifier);
	}

	public void evict(String... cacheObjNames) throws Exception {
		for (String cacheObjName : cacheObjNames) {
			CacheInfo cacheInfo = getCacheInfo(cacheObjName);

			Class<?> entityClass = cacheInfo.getEntityClass();

			String regionName = cacheInfo.getRegionName();

			if (isDefaultRegion(regionName)) {
				evictDefaultQueryRegion();
			} else if (isCollectionRegion(regionName)) {
				evictCollectionRegion(regionName);
			} else {
				evictQueryRegion(regionName);

				if (!isEvicted(regionName)) {
					evictEntityRegion(entityClass);
				}
			}
		}
	}

	public void evictAll() {
		evictDefaultQueryRegion();
		evictEntityRegions();
		evictCollectionRegions();
		evictQueryRegions();
	}

	private boolean isHibernateCache(String name) {
		if (StringUtil.isEmpty(name)) {
			return false;
		}

		String[] regionNames = sessionFactory.getStatistics().getSecondLevelCacheRegionNames();

		for (String regionName : regionNames) {
			if (name.equals(regionName) || regionName.indexOf(name) > -1) {
				return true;
			}
		}

		return false;
	}

	private boolean isDefaultRegion(String regionName) {
		return StringUtil.isEmpty(regionName) ? false : STANDARD_QUERY_CACHE.equalsIgnoreCase(regionName) || UPDATE_TIMESTAMPS_CACHE.equalsIgnoreCase(regionName);
	}

	private boolean isCollectionRegion(String regionName) {
		return StringUtil.isEmpty(regionName) ? false : regionName.indexOf(ENTITY_PACKAGE_NAME) != regionName.lastIndexOf(".") - ENTITY_PACKAGE_NAME.length();
	}

	private boolean isEvicted(String regionName) {
		SecondLevelCacheStatistics cacheStatistics = getCacheStatistics(regionName);

		return StringUtil.isEmpty(regionName) ? false : cacheStatistics.getElementCountInMemory() == 0;
	}

	private SecondLevelCacheStatistics getCacheStatistics(String regionName) {
		return null == regionName ? null : sessionFactory.getStatistics().getSecondLevelCacheStatistics(regionName);
	}

	private String getCacheObjName(String regionName) {
		return StringUtil.isEmpty(regionName) ? null : regionName.substring(regionName.lastIndexOf(".") + 1);
	}

	private String getEntityName(String regionName) {
		if (StringUtil.isEmpty(regionName)) {
			return null;
		}

		String entityName = null;

		if (isCollectionRegion(regionName) && !isDefaultRegion(regionName)) {
			String collectionName = regionName.substring(regionName.lastIndexOf(".") + 1);
			entityName = StringUtil.firstLetterToUpperCase(collectionName.substring(0, collectionName.length() - 1));
		} else {
			entityName = regionName.substring(regionName.lastIndexOf(".") + 1);
		}

		return entityName;
	}

	private String getEntityClassName(String regionName) {
		if (StringUtil.isEmpty(regionName)) {
			return null;
		}

		String entityClassName = null;

		if (isCollectionRegion(regionName) && !isDefaultRegion(regionName)) {
			entityClassName = ENTITY_CLASS_PATH_PREFIX + "." + getEntityName(regionName);
		} else {
			entityClassName = regionName;
		}

		return entityClassName;
	}

	// private String getCollectionRoleName(String regionName){
	// return !isCollectionRegion(regionName) ? null : regionName.substring(regionName.indexOf(ENTITY_PACKAGE_NAME) + ENTITY_PACKAGE_NAME.length() + 1);
	// }

	private CacheInfo getCacheInfo(String cacheObjName) throws ClassNotFoundException {
		String[] regionNames = null;

		CacheInfo cacheInfo = null;

		regionNames = sessionFactory.getStatistics().getSecondLevelCacheRegionNames();

		for (String regionName : regionNames) {
			if (regionName.indexOf(cacheObjName) > -1) {
				cacheInfo = new CacheInfo();
				cacheInfo.setCacheObjName(cacheObjName);
				cacheInfo.setEntityName(getEntityName(regionName));
				cacheInfo.setEntityClass(Class.forName(getEntityClassName(regionName)));
				cacheInfo.setRegionName(regionName);
				cacheInfo.setCacheStrategy(regionName);

				break;
			}
		}

		return cacheInfo;
	}

	private void evictEntity(String entityClassName, Serializable identifier) {
		sessionFactory.getCache().evictEntity(entityClassName, identifier);
	}

	private void evictEntity(Class<?> entityClass, Serializable identifier) {
		evictEntity(entityClass.getName(), identifier);
	}

	private void evictEntityRegion(String entityClassName) {
		sessionFactory.getCache().evictEntityRegion(entityClassName);
	}

	private void evictEntityRegion(Class<?> entityClass) {
		evictEntityRegion(entityClass.getName());
	}

	private void evictEntityRegions() {
		sessionFactory.getCache().evictEntityRegions();
	}

	private void evictCollection(String role, Serializable ownerIdentifier) {
		sessionFactory.getCache().evictCollection(role, ownerIdentifier);
	}

	private void evictCollectionRegion(String role) {
		sessionFactory.getCache().evictCollectionRegion(role);
	}

	private void evictCollectionRegions() {
		sessionFactory.getCache().evictCollectionRegions();
	}

	private void evictDefaultQueryRegion() {
		sessionFactory.getCache().evictDefaultQueryRegion();
	}

	private void evictQueryRegion(String... regionNames) {
		for (String regionName : regionNames) {
			sessionFactory.getCache().evictQueryRegion(regionName);
		}
	}

	private void evictQueryRegions() {
		sessionFactory.getCache().evictQueryRegions();
	}

	public List<Menu> getMenu() {
		log.debug("CacheServiceImpl getMenu  start");

		String state = null;
		try {
			String sysDictStr = RedisUtil.getDictNameByDictValue("MENU_STATE", "VALID");
			if(StringUtil.isNotEmpty(sysDictStr)){
				state = ConvertRedisUtil.convertDictType(sysDictStr).getDictCode();
			}
		} catch (Exception e) {
			log.error("CacheServiceImpl getMenu error:"+e, e);
		}
	
//		String state = getSysDictByDictTypeAndCode("STATE", "VALID").getDictValue();

		if (StringUtil.isEmpty(state)) {
			state = getSysDictByDictTypeAndValue("MENU_STATE", "VALID").getDictCode();
		}

		menuDao.setCacheQueries(true);
		// menuDao.setQueryCacheRegion(Menu.class.getName());

		List<Menu> menuList = menuDao.find("from Menu where state = '" + state + "' order by to_number(menuCode), sortId");
		//menuDao.setCacheQueries(false);
		log.debug("CacheServiceImpl getMenu end");
		return menuList;
	}

	public List<Menu> getMenuListByUserPrvg(List<UserPrvg> userPrvgList) {
		List<Menu> menuList = getMenu();

		boolean havePrvg = false;

		for (int i = menuList.size() - 1; i >= 0; i--) {
			Menu menu = menuList.get(i);

			if ("1".equals(menu.getMenuLevel())) {
				continue;
			}

			String mdlCode = menu.getMdlCode();

			havePrvg = false;

			for (UserPrvg userPrvg : userPrvgList) {
				if (mdlCode.equals(userPrvg.getMdlCode())) {
					havePrvg = true;
					break;
				}
			}

			if (!havePrvg) {
				menuList.remove(i);
			}
		}

		for (int i = menuList.size() - 1; i >= 0; i--) {
			Menu menu = menuList.get(i);

			String menuLevel = menu.getMenuLevel();

			if ("2".equals(menuLevel)) {
				continue;
			}

			String menuCode = menu.getMenuCode();
			boolean haveChildren = false;

			for (Menu m : menuList) {
				if ("1".equals(m.getMenuLevel())) {
					continue;
				}

				String parentMenuCode = m.getParentMenuCode();

				if (parentMenuCode.equals(menuCode)) {
					haveChildren = true;
					break;
				}
			}

			if (!haveChildren) {
				menuList.remove(i);
			}
		}

		return menuList;
	}
}

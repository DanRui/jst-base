package com.jst.common.service;


import java.io.Serializable;
import java.util.List;
import com.jst.common.model.BaseModel;
import javax.servlet.http.HttpSession;
import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.utils.page.Page;

/**
 * 基础服务接口
 * @author 刘美林 2012-04-25
 * 
 */


public interface BaseService {
	
	/**
	 * 新增对象
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public Serializable save(Object model) throws Exception;
	
	/**
	 * 修改对象 此方法必须保证 baseModel的默认值为null的字段不会进行更新，其它字段默认不为null时会进行更新
	 * @param baseModel
	 * @return
	 * @throws Exception
	 */
	public BaseModel update(Serializable id ,BaseModel baseModel) throws Exception;
	
	/**
	 * 修改对象
	 * @param baseModel
	 * @return
	 * @throws Exception
	 */
	public BaseModel update(BaseModel baseModel) throws Exception;
	
	/**
	 * 删除，此操作进行数据物理删除，如许逻辑删除请自行实现
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Serializable delete(Serializable id) throws Exception;
	
	/**
	 * 获取对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BaseModel get(Serializable id) throws Exception;	
	
	
	/**
	 * 获取所有对象List (数据量过多时请勿使用）
	 * @return
	 * @throws Exception
	 */	
	public List getAllList() throws Exception;
	
	/**
	 * 获取所有对象数量
	 * @return
	 * @throws Exception
	 */	
	public long getAllListCounter() throws Exception;
	
	/**
	 * 根据多个属性查找对象
	 * @param propertyNames
	 * @param values
	 * @param pSqlWhere
	 * @param orderByStr
	 * @return
	 * @throws Exception
	 */
	
	public List getListByPropertys(String[] propertyNames,
			Object[] values, String pSqlWhere ,String orderByStr) throws Exception;
	
	/**
	 * 根据一个属性查找对象
	 * @param propertyName
	 * @param value
	 * @param pSqlWhere
	 * @return
	 * @throws Exception
	 */
	public List getListByPorperty(String propertyName,
			Object value, String pSqlWhere) throws Exception;
	
	/**
	 * 获取对象Page (保留)
	 * @param page
	 * @param filters
	 * @return
	 * @throws Exception
	 */	
	public  Page getPage(Page page,List<PropertyFilter> filters) throws Exception;
	
	/**
	 * 获取对象Page(可设置忽略字段或查询字段)
	 * @param page
	 * @param filters
	 * @param isIgnore
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public  Page getPage(Page page,List<PropertyFilter> filters,boolean isIgnore,String...field) throws Exception;
	
		
}
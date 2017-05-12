package com.jst.common.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.jst.common.model.BaseModel;
import com.jst.common.utils.page.Page;

public interface BaseDAO<T> {

	
	public String formatSqlWhere(String sql);
	
	public long getListCounter(String sql, List paraList) throws Exception;
	
	public long getListCounter(String sql) throws Exception ;
	
	public List getObjList(String sql, List paraList,Page page) throws Exception;
	
	public List getObjList(String sql, List paraList,int pageSize,int pageNo) throws Exception;
	
	public void updatePart(String sql, List paraList) throws Exception;
	
	public void executeHql(String hql,List paraList) throws Exception ;
	
	public Serializable save(Object model) throws Exception;
	
	public void update(Object model) throws Exception;
	
	/**
	 * 修改对象中的指定字段
	 * @param model
	 * @param updateField
	 * @throws Exception
	 */
	public void update(Object model ,String updateField)throws Exception;
	
	public void delete(Serializable id) throws Exception;
	
	public void executeHql(String hql) throws Exception;
	
	public List getObjList(String sql, Page page) throws Exception ;
	
	public List getObjList(String sql) throws Exception ;
	
	public List getObjList(String sql, List paraList) throws Exception;

	public List getTableList(String sql,Page page) throws Exception ;
	
	public List getTableList(String sql, List paraList,String className) throws Exception ;
	
	public List getTableList(String sql, List paraList,Page page,String className)throws Exception ;
	
	public List getTableList(String sql, List paraList,int pageSize,int pageNo,String className)throws Exception;
	
	
	/**
	 * @param sql
	 * @param paraList
	 * @return
	 * @throws Exception
	 */
	public long getTableListCounter(String sql, List paraList) throws Exception;
	
	
	public void delete(Object id);
	
	public List getByPropertys(String[] propertyNames, Object[] values, String pSqlWhere) throws Exception ;
	
	public BaseModel getById(Object id);
	
	public BaseModel get(Object id);
	
	 public List getByExample(BaseModel instance) ;
	 
	 public BaseModel merge(BaseModel detachedInstance) ;
	
	 public String getModelName();
	 
	 public  List getObjList(BaseModel objBaseModel,Page page)throws Exception;
	 
	 public  long getObjListCounter(BaseModel objBaseModel)throws Exception;
	 
	 public long getObjListCounter(String sql, List paraList) throws Exception;
	 
	 /**
	     * 根据忽略字段进行分页查询
	     * @param page
	     * @param filters
	     * @param isIgnore 是否根据忽略字段进行查询， true时，field将以忽略字段进行查询，   false时 field 将以查询字段进行查询
	     * @param field	 需要忽略的字符串数组 ，长度为1时，使用方式与 getList(Page page,List<Propertyfilter>) 方法一致
	     * @return
	     * @throws Exception
	     */
	    public Page<T> getListByIgnore(final Page<T> page, final List<PropertyFilter> filters ,boolean isIgnore,  final String... field) throws Exception;
	    
	    /**
		 * 按属性过滤条件列表分页查找对象.
		 */
		public Page<T> getList(final Page<T> page, final List<PropertyFilter> filters) throws Exception;
		
		
		public Page<T> getListByField(final Page<T> page,final boolean isIgnore, final List<PropertyFilter> filters,final String ... field) throws Exception;
		
		public List getByPorperty(String propertyName, Object value, String pSqlWhere);
		
		public List getAllList();
		
		public long getAllListCounter() ;
		/**
		 * 根据HQL进行查询， 返回当前modelName的对象集合
		 * @param sql
		 * @return
		 * @throws Exception
		 */
		public List getListByHql(String sql) throws Exception;
		
		/**
		 * 根据HQL进行查询，返回当前modelName的对象集合
		 * @param sql
		 * @param columns  所查询的字段 以“,”分割
		 * @return
		 * @throws Exception
		 */
		public List getListByHql(String sql,String columns) throws Exception;
		
		/**
		 * 根据HQL进行查询， 返回当前modelName的对象集合
		 * @param sql
		 * @return
		 * @throws Exception
		 */
		public List getListByHql(String sql,String columns,List<String> paramList,Page page)throws Exception;
		/**
		 * 根据HQL进行查询， 返回当前modelName的对象集合
		 * @param sql
		 * @return
		 * @throws Exception
		 */
		public List getListByHql(String sql,String columns,List<String> paramList)throws Exception;
		/**
		 * 根据HQL进行查询， 返回当前modelName的对象集合
		 * @param sql
		 * @return
		 * @throws Exception
		 */
		public List getListByHql(String sql,String columns,List<String> paramList, Integer pageSize, Integer pageNum) throws Exception;
		
		/**
		 * 根据Sql进行查询，返回当前modelName的对象集合
		 * @param sql
		 * @return
		 * @throws Exception
		 */
		public List getListBySql(String sql) throws Exception;
		
		public List getListBySql(String sql ,String columns) throws Exception;
		
		public List getListBySql(String sql, String columns,Page page) throws Exception;
		
		/**
		 * 根据Sql进行查询，返回当前modelName的对象集合
		 * @param sql
		 * @return
		 * @throws Exception
		 */
		public List getListBySql(String sql,String columns,List<String> paramList,Page page) throws Exception;
		
		public List getListBySql(String sql, String columns ,List<String> paramList,Integer pageSize, Integer pageNum) throws Exception;
		
		public List query(Query query, String columns,List<String> paramList,Page page)throws Exception;
		
		public List find(String sql );
		
		public List queryForSql (String sql ) throws Exception;
		
		public void setCacheQueries(boolean cacheQueries);
		
		
		public List getByPropertys(String[] propertyNames,
				Object[] values, String pSqlWhere ,String orderByStr);
		
		public Session getSession();
}

package com.jst.common.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.apache.axis2.addressing.AddressingConstants.Final;
import org.apache.commons.dbutils.QueryLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.jst.common.model.BaseModel;
import com.jst.common.utils.reflection.ReflectionUtils;
import com.jst.common.utils.string.StringUtil;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.oracore.OracleType;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.hibernate.PropertyFilter.MatchType;
import com.jst.common.utils.page.Page;


/**
 * Data access object (DAO) for domain model
 * 
 * @author MyEclipse Persistence Tools
 * @param <T>
 */

@Repository
public abstract class HibernateBaseDAO<T> implements BaseDAO<T>{
	private static final Log log = LogFactory.getLog(HibernateBaseDAO.class);
	
	private boolean cacheQueries = false;
	
	@Autowired  
    private SessionFactory sessionfactory;  
	
	public Session getSession(){
		return sessionfactory.getCurrentSession();
	}
	
	/*@Autowired
	public void setSessionFactory(SessionFactory sessionFactory){  
	  super.setSessionFactory(sessionFactory);  
	}*/
		
	public String formatSqlWhere(String sql) {
		return sql;
	}

	public long getListCounter(String sql, List paraList) throws Exception {
		long counter = 0L;
		Session session = null;
		try {
			session = getSession();
			SQLQuery query = session.createSQLQuery(sql);

			if ((paraList != null) && (paraList.size() > 0)) {
				for (int index = 0; index < paraList.size(); ++index) {
					Object pe = paraList.get(index);
					query.setParameter(index, pe);
				}
			}
			counter = Long.parseLong(query.uniqueResult().toString());
			return counter;
		} catch (Exception ex) {
			log.error("getListCounter failed: " + ex);
			throw ex;
		}
	}

	public long getListCounter(String sql) throws Exception {
		return getListCounter(sql, null);
	}
	
	public long getObjListCounter(String sql, List paraList) throws Exception{
		long counter = 0;
		Session session =null;
		try{
			session = getSession();			
			Query query = session.createQuery(sql);
			
			if(paraList!=null && paraList.size()>0){
				for(int index=0;index<paraList.size();index++){
					Object pe = (Object)paraList.get(index);
					query.setParameter(index, pe);
				}
			}
			counter = Long.parseLong(query.uniqueResult().toString());		
			return counter;
		}
		catch(Exception ex){			
			log.error("getListCounter failed: " + ex);
			throw ex;
		}		
	}
	
	public List getObjList(String sql, List paraList,Page page) throws Exception {
		List list = new ArrayList();
		Session session = null;
		try {
			session = getSession();
			if(page!=null && StringUtil.isNotEmpty(page.getOrderBy())){
				sql +=" order by model."+page.getOrderBy();
				if(StringUtil.isNotEmpty(page.getOrder()) && page.getOrder().equalsIgnoreCase("asc")){
					sql+=" asc";
				}else{
					sql+=" desc";
				}
			}else{
				sql +=" order by model.id desc";
			}
			Query query = session.createQuery(sql);
			if ((paraList != null) && (paraList.size() > 0)) {
				for (int index = 0; index < paraList.size(); ++index) {
					Object pe = paraList.get(index);
					query.setParameter(index, pe);
				}
			}
			
			if (page!=null && (page.getPageNo() > 0) && (page.getPageSize()> 0)) {
				query.setFirstResult((page.getPageNo() - 1) * page.getPageSize());
				query.setMaxResults(page.getPageSize());
			}
			
			list = query.list();
			return list;
		} catch (Exception ex) {
			log.error("getList failed: " + ex);
			throw ex;
		}
	}
	
	public List getObjList(String sql, List paraList,int pageSize,int pageNo) throws Exception{
		
		Page page=new Page();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		
		return this.getObjList(sql, paraList, page);
			
		
	}
	
	public void updatePart(String sql, List paraList)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Query query = session.createQuery(sql);
			if ((paraList != null) && (paraList.size() > 0)) {
				for (int index = 0; index < paraList.size(); ++index) {
					Object pe = paraList.get(index);
					query.setParameter(index, pe);
				}
			}

			query.executeUpdate();
		} catch (Exception ex) {
			log.error("updatePart failed: " + ex);
			throw ex;
		}
	}
	
	public void executeHql(String hql,List paraList) throws Exception {
		log.debug("executeSql begin");
		
		Session session = null;
		try {
			session = getSession();
			Query query = session.createQuery(hql);
			if ((paraList != null) && (paraList.size() > 0)) {
				for (int index = 0; index < paraList.size(); ++index) {
					Object pe = paraList.get(index);
					query.setParameter(index, pe);
				}
			}

			query.executeUpdate();
		} catch (Exception ex) {
			log.error("executeSql failed: " + ex);
			throw ex;
		}
	}
	
	public Serializable save(Object model) throws Exception{
		return this.getSession().save(model);
	}
	
	public void update(Object model) throws Exception{
		this.getSession().update(model);
	}
	
	public void update(Object model ,String updateField)throws Exception{
		String alias = "t";
		String mName = model.getClass().getSimpleName();
		String sql = "update "+mName+ " "+alias;
		Field field = model.getClass().getDeclaredField("id");
		Field[] fields = model.getClass().getDeclaredFields();
		List paramList = new ArrayList();
		String updateStr = null;
		if(StringUtil.isNotEmpty(updateField)){
			
			for (int i = 0; i < fields.length; i++) {
				if(updateField.contains(fields[i].getName())){
					Field f = fields[i];
					f.setAccessible(true);
					if(StringUtil.isEmpty(updateStr)){
						updateStr = " set "+alias+"."+f.getName() +" = ? ";
						
					}else{
						updateStr += " , " + alias + "." + f.getName() +" = ? ";
					}
					paramList.add(f.get(model));
				}
			}
		}
		
		field.setAccessible(true);
		Object v = field.get(model);
		paramList.add(v);
		if(StringUtil.isNotEmpty(updateStr)){
			sql += updateStr;
		}
		sql += " where "+alias+"."+field.getName()+" = ?";
		Query query  = this.getSession().createQuery(sql);
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(i, paramList.get(i));
		}
		query.executeUpdate();
	}
	
	public void delete(Serializable id) throws Exception{
		this.getSession().delete(this.getSession().load(getModelName(), id));
	}

	public void executeHql(String hql) throws Exception {
		executeHql(hql,null);
	}

	public List getObjList(String sql, Page page) throws Exception {
		return getObjList(sql, null,null);
	}

	public List getObjList(String sql) throws Exception {
		return getObjList(sql, null,null);
	}

	public List getObjList(String sql, List paraList) throws Exception {
		return getObjList(sql, paraList,null);
	}

	public List getTableList(String sql,Page page) throws Exception {
		return getTableList(sql, null,page,null);
	}

	public List getTableList(String sql, List paraList,String className) throws Exception {
		return getTableList(sql, paraList, null,className);
	}
	
	public List getTableList(String sql, List paraList,Page page,String className)throws Exception {
		Object[] objs = null;
		Session session = null;
		try {
			session = getSession();
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);

			if ((paraList != null) && (paraList.size() > 0)) {
				for (int index = 0; index < paraList.size(); ++index) {
					Object pe = paraList.get(index);
					query.setParameter(index, pe);
				}
			}
			if(StringUtil.isNotEmpty(className)){
				query.addEntity(className);
			}
			if (page!=null && (page.getPageNo() > 0) && (page.getPageSize()> 0)) {
				query.setFirstResult((page.getPageNo() - 1) * page.getPageSize());
				query.setMaxResults(page.getPageSize());
			}
			List list = query.list();
			return list;
		} catch (Exception ex) {
			log.error("getListCounter failed: " + ex);
			throw ex;
		}
	}
	
	public List getTableList(String sql, List paraList,int pageSize,int pageNo,String className)throws Exception {
		Page page=new Page();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		
		return this.getTableList(sql, paraList, page, className);		
	}
	
	/**
	 * @param sql
	 * @param paraList
	 * @return
	 * @throws Exception
	 */
	public long getTableListCounter(String sql, List paraList) throws Exception{
		long counter = 0;
		Session session =null;
		try{
			session = getSession();			
			SQLQuery query = session.createSQLQuery(sql);
			
			if(paraList!=null && paraList.size()>0){
				for(int index=0;index<paraList.size();index++){
					Object pe = (Object)paraList.get(index);
					query.setParameter(index, pe);
				}
			}
			counter = Long.parseLong(query.uniqueResult().toString());		
			return counter;
		}
		catch(Exception ex){			
			log.error("getListCounter failed: " + ex);
			throw ex;
		}		
	}


	public void delete(Object id){
		StringBuffer hql = new StringBuffer(1000);
		hql.append("delete ");
		hql.append(getModelName());
		hql.append(" as model where model.id=?");
        Session sessions = getSession();
       // Transaction t = sessions.beginTransaction();
        Query query = sessions.createQuery(hql.toString());
        query.setParameter(0, id);       
            query.executeUpdate();      
       //     t.rollback();
       
	}
	
	public List getByPropertys(String[] propertyNames,
			Object[] values, String pSqlWhere) throws Exception {
		log.debug("geting " + getModelName() + " instance with propertys: ");
		try {
			String sqlWhere = "";

			for (int index = 0; index < propertyNames.length; ++index) {
				if (index == 0)
					sqlWhere = " where model." + propertyNames[index] + " = ?";
				else {
					sqlWhere = sqlWhere + " and  model." + propertyNames[index]
							+ " = ?";
				}
			}

			if ((pSqlWhere != null) && (pSqlWhere.trim().length() > 0)) {
				if (sqlWhere.trim().length() > 0)
					sqlWhere = sqlWhere + " and " + pSqlWhere;
				else {
					sqlWhere = sqlWhere + " where " + pSqlWhere;
				}
			}
			String sql = "select * from " + getModelName() + " as model " + sqlWhere;

			Query queryObject = getSession().createQuery(sql);

			for (int index = 0; index < values.length; ++index) {
				queryObject.setParameter(index, values[index]);
			}

			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("get by propertys name failed", re);
			throw re;
		}
	}
    
    public BaseModel getById(Object id) {
        log.debug("getting "+getModelName()+" instance with id: " + id);
        try {      	
        	        		
        	BaseModel instance = (BaseModel) getSession().get(
        					getModelName(), (Serializable) id);        	
        	
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    /*public T get(Serializable id) throws Exception{
    	return (T)getSession().get(getModelName(), id);
    }*/
    
    public BaseModel get(Object id) {
        log.debug("getting "+getModelName()+" instance with id: " + id);
        try {      	
        	        		
        	BaseModel instance = (BaseModel) getSession().get(
        					getModelName(), (Serializable) id);        	
        	
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public List getByExample(BaseModel instance) {
		log.debug("geting "+getModelName()+" instance by example");
		try {
			List results = getSession()
					.createCriteria(getModelName())
					.add(Example.create(instance)).list();
			log.debug("get by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("get by example failed", re);
			throw re;
		}
	}
    public BaseModel merge(BaseModel detachedInstance) {
		log.debug("merging "+getModelName()+" instance");
		try {
			BaseModel result = (BaseModel) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

    public abstract String getModelName();
    
    public  List getObjList(BaseModel objBaseModel,Page page)throws Exception{
    	return null;
    }
    public  long getObjListCounter(BaseModel objBaseModel)throws Exception{
    	return 0;
    }
    
    /**
     * 根据忽略字段进行分页查询
     * @param page
     * @param filters
     * @param isIgnore 是否根据忽略字段进行查询， true时，field将以忽略字段进行查询，   false时 field 将以查询字段进行查询
     * @param field	 需要忽略的字符串数组 ，长度为1时，使用方式与 getList(Page page,List<Propertyfilter>) 方法一致
     * @return
     * @throws Exception
     */
    public Page<T> getListByIgnore(final Page<T> page, final List<PropertyFilter> filters ,boolean isIgnore,  final String... field) throws Exception{
    	if(field.length<1){
    		return getList(page, filters);
    	}else{
    		Criterion[] criterions = new HibernateHelper().buildCriterionByPropertyFilter(filters);
    		
    		if(null != filters && filters.size() > 0){
    			return new HibernateHelper().getPage(page, filters,isIgnore, field , criterions);
    		}
    		
    		return new HibernateHelper().getPage(page, isIgnore,field, criterions);
    	}
    }
	
    
	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public Page<T> getList(final Page<T> page, final List<PropertyFilter> filters) throws Exception{
		Criterion[] criterions = new HibernateHelper().buildCriterionByPropertyFilter(filters);
		
		if(null != filters && filters.size() > 0){
			return new HibernateHelper().getPage(page, filters,false, null , criterions);
		}
		
		return new HibernateHelper().getPage(page, false,null, criterions);
	}
	
	public Page<T> getListByField(final Page<T> page,final boolean isIgnore, final List<PropertyFilter> filters,final String ... field) throws Exception{
		Criterion[] criterions = new HibernateHelper().buildCriterionByPropertyFilter(filters);
		
		if(null != filters && filters.size() > 0){
			return new HibernateHelper().getPage(page, filters, isIgnore, field , criterions);
		}
		
		return new HibernateHelper().getPage(page, isIgnore, field, criterions);
	}
	
	class HibernateHelper{
	
	/**
	 * @see 按属性条件列表创建Criterion数组,辅助函数
	 *      为了处理ManyToOne或OneToOne的特殊情况，对
	 *      filters做出了过滤。之前判断有点错误，JP于2016-06-13做了修改
	 * @author lee
	 * @since 2014-08-19
	 * @param filters
	 * @return
	 */
	private Criterion[] buildCriterionByPropertyFilter(final List<PropertyFilter> filters) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		
		for (int i=filters.size()-1; i>=0; i--) {
			PropertyFilter filter = filters.get(i);
			
			
			
			if (!filter.hasMultiProperties()) { //只有一个属性需要比较的情况.
				if(filter.getPropertyName().indexOf(".") > 0){
					continue;
				}
				
				Criterion criterion=null;
				
				if(filter.getMatchType().equals(MatchType.IN)){
					criterion = buildCriterionin(filter.getPropertyName(), filter.getCzValue());
				}else{
					criterion = buildCriterion(filter.getPropertyName(), filter.getMatchValue(), filter.getMatchType());
				}
				
				criterionList.add(criterion);
			} else {//包含多个属性需要比较的情况,进行or处理.
				Disjunction disjunction = Restrictions.disjunction();
				
				for (String param : filter.getPropertyNames()) {
					Criterion criterion=null;
					
					if(filter.getMatchType().equals(MatchType.IN)){
						criterion = buildCriterionin(param, filter.getCzValue());
					}else{
						criterion = buildCriterion(param, filter.getMatchValue(), filter.getMatchType());
					}
					
					disjunction.add(criterion);
				}
				
				criterionList.add(disjunction);
			}
			
			filters.remove(i);
		}
		
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}
	
	/**
	 * 按属性条件参数创建Criterion,辅助函数.
	 */
	private Criterion buildCriterion(final String propertyName, final Object propertyValue, final MatchType matchType) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = null;
		//根据MatchType构造criterion
		switch (matchType) {
		case EQ:
			criterion = Restrictions.eq(propertyName, propertyValue);
			break;
		case LIKE:
			criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
			break;
		case LE:
			criterion = Restrictions.le(propertyName, propertyValue);
			break;
		case LT:
			criterion = Restrictions.lt(propertyName, propertyValue);
			break;
		case GE:
			criterion = Restrictions.ge(propertyName, propertyValue);
			break;
		case GT:
			criterion = Restrictions.gt(propertyName, propertyValue);
			break;
		case NE:   //不等于
			criterion = Restrictions.ne(propertyName, propertyValue);
			break;
		case ILIKE:  //模糊查询不分大小写
			criterion = Restrictions.ilike(propertyName, propertyValue);
			break;
		}
		
		return criterion;
	}
	
	/**
	 * 按属性条件参数创建Criterion,辅助函数,此处只匹配in
	 */
	private Criterion buildCriterionin(final String propertyName, final List<?> propertyValue) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = null;		
		criterion = Restrictions.in(propertyName, propertyValue);						
		return criterion;
	}
	
	
	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page 分页参数.
	 * @param criterions 数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询输入参数.
	 */
	private Page<T> getPage(final Page<T> page,final boolean isIgnore, final String [] field, final Criterion... criterions) throws Exception{
		Assert.notNull(page, "page不能为空");
		Criteria c = createCriteria(criterions);
		if(isIgnore){
			setIgnoreField(c,field);
		}else{
			setQueryField(c,field);
		}
		if (page.isAutoCount()) {
			long totalCount = countCriteriaResult(c,isIgnore ,field);
			page.setTotalCount(totalCount);
		}
		
		setPageParameterToCriteria(c, page);
		
		List result = c.list();
		page.setResult(result);
		return page;
	}
	
	
	/**
	 * @see 此方法解决了实体关联关系为多对一，多方以一方非外键关联字段为查询条件查询时，
	 *      而导致的Exception:could not resolve property someproperty of some model
	 *      且同样适用于正常的方式，即在之前的编码基础上做出了适当升级，但仅支持JPA的Annotation
	 * @author lee
	 * @since 2014-08-19
	 * @param page
	 * @param filters 此时filter中仅包含ManyToOne中One方的属性
	 * @param criterions
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Page<T> getPage(final Page<T> page, final List<PropertyFilter> filters,final boolean isIgnore, final String [] field, final Criterion... criterions) throws Exception{
		Assert.notNull(page, "page不能为空");
		Criteria c = createCriteria(criterions);
		
		Map<String, List<PropertyFilter>> entityMap = new HashMap<String, List<PropertyFilter>>();
		
		for(PropertyFilter pf : filters){
			String entityName = pf.getPropertyName().substring(0, pf.getPropertyName().indexOf("."));
			
			if(!entityMap.containsKey(entityName)){
				String methodName = "get" + entityName.substring(0,1).toUpperCase() + entityName.substring(1,entityName.length());
				
				Class<?> modelClass = Class.forName(getModelName());
				
				try{
					Method method = modelClass.getMethod(methodName);
					
					if(!method.isAnnotationPresent(OneToOne.class) && !method.isAnnotationPresent(ManyToOne.class)){
						throw new Exception("请在方法" + methodName + "上添加@OneToOne或@ManyToOne注解");
					}
				}catch(Exception e){
					if(e instanceof NoSuchMethodException){
						throw new Exception("请检查实体" + modelClass.getSimpleName() + "中ManyToOne的One方实体命名与所传入属性命名");
					}else{
						throw e;
					}
				}
				
				List<PropertyFilter> propertyFilterList = new ArrayList<PropertyFilter>();
				
				propertyFilterList.add(pf);
				
				entityMap.put(entityName, propertyFilterList);
			}else{
				entityMap.get(entityName).add(pf);
			}
		}
		
		for(String entityName : entityMap.keySet()){
			Criteria criteria = c.createCriteria(entityName);
			
			for(PropertyFilter pf : entityMap.get(entityName)){
				String propertyName = pf.getPropertyName();
				
				propertyName = propertyName.substring(propertyName.indexOf(".")+1,propertyName.length());
				
				criteria.add(buildCriterion(propertyName, pf.getMatchValue(), pf.getMatchType()));
			}
		}
		
//		循环取出One方属性
//		for(int i=0; i<filters.size(); i++){
//			PropertyFilter pf = filters.get(i);
//			
//			String propertyName = pf.getPropertyName();
//			
//			propertyName = propertyName.substring(propertyName.indexOf(".")+1,propertyName.length());
//			
//			MatchType matchType = pf.getMatchType();
//			Object propertyValue = pf.getMatchValue();
//			
//			if(0 == i){
//				//Many方实体中的One方实体名称
//				String entityName = pf.getPropertyName().substring(0, pf.getPropertyName().indexOf("."));
//				
//				//Many方实体对象
//				Class<?> modelClass = Class.forName(getModelName());
//				
//				//尝试获取One方实体的get方法
//				String methodName = "get" + entityName.substring(0,1).toUpperCase() 
//											+ entityName.substring(1,entityName.length());
//				
//				try{
//					Method method = modelClass.getMethod(methodName);
//					
//					if(!method.isAnnotationPresent(OneToOne.class) &&  !method.isAnnotationPresent(ManyToOne.class)){
//						throw new Exception("请在方法" + methodName + "上添加@OneToOne或@ManyToOne注解");
//					}
//					
//					//为One方创建新的Criteria对象
//					criteria = c.createCriteria(entityName);
//				}catch(Exception e){
//					if(e instanceof NoSuchMethodException){
//						throw new Exception("请检查实体"+modelClass.getSimpleName()+"中ManyToOne的One方实体命名与所传入属性命名");
//					}else{
//						throw e;
//					}
//				}
//			}
//			
//			criteria.add(buildCriterion(propertyName, propertyValue, matchType));
//		}

		if(isIgnore){
			setIgnoreField(c,field);
		}else{
			setQueryField(c,field);
		}
		
		if (page.isAutoCount()) {
			long totalCount = countCriteriaResult(c,isIgnore,field);
			page.setTotalCount(totalCount);
		}

		setPageParameterToCriteria(c, page);

		List<T> result = c.list();
		
		page.setResult(result);
		
		return page;
	}
	
	private Criteria createCriteria( final Criterion... criterions) throws Exception{
		Criteria criteria = null;
		criteria = getSession().createCriteria(Class.forName(getModelName()), "t");
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	@SuppressWarnings("unchecked")
	private long countCriteriaResult(final Criteria c, final boolean isIgnore, final String...field) throws Exception{
		CriteriaImpl impl = (CriteriaImpl) c;
		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
	//ResultTransformer transformer = impl.getResultTransformer();
		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			log.error("countCriteriaResult error", e);
			throw e;
		}

		Object obj = c.setProjection(Projections.rowCount()).uniqueResult();
		// 执行Count查询
		Long totalCountObject = obj!=null?new Long(obj.toString()):null;
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		//if (transformer != null) {
			c.setResultTransformer(Transformers.aliasToBean(Class.forName(getModelName())));
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			log.error("countCriteriaResult error", e);
			throw e;
		}

		return totalCount;
	}
	
	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	private Criteria setPageParameterToCriteria(final Criteria c, final Page<T> page) {

		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

		//hibernate的firstResult的序号从0开始
		
		c.setFirstResult((page.getPageNo() - 1) * page.getPageSize());	
		c.setMaxResults(page.getPageSize());

		if (page.getOrderBy()!=null && page.getOrderBy().trim().length()>0) {
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');

			Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

			for (int i = 0; i < orderByArray.length; i++) {
				if (Page.ASC.equals(orderArray[i])) {
					c.addOrder(Order.asc(orderByArray[i]));
				} else {
					c.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		return c;
	}	
	}
	
	public List getByPorperty(String propertyName,
			Object value, String pSqlWhere){
		log.debug("getByPorperty begin");
		
		try{
			String[] propertyNames=new String[] {propertyName};
			Object[] values=new Object[] {value};
			
			String orderByStr="";
			List list=getByPropertys(propertyNames,values,pSqlWhere,orderByStr);
			log.debug("getByPorperty end");
			return list;
		} catch (RuntimeException re) {
			log.error("getByPorperty  failed", re);
			throw re;
		}
		
		
		
	}
	
	public List getByPropertys(String[] propertyNames,
			Object[] values, String pSqlWhere ,String orderByStr)  {
		log.debug("geting " + getModelName() + " instance with propertys: ");
		
		if(propertyNames==null || values==null){
			throw new RuntimeException("propertyNames 和 values 都不能为空");
		}
		if(propertyNames.length!=values.length){
			throw new RuntimeException("propertyNames 和 values 的length不一致");
		}
		
		try {
			String sqlWhere = "";

			for (int index = 0; index < propertyNames.length; ++index) {
				if (index == 0)
					sqlWhere = " where model." + propertyNames[index] + " = ?";
				else {
					sqlWhere = sqlWhere + " and  model." + propertyNames[index]
							+ " = ?";
				}
			}

			if ((pSqlWhere != null) && (pSqlWhere.trim().length() > 0)) {
				if (sqlWhere.trim().length() > 0)
					sqlWhere = sqlWhere + " and " + pSqlWhere;
				else {
					sqlWhere = sqlWhere + " where " + pSqlWhere;
				}
			}
			if(orderByStr==null)
				orderByStr="";
			String sql = " from " + getModelName() + " as model " + orderByStr+" "+sqlWhere;

			Query queryObject = getSession().createQuery(sql);

			for (int index = 0; index < values.length; ++index) {
				queryObject.setParameter(index, values[index]);
			}

			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("get by propertys name failed", re);
			throw re;
		}
	}
	
	public List getAllList() {

		log.debug("getAllList " + getModelName() + " instances");
		try {
			String queryString = "from " + getModelName();
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("getAllList failed", re);
			throw re;
		}

	}

	public long getAllListCounter() {

		long counter = 0;

		log.debug("getAllListCounter " + getModelName() + " instances");
		try {
			String queryString = "select count(*) from " + getModelName();
			Query queryObject = getSession().createQuery(queryString);
			counter = ((Long) queryObject.iterate().next()).longValue();
			return counter;

		} catch (RuntimeException re) {
			log.error("getAllListCounter failed", re);
			throw re;
		}

	}
	
	/**
	 * 设置查询字段
	 * @param c
	 * @param field
	 */
	public void setQueryField(Criteria c ,String... field){
		String modelName = getModelName();
		if(null != field){
			try {
				Class clazz = Class.forName(modelName);
				Field [] fields =  clazz.getDeclaredFields();
				ProjectionList pList = Projections.projectionList();
				for (int i = 0; i < fields.length; i++) {
					for (int j = 0; j < field.length; j++) {
						if(StringUtil.isNotEmpty(field[j])&&field[j].equals(fields[i].getName())){
							pList.add(Projections.property("t."+field[j]).as(field[j]));
						}
					}
				}
				if(pList.getLength()>=1){
					c.setProjection(pList);
				}
			} catch (ClassNotFoundException e) {
				log.error("class "+ modelName + " is not found !"+e,e);
			}
		}else{
			try {
				Class clazz = Class.forName(modelName);
				Field [] fields =  clazz.getDeclaredFields();
				ProjectionList pList = Projections.projectionList();
				for (int i = 0; i < fields.length; i++) {
					pList.add(Projections.property("t."+fields[i].getName()).as(fields[i].getName()));
				}
				if(pList.getLength()>=1){
					c.setProjection(pList);
				}
			} catch (Exception e) {
				log.error("class " + modelName +" is not found ! "+e,e);
			}
		}
		
	}
	
	/**
	 * 设置忽略字段
	 * @param c
	 * @param field
	 */
	public void setIgnoreField(Criteria c,String... field){
		String modelName = getModelName();
		if(null != field ){
			try {
				Class clazz = Class.forName(modelName);
				Field[] fields  = clazz.getDeclaredFields();
				ProjectionList pList = Projections.projectionList();
				/*Object obj = clazz.newInstance();
				Example example = Example.create(obj).ignoreCase();*/
				for (int i = 0; i < fields.length; i++) {
					boolean isIgnore = true;
					for (int j = 0; j < field.length; j++) {
						if(StringUtil.isNotEmpty(field[j])&&field[j].equals(fields[i].getName())){
							isIgnore = false;
						}
					}
					if(isIgnore){
						pList.add(Projections.property("t."+fields[i].getName()).as(fields[i].getName()));
					}
				}
				if(pList.getLength()>=1){
					c.setProjection(pList);
				}
				//c.add(example);
			} catch (ClassNotFoundException e) {
				log.error("class "+modelName+" is not found !"+e,e);
			} 
		}
		else{
			try {
				Class clazz = Class.forName(modelName);
				Field [] fields =  clazz.getDeclaredFields();
				ProjectionList pList = Projections.projectionList();
				for (int i = 0; i < fields.length; i++) {
					pList.add(Projections.property("t."+fields[i].getName()).as(fields[i].getName()));
				}
				if(pList.getLength()>=1){
					c.setProjection(pList);
				}
			} catch (Exception e) {
				log.error("class " + modelName +" is not found ! "+e,e);
			}
			
		}
	}
	
	
	
	/**
	 * 根据HQL进行查询， 返回当前modelName的对象集合
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List getListByHql(String sql) throws Exception{
		String fieldStr = null;
		if(StringUtil.isNotEmpty(sql)&&sql.contains("select")&&sql.indexOf("select")<1){
			fieldStr =  sql.substring(7, sql.indexOf("from")-1);
			if(fieldStr.contains("*")){
				fieldStr = null;
			}
		}
		return getListByHql(sql, fieldStr);
		
	}
	/**
	 * 根据HQL进行查询，返回当前modelName的对象集合
	 * @param sql
	 * @param columns  所查询的字段 以“,”分割
	 * @return
	 * @throws Exception
	 */
	public List getListByHql(String sql,String columns) throws Exception{
		return getListByHql(sql, columns, null, null);
	}
	/**
	 * 根据HQL进行查询， 返回当前modelName的对象集合
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List getListByHql(String sql,String columns,List<String> paramList,Page page)throws Exception{
		Query query = getSession().createQuery(sql);
		return query(query, columns, paramList, page);
	}
	/**
	 * 根据HQL进行查询， 返回当前modelName的对象集合
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List getListByHql(String sql,String columns,List<String> paramList)throws Exception{
		return getListByHql(sql, columns, paramList, null);
	}
	/**
	 * 根据HQL进行查询， 返回当前modelName的对象集合
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List getListByHql(String sql,String columns,List<String> paramList, Integer pageSize, Integer pageNum) throws Exception{
		Page page = new Page();
		page.setPageNo(pageNum);
		page.setPageSize(pageSize);
		return getListByHql(sql, columns, paramList, page);
	}
	
	/**
	 * 根据Sql进行查询，返回当前modelName的对象集合
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List getListBySql(String sql) throws Exception{
		return getListBySql(sql, null, null, null);
	}
	
	public List getListBySql(String sql ,String columns) throws Exception{
		return getListBySql(sql, columns, null, null);
	}
	
	public List getListBySql(String sql, String columns,Page page) throws Exception{
		return getListBySql(sql, columns, null, page);
	}
	
	/**
	 * 根据Sql进行查询，返回当前modelName的对象集合
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List getListBySql(String sql,String columns,List<String> paramList,Page page) throws Exception{
		try {
			String fieldStr = null;
			if(StringUtil.isNotEmpty(sql)&&sql.contains("select")&&sql.indexOf("select")<1){
				fieldStr =  sql.substring(7, sql.indexOf("from")-1);
				if(fieldStr.contains("*")){
					fieldStr = null;
				}
			}
			Session session = this.getSession();
			SQLQuery query = session.createSQLQuery(sql);
			if(StringUtil.isEmpty(fieldStr))
			query.addEntity(this.getModelName());
			return query(query, fieldStr, paramList, page);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public List getListBySql(String sql, String columns ,List<String> paramList,Integer pageSize, Integer pageNum) throws Exception{
		Page page = new Page();
		page.setPageNo(pageNum);
		page.setPageSize(pageSize);
		return getListBySql(sql, columns, paramList, page);
	}
	
	public List query(Query query, String columns,List<String> paramList,Page page)throws Exception{
		if ((paramList != null) && (paramList.size() > 0)) {
			for (int index = 0; index < paramList.size(); index++) {
				Object pe = paramList.get(index);
				query.setParameter(index, pe);
			}
		}
		if (page!=null && (page.getPageNo() > 0) && (page.getPageSize()> 0)) {
			query.setFirstResult((page.getPageNo() - 1) * page.getPageSize());
			query.setMaxResults(page.getPageSize());
		}
		
		List list = query.list();;
		if(StringUtil.isNotEmpty(columns)){
			Class clazz = Class.forName(getModelName());
			List modelList = new ArrayList();
			String[] attr = columns.split(",");
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object object = clazz.newInstance();
				if(attr.length>1){
					Object[] o = (Object[]) iterator.next();
					for(int i = 0;i < attr.length;i++){
						String fieldName = attr[i];
						if(attr[i].contains(".")){
							fieldName = attr[i].split("\\.")[1];
						}
						Field field = clazz.getDeclaredField(fieldName);
						field.setAccessible(true);
						field.set(object, o[i]);
					}
					
				}else{
					Object o = (Object) iterator.next();
					String fieldName = attr[0];
					if(attr[0].contains(".")){
						fieldName= attr[0].split("\\.")[1];
					}
					Field field = clazz.getDeclaredField(fieldName);
					field.setAccessible(true);
					field.set(object, o);
				}
				modelList.add(object);
				
			}
			return modelList;
		}else{
			return list;
		}
		
	}
	
	public List find(String sql ){
		Session session  =this.getSession();
		Query query = session.createQuery(sql);
		query.setCacheable(cacheQueries);
		return query.list();
	}
	
	
	public List queryForSql (String sql ) throws Exception{
		try {
			
			Session session = this.getSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			query.setResultSetMapping(this.getModelName());
			List list = query.list();
			System.out.println("bbb");
			
			return list;
		} catch (Exception e) {
			throw e;
		}
		
	}

	public void setCacheQueries(boolean cacheQueries) {
		this.cacheQueries = cacheQueries;
	}

	
}

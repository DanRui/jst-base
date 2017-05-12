package com.jst.common.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jst.common.utils.page.Page;
import com.jst.common.model.BaseModel;
import com.jst.common.hibernate.BaseDAO;
import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.service.BaseService;
import javax.servlet.http.HttpSession;

public abstract class BaseServiceImpl implements BaseService{
	private static final Log log = LogFactory.getLog(HibernateBaseDAO.class);
	
	public abstract BaseDAO getHibernateBaseDAO();
	
	/*public Object add(BaseModel baseModel,HttpSession httpSession)throws Exception{
		getHibernateBaseDAO().save(baseModel);
		return baseModel.getId();
	}
	
	public void update(BaseModel baseModel,HttpSession httpSession)throws Exception {
		getHibernateBaseDAO().update(baseModel);
	}*/
	
	@Transactional
	public Serializable save(Object model) throws Exception{
		return getHibernateBaseDAO().save(model);
	}
	
	@Transactional
	public BaseModel update(Serializable id ,BaseModel baseModel) throws Exception{
		Field field = baseModel.getClass().getDeclaredField("id");
		Field [] fields = baseModel.getClass().getDeclaredFields();
		field.setAccessible(true);
		BaseModel object = getHibernateBaseDAO().get(id);
		Class objClas =object.getClass();
		for (int i = 0; i < fields.length; i++) {
			Field curfield = fields[i];
			if("id".equals(curfield.getName())){
				continue;
			}
			Field objField = objClas.getDeclaredField(curfield.getName());
			curfield.setAccessible(true);
			objField.setAccessible(true);
			if(null != curfield.get(baseModel)){
				objField.set(object, curfield.get(baseModel));
			}
		}
		getHibernateBaseDAO().update(object);
		return object;
	}
	
	@Transactional
	public BaseModel update(BaseModel baseModel) throws Exception{
		/*Field field = baseModel.getClass().getDeclaredField("id");
		Field [] fields = baseModel.getClass().getDeclaredFields();
		field.setAccessible(true);
		BaseModel object = getHibernateBaseDAO().get(id);
		Class objClas =object.getClass();
		for (int i = 0; i < fields.length; i++) {
			Field curfield = fields[i];
			if("id".equals(curfield.getName())){
				continue;
			}
			Field objField = objClas.getDeclaredField(curfield.getName());
			curfield.setAccessible(true);
			objField.setAccessible(true);
			if(null != curfield.get(baseModel)){
				objField.set(object, curfield.get(baseModel));
			}
		}*/
		getHibernateBaseDAO().update(baseModel);
		return baseModel;
	}
	
	@Transactional
	public Serializable delete(Serializable id) throws Exception{
		/*BaseModel bm = this.get(id);
		getHibernateBaseDAO().delete(bm);*/
		getHibernateBaseDAO().delete(id);
		return id;
	}

	public void delete(Object id,HttpSession httpSession)throws Exception {
		
		getHibernateBaseDAO().delete(id);
	}
	
	
	public BaseModel get(Serializable id) throws Exception{
		return getHibernateBaseDAO().getById(id);
	}
	
	
	public List getListByPropertys(String[] propertyNames,
			Object[] values, String pSqlWhere ,String orderByStr) throws Exception {		
		return getHibernateBaseDAO().getByPropertys(propertyNames, values, pSqlWhere, orderByStr);
	}
	
	public List getListByPorperty(String propertyName,
			Object value, String pSqlWhere) throws Exception{
		return getHibernateBaseDAO().getByPorperty(propertyName, value, pSqlWhere);
	}
	
	
	
	
	public  Page getPage(Page page,List<PropertyFilter> filters) throws Exception{					
			return  getHibernateBaseDAO().getList(page, filters);	
	
	}
	
	
	public List getAllList() throws Exception{			
		return  getHibernateBaseDAO().getAllList();
	}
	
	
	public long getAllListCounter() throws Exception{			
		return  getHibernateBaseDAO().getAllListCounter();
	}
	
	
	public Page getPage(Page page, List<PropertyFilter> filters,boolean isIgnore,String...field)
			throws Exception {
		return getHibernateBaseDAO().getListByIgnore(page, filters, isIgnore, field);
	}
}

package com.jst.common.test.person;


import com.jst.common.hibernate.HibernateBaseDAO;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * Person entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.tdby.drvschool.model.person.Person
 * @author MyEclipse Persistence Tools
 */

@Repository(value="personDAO")
public class PersonDAO extends HibernateBaseDAO {
	private static final Log log = LogFactory.getLog(Person.class);
	private final String modelName ="com.jst.common.test.person.Person";
	public String getModelName() {
		return modelName;
	}
	
	public List getPersonList(String idCard, String personName,String schoolCode,String state,String sortStr,int pageSize,
			int pageNo) throws Exception {
		
		log.debug("getPersonList begin");

		List objList = new ArrayList();
		//List list = new ArrayList();
		List paraList = new ArrayList();
	
		String sqlWhere = "";

		try {

			if (idCard != null && idCard.trim().length() > 0) {
				sqlWhere += " and idCard=?";
				paraList.add(idCard);
			}

			if (personName != null && personName.trim().length() > 0) {
				sqlWhere += " and personName=?";
				paraList.add(personName);
			}

			
			if (schoolCode != null && schoolCode.trim().length() > 0) {
				sqlWhere += " and schoolCode=?";
				paraList.add(schoolCode);
			}

			if (state != null && state.trim().length() > 0) {
				sqlWhere += " and state=?";
				paraList.add(state);
			}

		

			String sql = "from Person a where 1=1 " + sqlWhere;
			
		
			if(sortStr==null || sortStr.equals("")){				
				sortStr = "  order by updateTime  Desc, rowid desc";
			}else{
				sortStr =" order by "+sortStr +" , updateTime Desc,rowid desc";
			}
			
			
			sql = sql + sortStr;
			
			sql = formatSqlWhere(sql);

			objList = this.getObjList(sql, paraList, pageSize, pageNo);
			log.debug("getPersonList end");

		} catch (RuntimeException re) {
			log.error("getPersonList failed", re);
			throw re;
		}

		return objList;
		
		
	}
	
	public long getPersonListCounter(String idCard, String personName,String schoolCode,String state) throws Exception {
		
		log.debug("getPersonListCounter begin");

		long counter = 0;
		List paraList = new ArrayList();
		int parameterIndex = 0;
		String sqlWhere = "";

		try {

			if (idCard != null && idCard.trim().length() > 0) {
				sqlWhere += " and idCard=?";
				paraList.add(idCard);
			}

			if (personName != null && personName.trim().length() > 0) {
				sqlWhere += " and personName=?";
				paraList.add(personName);
			}

			
			if (schoolCode != null && schoolCode.trim().length() > 0) {
				sqlWhere += " and schoolCode=?";
				paraList.add(schoolCode);
			}

			if (state != null && state.trim().length() > 0) {
				sqlWhere += " and state=?";
				paraList.add(state);
			}
			
			String sql = "select count(*) as counter from Person  a where 1=1 "
					+ sqlWhere;
			sql = formatSqlWhere(sql);

			counter = this.getObjListCounter(sql, paraList);
			log.debug("getPersonListCounter end");

		} catch (RuntimeException re) {
			log.error("getPersonListCounter failed", re);
			throw re;
		}

		return counter;
		
	}

	
}
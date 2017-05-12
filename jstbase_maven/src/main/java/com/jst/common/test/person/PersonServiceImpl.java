package com.jst.common.test.person;

import java.util.List;







import java.io.File;
import java.io.Serializable;
import java.sql.CallableStatement;


import javax.annotation.Resource;
import java.sql.Types;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jst.common.service.ServiceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;


import com.jst.common.utils.page.*;





import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.model.BaseModel;
import com.jst.common.service.BaseServiceImpl;


import java.io.*;


@Service(value="personService")
public class PersonServiceImpl extends BaseServiceImpl implements PersonService{

	private PersonDAO dao;
	private static final Log log = LogFactory.getLog(PersonServiceImpl.class);

		
	@Resource(name="personDAO")
	public void setHibernateBaseDAO(PersonDAO dao) {
		this.dao = dao;
	}
	@Override
	public HibernateBaseDAO getHibernateBaseDAO() {
		return dao;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// test("DELETE");
			//test("ADD");
			// test("UPDATE");
			// test("GET_LIST_COUNTER");
			// test("GET_LIST");
			// test("PAY_FEE_APP");
			test("GET_LIST");
			test("GET_LIST");
			test("GET_LIST");
			test("GET_LIST");
			test("GET_LIST");
			
		
			Thread.sleep(1000*30);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void test(String testType) throws Exception {

		PersonService svr=(PersonService)ServiceFactory.getService("personService") ;
		Person obj = getTestObj();
		HttpSession httpSession = null;

		if (testType.equalsIgnoreCase("ADD")) {
			
			//svr.add(obj, httpSession);
		} else if (testType.equalsIgnoreCase("UPDATE")) {

			//svr.update(obj, httpSession);

		} else if (testType.equalsIgnoreCase("DELETE")) {
			//svr.delete(obj, httpSession);
					
		} else if (testType.equalsIgnoreCase("GET_LIST_COUNTER")) {

			String idCard=null;
			String personName=null;
			String schoolCode=null;
			String state=null;
			String sortStr=null;
			
			long counter = 0;

			//counter=svr.getPersonListCounter(idCard, personName, schoolCode, state);

			System.out.println("couner:" + counter);

	
		} else if (testType.equalsIgnoreCase("LIST")) {
			
			Page page=new Page();
			List<PropertyFilter> filters=new ArrayList();			
			Page p=svr.getPage(page, filters);
			
			System.out.println("counter:"+p.getTotalCount());
			if(p!=null && p.getTotalCount()>0){
				
				Person person=(Person)p.getResult().get(0);
				System.out.println("personName:"+person.getPersonName());
				
			}
			
			
			
		} else if (testType.equalsIgnoreCase("GET_LIST")) {

			String idCard=null;
			String personName=null;
			String schoolCode=null;
			String state=null;
			String sortStr=null;			 
			
			int pageSize = 100;
			int pageNo = 1;

			List list = svr.getPersonList(idCard, personName, schoolCode, state, sortStr, pageSize, pageNo);

			System.out.println("couner:" + list.size());
			if (list.size() > 0) {
				System.out.println("id:" + ((Person) list.get(0)).getId()
						+ " name=:" + ((Person) list.get(0)).getPersonName());

			}
		}
		
		

	}
	


	public static Person getTestObj() {

		Integer id = new Integer(4);
		String idCard = "432402197609191010";
		String personName = "������2";
		String schoolCode = "001";
		String state = "1";
		byte[] photo =null;
		
		
		
		try{
			BufferedInputStream in = new BufferedInputStream(new FileInputStream("D:\\img\\2.2.1.4.jpg"));         
	        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);         
	        
	        System.out.println("Available bytes:" + in.available());         
	        
	        byte[] temp = new byte[1024];         
	        int size = 0;         
	        while ((size = in.read(temp)) != -1) {         
	            out.write(temp, 0, size);         
	        }         
	        in.close();  
	        photo = out.toByteArray();
        
		}catch(Exception e){
			e.printStackTrace();
		}
        
                

        

		Person person = new Person(idCard, personName, schoolCode, state);

		person.setId(id);
		person.setPhoto(photo);

		return person;

	}
	
	@Transactional(propagation=Propagation.NEVER,readOnly=true)
	public List getPersonList(String idCard, String personName,
			String schoolCode, String state, String sortStr, int pageSize,
			int pageNo) throws Exception {
		List list = null;
		log.debug("getPersonList begin");
		Session session = null;

		try {
		
			list=dao.getPersonList(idCard, personName, schoolCode, state, sortStr, pageSize, pageNo);
	
			log.debug("getPersonList successful");

		} catch (RuntimeException re) {

			log.error("getPersonList failed", re);
			throw re;
		}
		return list;

	}
	
	@Transactional(propagation=Propagation.NEVER,readOnly=true)
	public long getPersonListCounter(String idCard, String personName,
			String schoolCode, String state) throws Exception {
		long counter = 0;
		log.debug("getPersonListCounter begin");
		Session session = null;

		try {
	
			counter =dao.getPersonListCounter(idCard, personName, schoolCode, state); 
				
			log.debug("getPersonListCounter successful");
		} catch (RuntimeException re) {

			log.error("getPersonListCounter failed", re);
			throw re;
		}
		return counter;
	}


}

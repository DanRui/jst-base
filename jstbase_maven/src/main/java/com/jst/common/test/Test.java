/**
 * 
 */
package com.jst.common.test;

import java.io.BufferedInputStream;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.model.BaseModel;
import com.jst.common.model.DictType;
import com.jst.common.model.SysDict;
import com.jst.common.service.CacheService;
import com.jst.common.service.ServiceFactory;
import com.jst.common.test.person.Person;
import com.jst.common.test.person.PersonService;
import com.jst.common.utils.page.Page;

/**
 * @author Administrator
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		
		String sql = "select t.name,t.scription from TestModel";
		CacheService cacheService = (CacheService)ServiceFactory.getService("cacheService");
		List<SysDict> list = cacheService.getSysDict();
		
		System.out.println("fist query");
		
		System.out.println("________________________2 ________________");
		//List<DictType> bb = cacheService.getDictType();
		//System.out.println(menuList.size());
		/*list = cacheService.getSysDict();
		
		
		
		System.out.println("________________________3 ________________");
		*/
		
		List menuList = cacheService.getMenu();
		//menuList=cacheService.getMenu();
		
		System.out.println("________________________4 ________________");
		
		
		//System.out.println(sql.substring(7, sql.indexOf("from")-1));
	/*	test("ADD",false);*/
		/*try {
			// test("DELETE");
			
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
		}*/
		
		
		/*TestModel bm = new TestModel();
		bm.setId(123123);
		Field field = bm.getClass().getDeclaredField("id");
		field.setAccessible(true);
		System.out.println(field.get(bm));*/
		//ServiceFactory.getService("testServiceImpl");
		
	}
	
	
	public static void test(String testType,boolean aa) throws Exception{
		/*TestService test = (TestService)ServiceFactory.getService("testServiceImpl");
		TestModel testModel = new TestModel();
		testModel.setId(11);
		testModel.setName("我只是测试");
		//Serializable id = test.save(testModel);
		Page<TestModel> page = new Page<TestModel>();
		page.setPageNo(1);
		page.setPageSize(5);
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("LIKES_name","a"));
		page = test.getPage(page, filters,true);
		List<TestModel> list = page.getResult();
		System.out.println(list.get(0).getScription());*/
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
				//System.out.println("personName:"+person.getPersonName());
				
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
	
	

}

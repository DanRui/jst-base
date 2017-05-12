package com.jst.common.test.person;

import java.util.Date;

import java.util.List;

import com.jst.common.service.BaseService;

public interface PersonService extends BaseService{
	
	public List getPersonList(String idCard, String personName,String schoolCode,String state,String sortStr,int pageSize,
			int pageNo) throws Exception ;
	
	public long getPersonListCounter(String idCard, String personName,String schoolCode,String state) throws Exception ;

	
	
}

package com.jst.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.dao.ISysDictDAO;
import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.common.model.SysDict;

/**
 * 
 * @author Administrator
 *
 */
@Repository
public class SysDictDAO extends HibernateBaseDAO<SysDict> implements ISysDictDAO{
	
	private static final String modelName= SysDict.class.getName();

	@Override
	public String getModelName() {
		return modelName;
	}

}

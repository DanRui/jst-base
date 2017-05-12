package com.jst.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.dao.IMenuDAO;
import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.common.model.Menu;

@Repository
public class MenuDAO extends HibernateBaseDAO<Menu> implements IMenuDAO {

	private static final String modelName = MenuDAO.class.getName();
	
	@Override
	public String getModelName() {
		// TODO Auto-generated method stub
		return modelName;
	}

}

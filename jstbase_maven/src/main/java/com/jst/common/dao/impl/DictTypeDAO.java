package com.jst.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.dao.IDictTypeDAO;
import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.common.model.DictType;

@Repository
public class DictTypeDAO extends HibernateBaseDAO<DictType> implements IDictTypeDAO{

	private static final String modelName = DictType.class.getName();
	
	@Override
	public String getModelName() {
		return modelName;
	}

}

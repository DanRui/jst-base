package com.jst.common.dao.impl;
import org.springframework.stereotype.Repository;

import com.jst.common.dao.ISystemLogDAO;
import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.common.model.SystemLog;

@Repository
public class SystemLogDAO extends HibernateBaseDAO<SystemLog> implements ISystemLogDAO {

	private static final String modelName = SystemLog.class.getName();
	
	@Override
	public String getModelName() {
		return modelName;
	}


}
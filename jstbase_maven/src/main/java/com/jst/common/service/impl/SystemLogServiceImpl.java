package com.jst.common.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import com.jst.common.model.SystemLog;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jst.common.dao.ISystemLogDAO;
import com.jst.common.hibernate.BaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.service.ISystemLogService;


@Service("systemLogService")
public class SystemLogServiceImpl extends BaseServiceImpl implements ISystemLogService {

	@Resource(name = "systemLogDAO")
	private ISystemLogDAO logDAO;
	
	@Override
	public BaseDAO getHibernateBaseDAO() {
		return logDAO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Serializable save(Object model) throws Exception {
		// TODO Auto-generated method stub
		SystemLog systemLog = new SystemLog();
		BeanUtils.copyProperties(systemLog, model);
		return super.save(systemLog);
	}
}

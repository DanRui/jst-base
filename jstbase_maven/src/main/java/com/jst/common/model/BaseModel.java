package com.jst.common.model;


import java.io.Serializable;

import javax.persistence.Id;

public class BaseModel implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	protected Integer id;

	@Id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}

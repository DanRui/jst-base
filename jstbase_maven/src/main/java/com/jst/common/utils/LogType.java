package com.jst.common.utils;

/**
 * 日志枚举， 0，常规日志，1。业务操作日志，2.其它日志，3，错误日志
 * @author Administrator
 *
 */
public enum LogType {
	ZERO(0),
	ONE(1),
	TWO(2),  
	THREE(3);
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	private LogType(int value){
		this.value = value;
	}
}

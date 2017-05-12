package com.jst.common.utils.cache;

public enum CachKeys {
	PagePvg("pagePvg"),
	SysMdlPvg("SysMdlPvg")
	;
	private String key;
	private CachKeys(String key){
		this.key=key;
	};
	public String getKey(){
		return key;
	}
}

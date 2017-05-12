package com.jst.common.utils.web;

public class Page {
	// 当前页
	private int currPage;

	// 一页多少条数据
	private int pageSize;

	// 排序列
	private String sortname;

	// 排序类型 asc desc
	private String sortorder;
	
	
	
	public Page(){
		
	}
	public Page(int currPage,int pageSize,String sortname,String sortorder){
		this.currPage=currPage;
		this.pageSize=pageSize;
		this.sortname=sortname;
		this.sortorder=sortorder;
	}
	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}

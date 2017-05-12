package com.jst.common.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "T_MENU")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Menu implements Serializable{

	private static final long serialVersionUID = 6296164522403824993L;

	private String menuCode;
	private String menuName;
	private String parentMenuCode;
	private String menuLevel;
	private String mdlCode;
	private String menuPic;
	private String menuUrl;
	private String openMode;
	private String showType;
	private String width;
	private String height;
	private Integer sortId;
	private String state;
	private String remark;
	
	@Id
	@Column(name="MENU_CODE")
	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	@Column(name="MENU_NAME")
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	@Column(name="PARENT_MENU_CODE")
	public String getParentMenuCode() {
		return parentMenuCode;
	}

	public void setParentMenuCode(String parentMenuCode) {
		this.parentMenuCode = parentMenuCode;
	}
	
	@Column(name="MENU_LEVEL")
	public String getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
	}

	@Column(name="MDL_CODE")
	public String getMdlCode() {
		return mdlCode;
	}

	public void setMdlCode(String mdlCode) {
		this.mdlCode = mdlCode;
	}
	
	@Column(name="MENU_PIC")
	public String getMenuPic() {
		return menuPic;
	}

	public void setMenuPic(String menuPic) {
		this.menuPic = menuPic;
	}
	
	@Column(name="MENU_URL")
	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	@Column(name="OPEN_MODE")
	public String getOpenMode() {
		return openMode;
	}

	public void setOpenMode(String openMode) {
		this.openMode = openMode;
	}
	
	@Column(name="SHOW_TYPE")
	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	@Column(name="WIDTH")
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	
	@Column(name="HEIGHT")
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@Column(name="SORT_ID")
	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	@Column(name="STATE")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}

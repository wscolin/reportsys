package com.zxd.commonmodel;

import java.io.Serializable;

public class Page implements Serializable {

	private static final long serialVersionUID = 145646464664632L;

	private int start = 0; // 开始位置，从0开始

	private int length = 10; // 每页显示记录的条数

	private String searchValue;

	private String orderColumn;

	private String orderDir;
	
	private String USERID;	

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getOrderDir() {
		return orderDir;
	}

	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}

	public String getUSERID() {
		return USERID;
	}

	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}

}

package com.lms.tutor.model;

import java.io.Serializable;

import com.opencsv.bean.CsvBindByName;

public class BulkUploadUserChildCatMapping implements Serializable {
	
	private static final long serialVersionUID = 3817430792680203481L;
	
	public BulkUploadUserChildCatMapping() {
		super();
	}
	
	public BulkUploadUserChildCatMapping(String userId, Integer childCategoryId) {
		super();
		this.userId = userId;
		this.childCategoryId = childCategoryId;
	}
	
	@CsvBindByName
    private String userId;
    @CsvBindByName
    private Integer childCategoryId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getChildCategoryId() {
		return childCategoryId;
	}
	public void setChildCategoryId(Integer childCategoryId) {
		this.childCategoryId = childCategoryId;
	}
	

}

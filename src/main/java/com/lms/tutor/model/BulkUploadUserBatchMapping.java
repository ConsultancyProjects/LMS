package com.lms.tutor.model;

import java.io.Serializable;

import com.opencsv.bean.CsvBindByName;

public class BulkUploadUserBatchMapping implements Serializable {
	
	private static final long serialVersionUID = 1787224525888408253L;
	
	public BulkUploadUserBatchMapping() {
		super();
	}
	
	public BulkUploadUserBatchMapping(String userId, Integer batchId) {
		super();
		this.userId = userId;
		this.batchId = batchId;
	}
	
	@CsvBindByName
    private String userId;
    @CsvBindByName
    private Integer batchId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

}

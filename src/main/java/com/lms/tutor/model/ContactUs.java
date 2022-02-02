package com.lms.tutor.model;

import java.io.Serializable;

public class ContactUs implements Serializable {

	private static final long serialVersionUID = 398786853214094304L;

	private String fromEmailId;

	private String userName;

	private String description;

	public String getFromEmailId() {
		return fromEmailId;
	}

	public void setFromEmailId(String fromEmailId) {
		this.fromEmailId = fromEmailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

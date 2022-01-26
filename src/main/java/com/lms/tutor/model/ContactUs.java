package com.lms.tutor.model;

import java.io.Serializable;

public class ContactUs implements Serializable {

	private static final long serialVersionUID = 398786853214094304L;

	private String fromEmailId;

	private String toEmailId;

	private String description;

	public String getFromEmailId() {
		return fromEmailId;
	}

	public void setFromEmailId(String fromEmailId) {
		this.fromEmailId = fromEmailId;
	}

	public String getToEmailId() {
		return toEmailId;
	}

	public void setToEmailId(String toEmailId) {
		this.toEmailId = toEmailId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

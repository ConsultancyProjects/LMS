package com.lms.tutor.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BATCH")
public class Batch implements Serializable {

	private static final long serialVersionUID = 3599019479403785740L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BATCH_ID")
	private int batchId;

	@Column(name = "BATCH_NAME")
	private String batchName;

	@Column(name = "DESCRIPTION")
	private String description;

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(batchId, batchName, description);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Batch other = (Batch) obj;
		return Objects.equals(batchName, other.batchName);
	}

}

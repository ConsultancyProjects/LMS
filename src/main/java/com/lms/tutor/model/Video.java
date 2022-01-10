package com.lms.tutor.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "VIDEO")
public class Video implements Serializable {

	private static final long serialVersionUID = -142639955667239882L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "VIDEO_ID")
	private int videoId;

	@OneToOne
	@JoinColumn(name = "CATEGORY_ID")
	private ChildVideoCategory category;

	@Column(name = "VIDEO_NAME")
	private String videoName;

	@Column(name = "S3_PATH")
	private String s3Path;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}

	public ChildVideoCategory getCategory() {
		return category;
	}

	public void setCategoryId(ChildVideoCategory category) {
		this.category = category;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getS3Path() {
		return s3Path;
	}

	public void setS3Path(String s3Path) {
		this.s3Path = s3Path;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public void setCategory(ChildVideoCategory category) {
		this.category = category;
	}

}

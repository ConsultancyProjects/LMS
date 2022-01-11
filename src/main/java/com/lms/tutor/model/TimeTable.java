package com.lms.tutor.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TIME_TABLE")
public class TimeTable implements Serializable {

	private static final long serialVersionUID = 3586658158640686909L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SCHEDULE_ID")
	private int scheduleId;

	@JoinColumn(name = "VIDEO_ID")
	@OneToOne
	private Video video;

	@JoinColumn(name = "TUTOR_ID", referencedColumnName = "USER_ID")
	@OneToOne
	private User tutorId;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "BATCH_ID")
	private Batch batch;

	// "fromDate" : "2019-04-28T14:45:15"
	@Column(name = "FROM_DATE")
	private Timestamp fromDate;

	@Column(name = "TO_DATE")
	// @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp toDate;

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideoId(Video video) {
		this.video = video;
	}

	public User getTutorId() {
		return tutorId;
	}

	public void setTutorId(User tutorId) {
		this.tutorId = tutorId;
	}

	public long getFromDate() {
		return fromDate.getTime();
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public long getToDate() {
		return toDate.getTime();
	}

	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

}
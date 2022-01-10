package com.lms.tutor.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_VIDEO_CATEGORY_MAPPING")
public class UserVideoCategoryMapping implements Serializable {

	private static final long serialVersionUID = -398212648733641206L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	User user;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "CHILD_VIDEO_CATEGORY_ID")
	ChildVideoCategory childVideoCategory;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ChildVideoCategory getChildVideoCategory() {
		return childVideoCategory;
	}

	public void setChildVideoCategory(ChildVideoCategory childVideoCategory) {
		this.childVideoCategory = childVideoCategory;
	}

}

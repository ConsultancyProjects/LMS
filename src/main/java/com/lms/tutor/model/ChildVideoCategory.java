package com.lms.tutor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CHILD_VIDEO_CATEGORY")
public class ChildVideoCategory implements Serializable {

	private static final long serialVersionUID = 4146463930919882184L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CHILD_CATEGORY_ID")
    private int childCategoryId;
	
	@Column(name = "CATEGORY_NAME")
    private String name;
	
	@ManyToOne
	@JoinColumn(name = "PARENT_CATEGORY_ID")
	private VideoCategory parentCategory;
    

	public int getChildCategoryId() {
		return childCategoryId;
	}

	public void setChildCategoryId(int childCategoryId) {
		this.childCategoryId = childCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String categoryName) {
		this.name = categoryName;
	}

	public VideoCategory getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(VideoCategory parentCategory) {
		this.parentCategory = parentCategory;
	}
	
}

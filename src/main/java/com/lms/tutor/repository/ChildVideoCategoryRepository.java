package com.lms.tutor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.tutor.model.ChildVideoCategory;

public interface ChildVideoCategoryRepository extends JpaRepository<ChildVideoCategory, Integer> {

	List<ChildVideoCategory> findAllByParentCategoryCategoryId(int parentCategoryId);
	
	ChildVideoCategory findByChildCategoryId(int childCategoryId);

}

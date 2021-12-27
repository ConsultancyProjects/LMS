package com.lms.tutor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.tutor.model.UserVideoCategoryMapping;

@Repository
public interface UserVideoCategoryMappingRepository extends JpaRepository<UserVideoCategoryMapping, Integer> {
    
	List<UserVideoCategoryMapping> findByUserUserId(String userId);

	List<UserVideoCategoryMapping> findByChildVideoCategoryChildCategoryId(int childCatgoryId);
	
}
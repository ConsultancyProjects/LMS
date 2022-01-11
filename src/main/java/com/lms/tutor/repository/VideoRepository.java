package com.lms.tutor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.tutor.model.Video;


@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
	
	@Query(value = "\r\n"
			+ "select video.* from lms.child_video_category cat,\r\n"
			+ "lms.video video\r\n"
			+ "where video.CATEGORY_ID = cat.child_category_id\r\n"
			+ "and cat.parent_category_id = :categoryId", nativeQuery=true)
	List<Video> findAllVideosThatBelongToParentCategory(int categoryId);
	
	Video findByCategoryChildCategoryId(int categoryId);
	
	 @Query(value = "SELECT * FROM lms.video where CATEGORY_ID in (:catId)", nativeQuery=true)
	 List<Video> findVideosThatBelongToChildCategoryIn(@Param("catId")List<Integer> catId);
}
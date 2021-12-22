package com.lms.tutor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.tutor.model.Video;


@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
	
	List<Video> findAllByCategoryCategoryId(Integer categoryId);
}
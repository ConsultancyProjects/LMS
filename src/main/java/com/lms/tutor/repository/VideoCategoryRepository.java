package com.lms.tutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.tutor.model.VideoCategory;


@Repository
public interface VideoCategoryRepository extends JpaRepository<VideoCategory, Integer> {
}
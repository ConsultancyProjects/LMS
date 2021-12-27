package com.lms.tutor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.Video;
import com.lms.tutor.repository.VideoRepository;

@RestController
@RequestMapping("/videos")
public class VideoController {
	
	@Autowired
	VideoRepository videoRepository;
	
	@GetMapping("/{videoId}/metadata/")
	public Video getVideoMetaData(@PathVariable(value = "videoId") int videoId) {
		return videoRepository.findById(videoId).get();
	}
	
	@GetMapping("/all/metadata/")
	public List<Video> getAllVideosMetaData() {
		return videoRepository.findAll();
	} 
	
	@GetMapping("/all/metadata/{categoryId}")
	public List<Video> findAllVideosThatBelongToParentCategory(@PathVariable(value = "categoryId") Integer categoryId) {
		return videoRepository.findAllVideosThatBelongToParentCategory(categoryId);
	}
	
	@GetMapping("/metadata/{categoryId}")
	public Video getVideosMetaThatBelongToChildCategoryId(@PathVariable(value = "categoryId") Integer categoryId) {
		return videoRepository.findByCategoryChildCategoryId(categoryId);
	}
	
	@PostMapping("/metadata")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String addVideoMetaData(@RequestBody Video video) {
		videoRepository.save(video);
		return "Success";
	}
	
}

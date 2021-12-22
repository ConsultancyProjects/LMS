package com.lms.tutor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.VideoCategory;
import com.lms.tutor.repository.VideoCategoryRepository;

@RestController
@RequestMapping("/video-categories")
public class VideoCategoryController {
	
	@Autowired
	private VideoCategoryRepository videoCategoryRepository;
	
	@GetMapping("/")
	public List<VideoCategory> getAllVideoCategories() {
		return videoCategoryRepository.findAll();
	}
	
	@PostMapping("/")
	public String addTimeTable(@RequestBody VideoCategory videoCategory) {
		videoCategoryRepository.save(videoCategory);
		return "Success";
	}
	
	@PutMapping("/")
	public String updateTimeTable(@RequestBody VideoCategory videoCategory) {
		videoCategoryRepository.save(videoCategory);
		return "Success";
	}

}

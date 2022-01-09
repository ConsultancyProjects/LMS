package com.lms.tutor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.Status;
import com.lms.tutor.model.VideoCategory;
import com.lms.tutor.repository.VideoCategoryRepository;

@RestController
@RequestMapping("/video-categories")
@PreAuthorize("hasAuthority('ADMIN')")
public class VideoCategoryController {
	
	@Autowired
	private VideoCategoryRepository videoCategoryRepository;
	
	@GetMapping("/")
	public List<VideoCategory> getAllVideoCategories() {
		return videoCategoryRepository.findAll();
	}
	
	@PostMapping("/")
	public String addParentVideoCategory(@RequestBody VideoCategory videoCategory) {
		videoCategoryRepository.save(videoCategory);
		return "Success";
	}
	@DeleteMapping("/")
	public String addParentVideoCategory(@RequestParam String categoryId) {
		videoCategoryRepository.deleteById(Integer.parseInt(categoryId));
		return "Success";
	}
	@PostMapping("/all")
	public Status addParentVideoCategories(@RequestBody List<VideoCategory> videoCategories) {
		videoCategoryRepository.saveAll(videoCategories);
		return new Status("Success");
	}
	@PutMapping("/")
	public String updateParentVideoCategory(@RequestBody VideoCategory videoCategory) {
		videoCategoryRepository.save(videoCategory);
		return "Success";
	}

}

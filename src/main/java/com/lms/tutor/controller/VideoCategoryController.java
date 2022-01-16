package com.lms.tutor.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.ChildVideoCategory;
import com.lms.tutor.model.Status;
import com.lms.tutor.model.VideoCategory;
import com.lms.tutor.repository.ChildVideoCategoryRepository;
import com.lms.tutor.repository.VideoCategoryRepository;

@RestController
@RequestMapping("/video-categories")
@PreAuthorize("hasAuthority('ADMIN')")
public class VideoCategoryController {

	@Autowired
	private VideoCategoryRepository videoCategoryRepository;

	@Autowired
	private ChildVideoCategoryRepository childVideoCategoryRepository;

	@GetMapping("/")
	public List<VideoCategory> getAllVideoCategories() {
		return videoCategoryRepository.findAll();
	}

	@PostMapping("/")
	public Status addParentVideoCategory(@RequestBody VideoCategory videoCategory) throws Exception {
		List<VideoCategory> catList = videoCategoryRepository.findAll();
		if (catList.contains(videoCategory)) {
			throw new Exception("Category Already Present!");
		}
		videoCategoryRepository.save(videoCategory);
		return new Status("Success");
	}

	@DeleteMapping("/")
	public Status addParentVideoCategory(@RequestParam String categoryId) {
		videoCategoryRepository.deleteById(Integer.parseInt(categoryId));
		return new Status("Success");
	}

	@PostMapping("/all")
	public Status addParentVideoCategories(@RequestBody List<VideoCategory> videoCategories) {
		videoCategoryRepository.saveAll(videoCategories);
		return new Status("Success");
	}

	@PutMapping("/")
	public Status updateParentVideoCategory(@RequestBody VideoCategory videoCategory) {
		videoCategoryRepository.save(videoCategory);
		return new Status("Success");
	}

	@DeleteMapping("/parent/{categoryId}")
	@Transactional
	public Status deleteParentVideoCategory(@PathVariable int categoryId) throws Exception {
		List<ChildVideoCategory> catList = childVideoCategoryRepository.
				findAllByParentCategoryCategoryId(categoryId);
		if (CollectionUtils.isEmpty(catList)) {
			videoCategoryRepository.deleteById(categoryId);
		} else {
			throw new Exception("You can't delete if theres a child category associated with it");
		}
		return new Status("Success");
	}

	@DeleteMapping("/child/{categoryId}")
	@Transactional
	public Status deleteChildVideoCategory(@PathVariable int categoryId) {
		childVideoCategoryRepository.deleteById(categoryId);
		return new Status("Success");
	}

}

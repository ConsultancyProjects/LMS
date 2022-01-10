package com.lms.tutor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.ChildVideoCategory;
import com.lms.tutor.repository.ChildVideoCategoryRepository;

@RestController
@RequestMapping("/child-video-categories")
@PreAuthorize("hasAuthority('ADMIN')")
public class ChildVideoCategoryController {

	@Autowired
	private ChildVideoCategoryRepository childVideoCategoryRepository;

	@GetMapping("/")
	public List<ChildVideoCategory> getAllChildVideoCategories() {
		return childVideoCategoryRepository.findAll();
	}

	@GetMapping("/{parentCategoryId}")
	public List<ChildVideoCategory> getAllVideoCategoriesByParentCatId(@PathVariable int parentCategoryId) {
		return childVideoCategoryRepository.findAllByParentCategoryCategoryId(parentCategoryId);
	}

	@PostMapping("/")
	public ChildVideoCategory addChildVideoCategory(@RequestBody ChildVideoCategory videoCategory) {
		childVideoCategoryRepository.save(videoCategory);
		return videoCategory;
	}

	@PutMapping("/")
	public ChildVideoCategory updateChildVideoCategory(@RequestBody ChildVideoCategory videoCategory) {
		childVideoCategoryRepository.save(videoCategory);
		return videoCategory;
	}

}

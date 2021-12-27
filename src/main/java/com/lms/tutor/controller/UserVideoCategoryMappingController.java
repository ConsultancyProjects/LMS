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

import com.lms.tutor.model.UserVideoCategoryMapping;
import com.lms.tutor.repository.ChildVideoCategoryRepository;
import com.lms.tutor.repository.UserRepository;
import com.lms.tutor.repository.UserVideoCategoryMappingRepository;

@RestController
@PreAuthorize("hasAnyAuthority('ADMIN', 'TUTOR')")
@RequestMapping("/user-catgeory-mapping")
public class UserVideoCategoryMappingController {
	
	@Autowired
	private UserVideoCategoryMappingRepository userVideoCategoryMappingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ChildVideoCategoryRepository childVideoCategoryRepository;
	
	@GetMapping("/user/{userId}")
	public List<UserVideoCategoryMapping> getAllCatgeoriesForUser(@PathVariable String userId) {
		return userVideoCategoryMappingRepository.findByUserUserId(userId);
	}
	
	@GetMapping("/category/{categoryId}")
	public List<UserVideoCategoryMapping> getAllCatgeoriesForUser(@PathVariable int categoryId) {
		return userVideoCategoryMappingRepository.findByChildVideoCategoryChildCategoryId(categoryId);
	}
	
	@PostMapping("/")
	public String addUserVideoCategoryMapping(@RequestBody UserVideoCategoryMapping
			userVideoCategoryMapping) {
		userVideoCategoryMapping.setChildVideoCategory(childVideoCategoryRepository.findByChildCategoryId(userVideoCategoryMapping.
				getChildVideoCategory().getChildCategoryId()));
		userVideoCategoryMapping.setUser(userRepository.
				findByUserId(userVideoCategoryMapping.getUser().getUserId()).get());
		 userVideoCategoryMappingRepository.save(userVideoCategoryMapping);
		 return "Success";
	}
	
	@PutMapping("/")
	public String updateUserVideoCategoryMapping(@RequestBody UserVideoCategoryMapping
			userVideoCategoryMapping) {
		 userVideoCategoryMappingRepository.save(userVideoCategoryMapping);
		 return "Success";
	}

}
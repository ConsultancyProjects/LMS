package com.lms.tutor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.ChildVideoCategory;
import com.lms.tutor.model.Status;
import com.lms.tutor.model.User;
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

	@GetMapping("/childcat/user/{userId}")
	public List<ChildVideoCategory> getAllCatgeoriesForUser(@PathVariable String userId) {
		List<UserVideoCategoryMapping> mapList = 
				userVideoCategoryMappingRepository.findByUserUserId(userId);
		List<ChildVideoCategory> catList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(mapList)) {
			catList = mapList.stream().map(mp->mp.getChildVideoCategory()).collect(Collectors.toList());
		}
		 return catList;
	}

	@GetMapping("/users/category/{categoryId}")
	public List<User> getAllUsersForCatgeory(@PathVariable int categoryId) {
		List<UserVideoCategoryMapping> mapList = 
				userVideoCategoryMappingRepository.findByChildVideoCategoryChildCategoryId(categoryId);
		List<User> userList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(mapList)) {
			userList = mapList.stream().map(mp->mp.getUser()).collect(Collectors.toList());
		}
		 return userList;
	}

	@GetMapping("/user/{userId}")
	public List<UserVideoCategoryMapping> getAllCatgeorieMappingsForUser(@PathVariable String userId) {
		return userVideoCategoryMappingRepository.findByUserUserId(userId);
	}

	@GetMapping("/category/{categoryId}")
	public List<UserVideoCategoryMapping> getAllCatgeorieMappingsForCategories(@PathVariable int categoryId) {
		return userVideoCategoryMappingRepository.findByChildVideoCategoryChildCategoryId(categoryId);
	}
	
	@PostMapping("/")
	@Transactional
	public Status addUserVideoCategoryMapping(@RequestBody UserVideoCategoryMapping userVideoCategoryMapping) {
		userVideoCategoryMapping.setChildVideoCategory(childVideoCategoryRepository
				.findByChildCategoryId(userVideoCategoryMapping.getChildVideoCategory().getChildCategoryId()));
		userVideoCategoryMapping
				.setUser(userRepository.findByUserId(userVideoCategoryMapping.getUser().getUserId()).get());
		userVideoCategoryMappingRepository.save(userVideoCategoryMapping);
		return new Status("Success");
	}

	@PutMapping("/")
	@Transactional
	public Status updateUserVideoCategoryMapping(@RequestBody UserVideoCategoryMapping userVideoCategoryMapping) {
		userVideoCategoryMapping.setChildVideoCategory(childVideoCategoryRepository
				.findByChildCategoryId(userVideoCategoryMapping.getChildVideoCategory().getChildCategoryId()));
		userVideoCategoryMapping
				.setUser(userRepository.findByUserId(userVideoCategoryMapping.getUser().getUserId()).get());
		userVideoCategoryMappingRepository.save(userVideoCategoryMapping);
		return new Status("Success");
	}

	@DeleteMapping("/{userVideoCatMapping}")
	@Transactional
	public Status deleteUserCatMapping(@PathVariable int userVideoCatMapping) {
		userVideoCategoryMappingRepository.deleteById(userVideoCatMapping);
		return new Status("Success");
	}

}
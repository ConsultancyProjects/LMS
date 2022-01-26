package com.lms.tutor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.constants.Constants;
import com.lms.tutor.model.Batch;
import com.lms.tutor.model.ChildVideoCategory;
import com.lms.tutor.model.MyUserDetails;
import com.lms.tutor.model.Status;
import com.lms.tutor.model.User;
import com.lms.tutor.model.UserBatchMapping;
import com.lms.tutor.model.UserVideoCategoryMapping;
import com.lms.tutor.repository.UserBatchMappingRepository;
import com.lms.tutor.repository.UserVideoCategoryMappingRepository;
import com.lms.tutor.service.UserLoginServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserLoginServiceImpl userDetailsService;
	
	@Autowired
	private UserVideoCategoryMappingRepository userVideoCategoryMappingRepository;
	
	@Autowired
	private UserBatchMappingRepository userBatchMappingRepository;

	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/")
	public List<User> getAllUsersForGivenRole(@RequestParam(required = false) Integer roleId) {
		List<User> userList = new ArrayList<>();
		if (roleId == null) {
			userList = userDetailsService.getAllUsersWithNoRole();

		} else {
			userList = userDetailsService.getAllUsersWithRoleId(roleId);
		}
		userList.stream().forEach(u ->{
			u.setPassword(null);
			u.setCategories(getAllCatgeoriesForUser(u.getUserId()));
			u.setBatches(getAllBatchMappingsForUsers(u.getUserId()));
		});
		return userList;
	}

	@GetMapping("/all")
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<>();
		userList.stream().forEach(u ->{
			u.setPassword(null);
			u.setCategories(getAllCatgeoriesForUser(u.getUserId()));
			u.setBatches(getAllBatchMappingsForUsers(u.getUserId()));
		});
		return userList;
	}

	@GetMapping("/{userId}")
	public User getUserProfile(@PathVariable String userId) {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (userDetails.getAuthorities().contains(Constants.ROLE_STUDENT)
				&& !userDetails.getUsername().equalsIgnoreCase(userId)) {
			throw new AccessDeniedException("You are authorized");
		}
		Optional<User> userData = userDetailsService.findUserByUserId(userId);
		if (userData.isPresent()) {
			userData.get().setPassword(null);
			userData.get().setCategories(getAllCatgeoriesForUser(userId));
			userData.get().setBatches(getAllBatchMappingsForUsers(userId));
		}
		return userData.get();
	}

	@DeleteMapping("/{userId}")
	public Status deleteUser(@PathVariable String userId) {
		userDetailsService.deleteUser(userId);
		return new Status("Success");
	}
	
	private List<ChildVideoCategory> getAllCatgeoriesForUser( String userId) {
		List<UserVideoCategoryMapping> mapList = 
				userVideoCategoryMappingRepository.findByUserUserId(userId);
		List<ChildVideoCategory> catList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(mapList)) {
			catList = mapList.stream().map(mp->mp.getChildVideoCategory()).collect(Collectors.toList());
		}
		 return catList;
	}

	private List<Batch> getAllBatchMappingsForUsers(@PathVariable String userId) {
		List<UserBatchMapping> mapList = 
				userBatchMappingRepository.findByUserId(userId);
		List<Batch> catList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(mapList)) {
			catList = mapList.stream().map(mp->mp.getBatch()).collect(Collectors.toList());
		}
		 return catList;
	}
	
	
	
}

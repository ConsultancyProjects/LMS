package com.lms.tutor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.constants.Constants;
import com.lms.tutor.model.MyUserDetails;
import com.lms.tutor.model.User;
import com.lms.tutor.service.UserLoginServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserLoginServiceImpl userDetailsService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/")
	public List<User> getAllUsersForGivenRole(@RequestParam(required=false) Integer roleId) {
		List<User> userList = new ArrayList<>();
		if (roleId == null) {
			userList = userDetailsService.getAllUsersWithNoRole();
					
		} else {
			userList = userDetailsService.getAllUsersWithRoleId(roleId);
		}
		userList.stream().forEach(u->u.setPassword(null));
		return userList;
	}
	
	@GetMapping("/{userId}")
	public User getUserProfile(@PathVariable String userId) {
		MyUserDetails userDetails = (MyUserDetails)SecurityContextHolder.getContext().
				getAuthentication().getPrincipal();
		if (userDetails.getAuthorities().contains(Constants.ROLE_STUDENT)
				&& !userDetails.getUsername().equalsIgnoreCase(userId)) {
			throw new AccessDeniedException("You are authorized");
		}
		Optional<User> userData = userDetailsService.findUserByUserId(userId);
		if (userData.isPresent()) {
			userData.get().setPassword(null);
		}
		return userData.get();
	}

}

package com.lms.tutor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.service.UserLoginServiceImpl;


@RestController
@PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT', 'TUTOR')")
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private UserLoginServiceImpl userDetailsService;
	
	@GetMapping("/")
	public String getAllStudentsForCourse() {
		return "healthy";
	}

}


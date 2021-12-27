package com.lms.tutor.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT', 'TUTOR')")
@RequestMapping("/students")
public class StudentController {
	
	@GetMapping("/")
	public String getAllStudentsForCourse() {
		return "healthy";
	}

}


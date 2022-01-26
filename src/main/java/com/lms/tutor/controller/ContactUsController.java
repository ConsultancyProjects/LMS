package com.lms.tutor.controller;

import javax.transaction.Transactional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.ContactUs;
import com.lms.tutor.model.Status;


@RestController
@RequestMapping("/contact-us")
@PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT', 'TUTOR')")
public class ContactUsController {
	
	@PostMapping("/")
	@Transactional
	public Status contactUs(@RequestBody ContactUs contactUs) throws Exception {
		return new Status("Success");
	}
	
}
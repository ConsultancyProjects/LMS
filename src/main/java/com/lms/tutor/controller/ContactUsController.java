package com.lms.tutor.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.ContactUs;
import com.lms.tutor.model.Status;
import com.lms.tutor.util.AmazonSesClient;


@RestController
@RequestMapping("/contact-us")
@PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT', 'TUTOR')")
public class ContactUsController {
	
	@Autowired
	AmazonSesClient amazonSesClient;
	
	@PostMapping("/")
	@Transactional
	public Status contactUs(@RequestBody ContactUs contactUs) throws Exception {
		amazonSesClient.sendContactUsMail(contactUs.getUserName(),
				contactUs.getDescription(), contactUs.getFromEmailId());
		return new Status("Success");
	}
	
}
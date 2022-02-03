package com.lms.tutor.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.AuthenticationRequest;
import com.lms.tutor.model.AuthenticationResponse;
import com.lms.tutor.model.ResetPassword;
import com.lms.tutor.model.Status;
import com.lms.tutor.model.User;
import com.lms.tutor.service.UserLoginServiceImpl;
import com.lms.tutor.util.AmazonSesClient;
import com.lms.tutor.util.JwtUtil;

@RestController
class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private UserLoginServiceImpl userDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AmazonSesClient amazonSesClient;
	
	@PostMapping(value = "/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
	try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("Incorrect username or password");
		} catch (Exception e) {
			throw new Exception("No Role assigned, reach out to admin", e);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return new AuthenticationResponse(jwt);
	}

	@PostMapping(value = "/register")
	public Status registerUser(@RequestBody User user) throws Exception {
		this.userDetailsService.registerUser(user);
		return new Status("Success");
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(value = "/add-user")
	public Status addUser(@RequestBody User user) throws Exception {
		this.userDetailsService.addUser(user);
		return new Status("Success");
	}
	
	@PutMapping(value = "/resetpassword")
	@Transactional
	public Status resetPassword(@RequestBody ResetPassword resetPassword) throws Exception {
		User user = this.userDetailsService.findUserByUserId(resetPassword.getUserId()).get();
		if (passwordEncoder.encode(user.getPassword()).equals(
				passwordEncoder.encode(resetPassword.getOldPassword()))
				) {
			user.setUpdateDate((new Timestamp((new Date()).getTime())));
			user.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
			this.userDetailsService.registerUser(user);
		} else {
			throw new Exception("Old and new Password dont match");
		}
		
		return new Status("Success");
	}
	
	@PutMapping(value = "/forgotpassword/{userName}")
	@Transactional
	public Status forgotPassword(@PathVariable String userName) throws Exception {
		User user = this.userDetailsService.findUserByUserId(userName).get();
		
		String tempPwd = user.getUserId()+"@$"+user.getEmail().hashCode()+"@";
		user.setPassword(passwordEncoder.encode(tempPwd));
		this.userDetailsService.updateUser(user);
		
		amazonSesClient.sendEmailForTempPassword(tempPwd, user.getUserId(), user.getName(), user.getEmail());
		return new Status("Success");
	}
}
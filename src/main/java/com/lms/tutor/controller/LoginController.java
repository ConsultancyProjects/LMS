package com.lms.tutor.controller;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.AuthenticationRequest;
import com.lms.tutor.model.AuthenticationResponse;
import com.lms.tutor.model.User;
import com.lms.tutor.service.UserLoginServiceImpl;
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
	
	@PostMapping(value = "/login")
	public AuthenticationResponse createAuthenticationToken(
			@RequestBody AuthenticationRequest authenticationRequest)
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
	public String registerUser(@RequestBody User user) throws Exception {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		this.userDetailsService.registerUser(user);
		return "Success";
	}
}
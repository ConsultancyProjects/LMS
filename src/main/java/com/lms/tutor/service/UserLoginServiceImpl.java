package com.lms.tutor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lms.tutor.model.MyUserDetails;
import com.lms.tutor.model.User;
import com.lms.tutor.repository.UserRepository;

@Service
public class UserLoginServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserId(userName);
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
		return user.map(MyUserDetails::new).get();
	}
	
	public List<User> findAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	public Optional<User> findUserByUserId(String userName) {
		return userRepository.findByUserId(userName);
	}
	
	public void deleteUser(String userId) {
		userRepository.deleteUserByUserId(userId);
		userRepository.deleteUsersInVideoCategory(userId);
	}

	public void registerUser(User user) throws Exception {
		if (userRepository.findByUserId(user.getUserId()).isPresent()) {
			throw new Exception("Username already exists");
		}
		// so that role cant be assigned
		user.setRole(null);
		userRepository.save(user);
	}

	public Optional<User> getUserByUserName(String userName) {
		return userRepository.findByUserId(userName);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}

	public List<User> getAllUsersWithNoRole() {
		return userRepository.findAllUsersWithNoRole();
	}

	public List<User> getAllUsersWithRoleId(int roleId) {
		return userRepository.findAllByRoleId(roleId);
	}

	public List<User> filterUserByEmail(String emailId) {
		return userRepository.findByEmailStartsWith(emailId);
	}
	

}

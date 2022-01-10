package com.lms.tutor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lms.tutor.model.Role;
import com.lms.tutor.model.User;
import com.lms.tutor.repository.RoleRepository;

@Service
public class RoleServiceImpl {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	UserLoginServiceImpl userLoginServiceImpl;

	public void updateRoleOfUser(int roleId, String userName) {
		Optional<User> user = userLoginServiceImpl.getUserByUserName(userName);
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
		user.get().setRole(this.getRole(roleId).get());
		userLoginServiceImpl.updateUser(user.get());
	}

	public Optional<Role> getRole(int roleId) {
		return roleRepository.findById(roleId);
	}

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

}

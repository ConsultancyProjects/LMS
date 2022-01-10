package com.lms.tutor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.Role;
import com.lms.tutor.model.Status;
import com.lms.tutor.service.RoleServiceImpl;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleServiceImpl roleServiceImpl;

	@GetMapping("/")
	public List<Role> getAllRoles() {
		return roleServiceImpl.getAllRoles();
	}

	@PutMapping("/{roleId}")
	public Status updateRole(@RequestParam String userName, @PathVariable int roleId) {
		roleServiceImpl.updateRoleOfUser(roleId, userName);
		return new Status("Success");
	}

}

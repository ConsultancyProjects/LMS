package com.lms.tutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.tutor.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
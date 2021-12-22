package com.lms.tutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lms.tutor.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(String userId);

	List<User> findAllByRoleId(int roleId);
	
	@Query(value = "select * from lms.user_registration  where role_id is null", nativeQuery=true)
	List<User> findAllUsersWithNoRole();
}
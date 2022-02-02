package com.lms.tutor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.tutor.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUserId(String userId);

	List<User> findAllByRoleId(int roleId);
	
	@Query(value="delete from lms.user_registration b where b.user_id=:userId", nativeQuery=true)
	@Modifying
	void deleteUserByUserId(@Param("userId") String userId);
	
	@Query(value="delete from lms.user_video_category_mapping b where b.user_id=:userId", nativeQuery=true)
	@Modifying
	void deleteUsersInVideoCategory(@Param("userId") String userId);
	
	@Query(value="delete from lms.USER_BATCH_MAPPING b where b.user_id=:userId", nativeQuery=true)
	@Modifying
	void deleteUsersInBatch(@Param("userId") String userId);
	
	@Query(value = "select * from lms.user_registration  where role_id is null", nativeQuery=true)
	List<User> findAllUsersWithNoRole();
	
	@Query(value = "SELECT * FROM lms.user_registration where UPDATE_DATE  < DATE_SUB(NOW(), INTERVAL 30 DAY)", nativeQuery=true)
	List<User> getAllUsersWhosePasswordsAreUpdatedLongBack();
	
	List<User> findByEmailStartsWith(String emailId);
}
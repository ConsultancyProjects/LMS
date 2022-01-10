package com.lms.tutor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.tutor.model.UserBatchMapping;

@Repository
public interface UserBatchMappingRepository extends JpaRepository<UserBatchMapping, Integer> {

	List<UserBatchMapping> findByUserId(String userId);

	List<UserBatchMapping> findByBatchBatchId(int BatchId);

	@Query(value = "delete from lms.user_batch_mapping b where b.user_id=:userId and b.batch_id=:batchId", nativeQuery = true)
	@Modifying
	void deleteEntryForGivenUserAndBatchId(@Param("userId") String userId, @Param("batchId") int batchId);

}
package com.lms.tutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.tutor.model.Batch;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {
	
	Batch findByBatchId(int batchId);
}
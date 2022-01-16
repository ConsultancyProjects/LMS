package com.lms.tutor.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.Status;
import com.lms.tutor.model.UserBatchMapping;
import com.lms.tutor.repository.BatchRepository;
import com.lms.tutor.repository.UserBatchMappingRepository;

@RestController
@PreAuthorize("hasAnyAuthority('ADMIN', 'TUTOR')")
@RequestMapping("/user-batch-mapping")
public class UserBatchMappingController {

	@Autowired
	private UserBatchMappingRepository userBatchMappingRepository;

	@Autowired
	private BatchRepository batchRepository;

	@GetMapping("/{userId}")
	public List<UserBatchMapping> getAllBatchMappingsForUsers(@PathVariable String userId) {
		return userBatchMappingRepository.findByUserId(userId);
	}
	
	@GetMapping("/{batchId}")
	public List<UserBatchMapping> getAllBatchMappingsForUsers(@PathVariable int batchId) {
		return userBatchMappingRepository.findByBatchBatchId(batchId);
	}

	@PostMapping("/{userId}/batch/{batchId}")
	@Transactional
	public UserBatchMapping addUserBatchCategoryMapping(@PathVariable String userId, @PathVariable int batchId) {
		UserBatchMapping ubMapping = new UserBatchMapping();
		ubMapping.setUserId(userId);
		ubMapping.setBatch(batchRepository.findByBatchId(batchId));
		userBatchMappingRepository.save(ubMapping);
		return ubMapping;
	}

	@PutMapping("/{userId}/batch/{batchId}")
	@Transactional
	public UserBatchMapping updateUserBatchMapping(@PathVariable String userId, @PathVariable int batchId) {
		UserBatchMapping ubMapping = new UserBatchMapping();
		ubMapping.setUserId(userId);
		ubMapping.setBatch(batchRepository.findByBatchId(batchId));
		userBatchMappingRepository.save(ubMapping);
		return ubMapping;
	}

	@DeleteMapping("/{userId}/batch/{batchId}")
	public Status deleteUserBatchMapping(@PathVariable String userId, @PathVariable int batchId) {
		userBatchMappingRepository.deleteEntryForGivenUserAndBatchId(userId, batchId);
		return new Status("Success");
	}

}
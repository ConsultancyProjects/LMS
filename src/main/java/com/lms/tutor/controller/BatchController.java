package com.lms.tutor.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.Batch;
import com.lms.tutor.model.Status;
import com.lms.tutor.repository.BatchRepository;
import com.lms.tutor.repository.TimeTableRepository;
import com.lms.tutor.repository.UserBatchMappingRepository;

@RestController
@RequestMapping("/batches")
@PreAuthorize("hasAuthority('ADMIN')")
public class BatchController {

	@Autowired
	private BatchRepository batchRepository;

	@Autowired
	private UserBatchMappingRepository userBatchMappingRepository;

	@Autowired
	private TimeTableRepository timeTableRepository;

	@GetMapping("/")
	public List<Batch> getAllBatches() {
		return batchRepository.findAll();
	}

	@GetMapping("/user/{userId}")
	public List<Batch> getAllBatchesForUser(@PathVariable String userId) {
		return userBatchMappingRepository.findByUserId(userId).stream().map(bm -> bm.getBatch())
				.collect(Collectors.toList());
	}
	
	@GetMapping("/child-vid-cat/{childVidCatId}")
	public List<Batch> getAllBatchesForChildCatgeory(@PathVariable Integer childVidCatId) {
		return batchRepository.findByChildVideoCategoryId(childVidCatId);
	}

	@PostMapping("/")
	public Status addBatch(@RequestBody Batch batch) throws Exception {
		List<Batch> batList = batchRepository.findAll();
		if (batList.contains(batch)) {
			throw new Exception("Batch Name Already Present!");
		}
		batchRepository.save(batch);
		return new Status("Success");
	}

	@DeleteMapping("/{batchId}")
	@Transactional
	public Status deleteBatch(@PathVariable int batchId) throws Exception {
		if (CollectionUtils.isEmpty(userBatchMappingRepository.findByBatchBatchId(batchId))) {
			if (CollectionUtils.isEmpty(timeTableRepository.findByBatchBatchId(batchId))) {
				batchRepository.deleteById(batchId);
			} else {
				throw new Exception("No TimeTable should be associated with a batch that is goint to be deleted");
			}
		} else {
			throw new Exception("No User should be associated in a batch that is goint to be deleted");
		}
		return new Status("Success");
	}

	@PutMapping("/{batchId}")
	public Status updateBatch(@RequestBody Batch batch) {
		batchRepository.save(batch);
		return new Status("Success");
	}

}

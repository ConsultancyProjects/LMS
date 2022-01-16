package com.lms.tutor.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lms.tutor.model.BulkUploadUserBatchMapping;
import com.lms.tutor.model.BulkUploadUserChildCatMapping;
import com.lms.tutor.model.Status;
import com.lms.tutor.model.UserBatchMapping;
import com.lms.tutor.model.UserVideoCategoryMapping;
import com.lms.tutor.repository.BatchRepository;
import com.lms.tutor.repository.ChildVideoCategoryRepository;
import com.lms.tutor.repository.UserBatchMappingRepository;
import com.lms.tutor.repository.UserRepository;
import com.lms.tutor.repository.UserVideoCategoryMappingRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@RestController
@RequestMapping("/bulk-upload")
@PreAuthorize("hasAuthority('ADMIN')")
public class BulkUploadController {

	@Autowired
	private UserBatchMappingRepository userBatchMappingRepository;

	@Autowired
	private BatchRepository batchRepository;

	@Autowired
	private UserVideoCategoryMappingRepository userVideoCategoryMappingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ChildVideoCategoryRepository childVideoCategoryRepository;

	@PostMapping("/userBatchMapping")
	@Transactional
	public Status uploadCSVFileBatchMapping(@RequestParam("userBatchMapping") MultipartFile userBatchMapping)
			throws Exception {

		if (userBatchMapping.isEmpty()) {
			throw new Exception("CSV can't be empty");
		} else {

			try (Reader reader = new BufferedReader(new InputStreamReader(userBatchMapping.getInputStream()))) {
				CsvToBean<BulkUploadUserBatchMapping> csvToBean = new CsvToBeanBuilder<BulkUploadUserBatchMapping>(
						reader).withType(BulkUploadUserBatchMapping.class).withIgnoreLeadingWhiteSpace(true).build();
				List<BulkUploadUserBatchMapping> mappings = csvToBean.parse();
				mappings.forEach(mp -> {
					UserBatchMapping ubMapping = new UserBatchMapping();
					ubMapping.setUserId(mp.getUserId());
					ubMapping.setBatch(batchRepository.findByBatchId(mp.getBatchId()));
					userBatchMappingRepository.save(ubMapping);
				});

			} catch (Exception ex) {
				throw new Exception("Error while processing csv");
			}
		}
		return new Status("Success");

	}

	@PostMapping("/userChildCatMapping")
	@Transactional
	public Status uploadCSVFileChildCatMapping(@RequestParam("userChildCatMapping") MultipartFile userChildCatMapping)
			throws Exception {

		if (userChildCatMapping.isEmpty()) {
			throw new Exception("CSV can't be empty");
		} else {

			try (Reader reader = new BufferedReader(new InputStreamReader(userChildCatMapping.getInputStream()))) {
				CsvToBean<BulkUploadUserChildCatMapping> csvToBean = new CsvToBeanBuilder<BulkUploadUserChildCatMapping>(
						reader).withType(BulkUploadUserChildCatMapping.class).withIgnoreLeadingWhiteSpace(true).build();
				List<BulkUploadUserChildCatMapping> mappings = csvToBean.parse();
				mappings.forEach(mp -> {
					UserVideoCategoryMapping userVideoCategoryMapping = new UserVideoCategoryMapping();
					userVideoCategoryMapping.setChildVideoCategory(
							childVideoCategoryRepository.findByChildCategoryId(mp.getChildCategoryId()));
					userVideoCategoryMapping.setUser(userRepository.findByUserId(mp.getUserId()).get());
					userVideoCategoryMappingRepository.save(userVideoCategoryMapping);
				});

			} catch (Exception ex) {
				throw new Exception("Error while processing csv");
			}
		}
		return new Status("Success");

	}

}

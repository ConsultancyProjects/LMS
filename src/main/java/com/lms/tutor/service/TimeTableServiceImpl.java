package com.lms.tutor.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.tutor.model.TimeTable;
import com.lms.tutor.repository.TimeTableRepository;
import com.lms.tutor.repository.UserBatchMappingRepository;

@Service
public class TimeTableServiceImpl {

	@Autowired
	private TimeTableRepository timeTableRepository;

	@Autowired
	UserBatchMappingRepository userBatchMappingRepository;
	
	public List<TimeTable> getAll() {
		return timeTableRepository.findAll();
	}

	public List<TimeTable> getAllScheduledCourses(Timestamp fromDate) {
		return timeTableRepository.findAllByFromDate(fromDate);
	}
	
	public List<TimeTable> getTimeTableForUser(String userId) {
		List<Integer> batchIds = userBatchMappingRepository.findByUserId(userId)
				.stream().map(bm -> bm.getBatch().getBatchId())
		.collect(Collectors.toList());
		return timeTableRepository.getTimeTableForUser(batchIds);
	}

	public void saveTimeTable(TimeTable timeTable) {
		timeTableRepository.save(timeTable);
	}
}

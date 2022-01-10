package com.lms.tutor.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.tutor.model.TimeTable;
import com.lms.tutor.repository.TimeTableRepository;

@Service
public class TimeTableServiceImpl {

	@Autowired
	private TimeTableRepository timeTableRepository;

	public List<TimeTable> getAll() {
		return timeTableRepository.findAll();
	}

	public List<TimeTable> getAllScheduledCourses(Timestamp fromDate) {
		return timeTableRepository.findAllByFromDate(fromDate);
	}

	public void saveTimeTable(TimeTable timeTable) {
		timeTableRepository.save(timeTable);

	}
}

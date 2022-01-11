package com.lms.tutor.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.TimeTable;
import com.lms.tutor.service.TimeTableServiceImpl;

@RestController
@RequestMapping("/timetable")
public class TimeTableController {
	
	@Autowired
	private TimeTableServiceImpl timeTableServiceImpl;
	
	//1639761223000 get timetable for based on date 
	@GetMapping("/") 
	public List<TimeTable> getTimeTableFor(@RequestParam long fromDate) {
		//return timeTableServiceImpl.getAll();
		return timeTableServiceImpl.getAllScheduledCourses(new Timestamp(fromDate));
	}
	
	
	@PostMapping("/")
	@PreAuthorize("hasAuthority('ADMIN')")
	public TimeTable addTimeTable(@RequestBody TimeTable timeTable) {
		timeTableServiceImpl.saveTimeTable(timeTable);
		return timeTable;
	}
	
	@PutMapping("/")
	@PreAuthorize("hasAuthority('ADMIN')")
	public TimeTable updateTimeTable(@RequestBody TimeTable timeTable) {
		timeTableServiceImpl.saveTimeTable(timeTable);
		return timeTable;
	}
	
	
	

}

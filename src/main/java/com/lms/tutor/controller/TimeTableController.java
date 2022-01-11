package com.lms.tutor.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.TimeTable;
import com.lms.tutor.service.TimeTableServiceImpl;
import com.lms.tutor.util.S3Util;

@RestController
@RequestMapping("/timetable")
public class TimeTableController {
	
	@Autowired
	private TimeTableServiceImpl timeTableServiceImpl;
	
	@Autowired
	private S3Util s3Util;
	
	//1639761223000 get timetable for based on date 
	@GetMapping("/") 
	public List<TimeTable> getTimeTableFor(@RequestParam long fromDate) {
		//return timeTableServiceImpl.getAll();
		return changeVideoUrlToPresigned(timeTableServiceImpl.getAllScheduledCourses(new Timestamp(fromDate)));
	}
	
	
	@GetMapping("/student/{userId}") 
	public List<TimeTable> getTimeTableForStudent(@PathVariable String userId) {
		return changeVideoUrlToPresigned(timeTableServiceImpl.getTimeTableForUser(userId));
	}
	
	@PostMapping("/")
	@PreAuthorize("hasAuthority('ADMIN')")
	public TimeTable addTimeTable(@RequestBody TimeTable timeTable) {
		timeTableServiceImpl.saveTimeTable(timeTable);
		timeTable.getVideo().setS3Path(s3Util.generatePresignedUrl(timeTable.getVideo().getS3Path()));
		return timeTable;
	}
	
	@PutMapping("/")
	@PreAuthorize("hasAuthority('ADMIN')")
	public TimeTable updateTimeTable(@RequestBody TimeTable timeTable) {
		timeTableServiceImpl.saveTimeTable(timeTable);
		timeTable.getVideo().setS3Path(s3Util.generatePresignedUrl(timeTable.getVideo().getS3Path()));
		return timeTable;
	}
	
	
	private List<TimeTable> changeVideoUrlToPresigned(List<TimeTable> timeTable) {
		if (!CollectionUtils.isEmpty(timeTable)) {
			timeTable.forEach(ttb -> {
				ttb.getVideo().setS3Path(s3Util.generatePresignedUrl(ttb.getVideo().getS3Path()));
			});
		}
		return timeTable;
	}

}

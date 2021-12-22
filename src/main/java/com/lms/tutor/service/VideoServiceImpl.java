package com.lms.tutor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.tutor.model.Video;
import com.lms.tutor.repository.VideoRepository;

@Service
public class VideoServiceImpl {
	
	@Autowired
	private VideoRepository videoRepository;

	public void updateVideoMethaData(Video video) {
		 videoRepository.save(video);
	}

}

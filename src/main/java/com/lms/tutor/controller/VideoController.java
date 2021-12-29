package com.lms.tutor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.Video;
import com.lms.tutor.repository.VideoRepository;
import com.lms.tutor.util.S3Util;

@RestController
@RequestMapping("/videos")
public class VideoController {
	
	@Autowired
	VideoRepository videoRepository;
	
	@Autowired
	S3Util s3Util;
	
	@GetMapping("/{videoId}/metadata/")
	public Video getVideoMetaData(@PathVariable(value = "videoId") int videoId) {
		Optional<Video> video = videoRepository.findById(videoId);
		video.ifPresent(v->v.setS3Path(s3Util.generatePresignedUrl(v.getS3Path())));
		return video.get();
	}
	
	@GetMapping("/all/metadata/")
	public List<Video> getAllVideosMetaData() {
		List<Video> videoData = videoRepository.findAll();
		videoData.forEach(video->{
			video.setS3Path(s3Util.generatePresignedUrl(video.getS3Path()));
		});
		return videoData;
	} 
	
	@GetMapping("/all/metadata/{categoryId}")
	public List<Video> findAllVideosThatBelongToParentCategory(@PathVariable(value = "categoryId") Integer categoryId) {
		List<Video> videoData = videoRepository.findAllVideosThatBelongToParentCategory(categoryId);
		videoData.forEach(video->{
			video.setS3Path(s3Util.generatePresignedUrl(video.getS3Path()));
		});
		return videoData;
	}
	
	@GetMapping("/metadata/{categoryId}")
	public Video getVideosMetaThatBelongToChildCategoryId(@PathVariable(value = "categoryId") Integer categoryId) {
		Video video = videoRepository.findByCategoryChildCategoryId(categoryId);
		if (null != video) {
			video.setS3Path(s3Util.generatePresignedUrl(video.getS3Path()));
		}
		return video;
	}
	
	@PostMapping("/metadata")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String addVideoMetaData(@RequestBody Video video) {
		videoRepository.save(video);
		return "Success";
	}
	
}

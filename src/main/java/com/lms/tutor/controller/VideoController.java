package com.lms.tutor.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.tutor.model.Video;
import com.lms.tutor.repository.TimeTableRepository;
import com.lms.tutor.repository.UserVideoCategoryMappingRepository;
import com.lms.tutor.repository.VideoRepository;
import com.lms.tutor.util.S3Util;

@RestController
@RequestMapping("/videos")
public class VideoController {

	@Autowired
	VideoRepository videoRepository;

	@Autowired
	S3Util s3Util;

	@Autowired
	TimeTableRepository timeTableRepository;

	@Autowired
	private UserVideoCategoryMappingRepository userVideoCategoryMappingRepository;

	@GetMapping("/{videoId}/metadata/")
	public Video getVideoMetaData(@PathVariable(value = "videoId") int videoId) {
		Optional<Video> video = videoRepository.findById(videoId);
		video.ifPresent(v -> v.setS3Path(s3Util.generatePresignedUrl(v.getS3Path())));
		return video.get();
	}

	@GetMapping("/all/metadata/")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Video> getAllVideosMetaData() {
		return changeVideoUrlToPresigned(videoRepository.findAll());
	}

	@GetMapping("/all/metadata/{categoryId}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'TUTOR')")
	public List<Video> findAllVideosThatBelongToParentCategory(@PathVariable(value = "categoryId") Integer categoryId) {
		return changeVideoUrlToPresigned(videoRepository.findAllVideosThatBelongToParentCategory(categoryId));
	}

	@GetMapping("/all/metadata/batch/{batchId}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'TUTOR', 'STUDENT')")
	public List<Video> findAllHistoryVideosThatBelongToBatch(@PathVariable(value = "batchId") Integer batchId) {
		//Todo: might be u can chk logged in user is student and query for diff batch then reject request.
		List<Video> videoData = timeTableRepository.findAllHistoryDataForBatch(batchId).stream()
				.map(ttb -> ttb.getVideo()).collect(Collectors.toList());
		return changeVideoUrlToPresigned(videoData);
	}

	@GetMapping("/all/metadata/tutor/{userId}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'TUTOR')")
	@SuppressWarnings("unchecked")
	public List<Video> getAllOfflineVideosForTutor(@PathVariable String userId) {
		List<Integer> catIds = (List<Integer>) userVideoCategoryMappingRepository.findByUserUserId(userId).stream()
				.map(cat -> cat.getChildVideoCategory().getChildCategoryId());
		return changeVideoUrlToPresigned(videoRepository.findVideosThatBelongToChildCategoryIn(catIds));
	}

	@GetMapping("/metadata/{categoryId}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'TUTOR')")
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
	
	private List<Video> changeVideoUrlToPresigned(List<Video> videoData) {
		if (!CollectionUtils.isEmpty(videoData)) {
			videoData.forEach(video -> {
				video.setS3Path(s3Util.generatePresignedUrl(video.getS3Path()));
			});
		}
		return videoData;
	}

}

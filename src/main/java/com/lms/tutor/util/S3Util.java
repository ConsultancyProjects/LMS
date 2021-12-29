package com.lms.tutor.util;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;

@Service
public class S3Util {

	@Autowired
	private AmazonS3 amazonS3Client;
	

	@Value("${s3.bucket.name}")
	private String s3BucketName;

	private String generateUrl(String fileName, HttpMethod httpMethod) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, 3); // Generated URL will be valid for 24 hours
		return amazonS3Client.generatePresignedUrl(s3BucketName, fileName, calendar.getTime(), httpMethod).toString();
	}

	@Async
	public String generatePresignedUrl(String fileName) {
		if (!amazonS3Client.doesObjectExist(s3BucketName, fileName)) {
			return "File does not exist";
		}
		return generateUrl(fileName, HttpMethod.GET);
	}

	@Async
	public String save(String extension) {
		String fileName = UUID.randomUUID().toString() + extension;
		return generateUrl(fileName, HttpMethod.PUT);
	}

}

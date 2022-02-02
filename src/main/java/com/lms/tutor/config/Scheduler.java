package com.lms.tutor.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.lms.tutor.model.User;
import com.lms.tutor.repository.UserRepository;
import com.lms.tutor.util.AmazonSesClient;

@EnableScheduling
@Configuration
public class Scheduler {
	
	@Autowired
	AmazonSesClient amazonSesClient;
	
	@Autowired
	UserRepository userRepository;

	// runs every day
	@Scheduled(fixedDelay = 1000*60*60*24)
	public void scheduleFixedDelayTask() {
		List<User> userList = userRepository.getAllUsersWhosePasswordsAreUpdatedLongBack();
		for (User user : userList) {
			try {
				amazonSesClient.sendPasswordExpiryMail(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	   /* System.out.println(
	      "Fixed delay task - " + System.currentTimeMillis() / 1000);*/
	}
}

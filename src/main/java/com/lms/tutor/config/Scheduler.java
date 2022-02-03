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
	@Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
	public void scheduleFixedDelayTask() {
		List<User> userList = userRepository.getAllUsersWhosePasswordsAreUpdatedLongBack();
		for (User user : userList) {
			try {
				amazonSesClient.sendPasswordExpiryMail(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// runs every 5min
	@Scheduled(fixedDelay = 1000 * 60 * 5)
	public void scheduleToSendTempPwdMailTask() {
		List<String> emailList = amazonSesClient.getUnverifiedEmailList();
		if (emailList.size() > 0) {
			List<User> userList = userRepository.getCreatedUsersWhosePwdIsNotSet();
			userList.stream().filter(user -> emailList.contains(user.getEmail())).forEach(user -> {
				String tempPwd = user.getUserId() + "@$" + user.getEmail().hashCode() + "@";
				try {
					amazonSesClient.sendEmailForTempPassword(tempPwd, user.getUserId(), user.getName(),
							user.getEmail());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}
}

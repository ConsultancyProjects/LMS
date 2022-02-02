package com.lms.tutor.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms.tutor.model.MyUserDetails;
import com.lms.tutor.model.Status;
import com.lms.tutor.model.User;
import com.lms.tutor.model.UserBatchMapping;
import com.lms.tutor.model.UserVideoCategoryMapping;
import com.lms.tutor.repository.BatchRepository;
import com.lms.tutor.repository.ChildVideoCategoryRepository;
import com.lms.tutor.repository.UserBatchMappingRepository;
import com.lms.tutor.repository.UserRepository;
import com.lms.tutor.repository.UserVideoCategoryMappingRepository;
import com.lms.tutor.util.AmazonSesClient;

@Service
public class UserLoginServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserBatchMappingRepository userBatchMappingRepository;

	@Autowired
	private BatchRepository batchRepository;
	
	@Autowired
	private UserVideoCategoryMappingRepository userVideoCategoryMappingRepository;

	@Autowired
	private ChildVideoCategoryRepository childVideoCategoryRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	AmazonSesClient amazonSesClient;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserId(userName);
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
		return user.map(MyUserDetails::new).get();
	}

	public List<User> findAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	public Optional<User> findUserByUserId(String userName) {
		Optional<User> user = userRepository.findByUserId(userName);
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
		return user;
	}

	@Transactional
	public void deleteUser(String userId) {
		userRepository.deleteUserByUserId(userId);
		userRepository.deleteUsersInVideoCategory(userId);
		userRepository.deleteUsersInBatch(userId);
	}

	public void registerUser(User user) throws Exception {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (userRepository.findByUserId(user.getUserId()).isPresent()) {
			throw new Exception("UserId already exists");
		}
		userRepository.save(user);
	}
	
	@Transactional
	public void addUser(User user) throws Exception {
		if (userRepository.findByUserId(user.getUserId()).isPresent()) {
			throw new Exception("UserId already exists");
		}
		String tempPwd = user.getUserId()+"@$"+user.getEmail().hashCode()+"@";
		user.setPassword(passwordEncoder.encode(tempPwd));
		
		userRepository.save(user);
		user.getBatches().forEach(batch->{
			addUserBatchCategoryMapping(user.getUserId(), batch.getBatchId());
		});
		user.getCategories().forEach(cat->{
			addUserVideoCategoryMapping(user.getUserId(), cat.getChildCategoryId());
		});
		amazonSesClient.sendEmailForTempPassword(tempPwd, user.getUserId(), user.getName(), user.getEmail());
	}
	
	@Transactional
	public UserBatchMapping addUserBatchCategoryMapping(String userId, int batchId) {
		UserBatchMapping ubMapping = new UserBatchMapping();
		ubMapping.setUserId(userId);
		ubMapping.setBatch(batchRepository.findByBatchId(batchId));
		userBatchMappingRepository.save(ubMapping);
		return ubMapping;
	}
	
	@Transactional
	public Status addUserVideoCategoryMapping(String userId, Integer childCatId) {
		UserVideoCategoryMapping userVideoCategoryMapping = new UserVideoCategoryMapping();
		userVideoCategoryMapping.setChildVideoCategory(childVideoCategoryRepository
				.findByChildCategoryId(childCatId));
		userVideoCategoryMapping
				.setUser(userRepository.findByUserId(userId).get());
		userVideoCategoryMappingRepository.save(userVideoCategoryMapping);
		return new Status("Success");
	}
	
	

	public Optional<User> getUserByUserName(String userName) {
		return userRepository.findByUserId(userName);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}

	public List<User> getAllUsersWithNoRole() {
		return userRepository.findAllUsersWithNoRole();
	}

	public List<User> getAllUsersWithRoleId(int roleId) {
		return userRepository.findAllByRoleId(roleId);
	}

	public List<User> filterUserByEmail(String emailId) {
		return userRepository.findByEmailStartsWith(emailId);
	}

}

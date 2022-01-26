package com.lms.tutor.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
		if (userRepository.findByUserId(user.getUserId()).isPresent()) {
			throw new Exception("UserId already exists");
		}
		userRepository.save(user);
	}
	
	public void addUser(User user) throws Exception {
		if (userRepository.findByUserId(user.getUserId()).isPresent()) {
			throw new Exception("UserId already exists");
		}
		userRepository.save(user);
		user.getBatches().forEach(batch->{
			addUserBatchCategoryMapping(user.getUserId(), batch.getBatchId());
		});
		user.getCategories().forEach(cat->{
			addUserVideoCategoryMapping(user.getUserId(), cat.getChildCategoryId());
		});
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

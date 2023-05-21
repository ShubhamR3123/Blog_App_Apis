package com.shubhamit.blog_app_api.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shubhamit.blog_app_api.entity.Role;
import com.shubhamit.blog_app_api.entity.User;
import com.shubhamit.blog_app_api.exception.ResourceNotFoundException;
import com.shubhamit.blog_app_api.helper.AppConstants;
import com.shubhamit.blog_app_api.payloads.UserDto;
import com.shubhamit.blog_app_api.repository.RoleRepository;
import com.shubhamit.blog_app_api.repository.UserRepository;
import com.shubhamit.blog_app_api.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;
	/**
	 * *@author Shubham
	 * 
	 * @apiNote This Method is used to Register
	 * @param userDto
	 * @return
	 */
	@Override
	public UserDto registerNewUser(UserDto userDto) {
		log.info("Initiate Dao call for Register");
		User user = this.modelMapper.map(userDto, User.class);

		// password incode
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// getting role
		Role role = this.roleRepository.findById(AppConstants.USER_ADMIN).get();

		user.getRoles().add(role);
		User user2 = this.userRepo.save(user);
		log.info("Completed Dao call for Register");
		return this.modelMapper.map(user2, UserDto.class);
	}


	
	
	/**
	 * *@author Shubham
	 * 
	 * @apiNote This Method is used to create user details
	 * @param userDto
	 * @return
	 */
	@Override
	public UserDto createUser(UserDto userDto) {

		// conveet userDto to Entity
		// User user = this.dtoToUser(userDto);
		// User savedUser= this.userRepo.save(user);
		// return this.userToDto(savedUser);

		// ModelMapper-One bject convert into another Object
		log.info("Initiate Dao call for create User Details");
		User user = modelMapper.map(userDto, User.class);
		User savedUser = userRepo.save(user);
		UserDto dto = modelMapper.map(savedUser, UserDto.class);
		log.info("Completed Dao Call for create User Details");
		return dto;

	}

	/**
	 * @author Shubham
	 * @apiNote This Method is used for update user details
	 * @param userDto
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public UserDto updateUser(UserDto userDto, Long userId) {
		log.info("Initiate Dao call for update User Details with userId:{}", userId);
		User user2 = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId, "User", userId));
		user2.setUserId(userDto.getUserId());
		user2.setUserName(userDto.getUserName());
		user2.setEmail(userDto.getEmail());
		user2.setPassword(userDto.getPassword());
		user2.setAbout(userDto.getAbout());
		User updatedUser = userRepo.save(user2);
		log.info("Completed Dao call for update User Details with userId:{}", userId);
		return modelMapper.map(updatedUser, UserDto.class);
	}

	/**
	 * @author Shubham
	 * @apiNote This Method is used for get single user
	 * @param UserId
	 * @return
	 */

	@Override
	public UserDto getUserById(Long userId) {
		log.info("Initiate Dao Call for get single User Details with userId:{}", userId);
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId, "User", userId));
		log.info("Completed Dao call for get single User Details with userId:{}", userId);
		return modelMapper.map(user, UserDto.class);
	}

	/**
	 * @author Shubham
	 * @apiNote This Method is used for get all users
	 * @return
	 */
	@Override
	public List<UserDto> getAllUsers() {
		log.info("Initiate Dao call for get All User Details");
		List<User> allUsers = userRepo.findAll();
		List<UserDto> list = allUsers.stream().map((users) -> modelMapper.map(users, UserDto.class))
				.collect(Collectors.toList());
		log.info("Completed Dao call for get All User Details");
		return list;
	}

	/**
	 * @author Shubham
	 * @apiNote This Method is used for delete user
	 * @param UserId
	 * @return
	 */
	@Override
	public void deleteUser(Long userId) {
		log.info("Initiate Dao call for delete User Details With userID:{},userId");
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_DELETED + userId, "User", userId));
		log.info("Completed Dao call for delete User Details With userID:{},userId");
		userRepo.delete(user);
	}

	//	private User dtoToUser(UserDto userDto) {
//		User user=new User();
//	user.setUserId(userDto.getUserId());
//	user.setUserName(userDto.getUserName());
//	user.setEmail(userDto.getEmail());
//	user.setPassword(userDto.getPassword());
//	user.setAbout(userDto.getAbout());
//		return user;
//	
//	}
//	
//	private UserDto userToDto(User user) {
//		
//		UserDto userDto=new UserDto();
//		userDto.setUserId(user.getUserId());
//		userDto.setUserName(user.getUserName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
//		
//		
//		return userDto;
//		
//	}
//	}
//
}
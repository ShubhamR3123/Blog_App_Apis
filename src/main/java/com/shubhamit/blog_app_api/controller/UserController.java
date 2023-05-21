package com.shubhamit.blog_app_api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubhamit.blog_app_api.helper.AppConstants;
import com.shubhamit.blog_app_api.payloads.UserDto;
import com.shubhamit.blog_app_api.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;

	
	
	/**
	 * *@author Shubham
	 * @apiNote This Api is used to create user details
	 * @param userDto
	 * @return
	 */
	
	
	@PostMapping("/user")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		log.info("Initiated request for create User details");
		UserDto saveUser = userService.createUser(userDto);
		log.info("Completed request for create User details");
		return new ResponseEntity<UserDto>( saveUser,HttpStatus.CREATED);
		
	}
	
	/**
	 * @author Shubham
	 * @apiNote This api is used for update user details
	 * @param userDto
	 * 
	 * @param userId
	 * @return
	 */
	
	@PutMapping("/user/{id}")
	public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("id")Long userId){
		log.info("Initiated request for update User details with userId:{}",userId);
		UserDto updateUser = userService.updateUser(userDto, userId);
		log.info("Completed request for update User details with userId:{}",userId);
		return new ResponseEntity<UserDto>(updateUser,HttpStatus.OK);
		
	}
	
	/**
	 * @author Shubham
	 * @apiNote This api is used for get single user 
	 * @param UserId
	 * @return
	 */
	@GetMapping("user/{id}")
	public ResponseEntity<UserDto>getSingleUser(@PathVariable("id") Long userId){
		log.info("Initiated request for get single User details with userId:{}",userId);
		UserDto userById = userService.getUserById(userId);
		log.info("Completed request for get single User details with userId:{}",userId);		
		return new ResponseEntity<UserDto>(userById,HttpStatus.OK);
		
	}
	
	/**
	 * @author Shubham
	 * @apiNote This api is used for get all users
	 * @return
	 */
	@GetMapping("/user")
	public ResponseEntity<List<UserDto>>getAllUsers(){
		log.info("Initiated request for get all User details");
		List<UserDto> allUsers = userService.getAllUsers();
		log.info("Completed request for get all User details");
		return new ResponseEntity<>(allUsers,HttpStatus.OK);
		
	}
	
	/**
	 * @author Shubham
	 * @apiNote This api is used for delete user
	 * @param UserId
	 * @return
	 */
	//Delete User 
	//Admin
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/user/{id}")
	public ResponseEntity<String>delteUser(@PathVariable("id") Long userId){
		log.info("Initiated request for delete User details with userId:{}",userId);
		userService.deleteUser(userId);
		log.info("Completed request for delete User details with userId:{}",userId);
		return new ResponseEntity<String>(AppConstants.USER_DELETED,HttpStatus.OK);
		
	}
	
}

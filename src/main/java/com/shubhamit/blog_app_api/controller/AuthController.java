package com.shubhamit.blog_app_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubhamit.blog_app_api.payloads.JwtAuthRequest;
import com.shubhamit.blog_app_api.payloads.JwtAuthResponse;
import com.shubhamit.blog_app_api.payloads.UserDto;
import com.shubhamit.blog_app_api.security.JwtTokenHelper;
import com.shubhamit.blog_app_api.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	/**
	 * @author Shubham
	 * @apiNote This api is used to Login
	 * @param request
	 * @return
	 */

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		log.info("Initiated request for Login");
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String generateToken = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(generateToken);
		log.info("Completed request for Login");
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {
			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {

			System.out.println("Invalid !!");
			throw new Exception("Invalid username or assword");
		}
	}

	/**
	 * @author Shubham
	 * @apiNote This Api is used to Register
	 * @param userDto
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto) {
		log.info("Initiated request for Register ");
		UserDto newUser = this.userService.registerNewUser(userDto);
		log.info("Completed request for Register ");
		return new ResponseEntity<UserDto>(newUser, HttpStatus.CREATED);
	}
}

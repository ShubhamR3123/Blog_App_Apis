package com.shubhamit.blog_app_api.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.shubhamit.blog_app_api.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	
	private Long userId;
	
	@NotNull 
	@Size(min = 4,message = "Username Must be Min 4 Chars...!!")
	private String userName;
	
	@Email
	private String email;
	
	@NotEmpty
	@Size(min = 3,max = 10,message = "Min 3 chars and Max 10 Chars..!!")
	private String password;
	
	@Size(min = 15,max = 100,message = "Min 10 Chars and Max 100 Chars...!!")
	private String about;
	
	private Set<RoleDto>roles=new HashSet<>();
}

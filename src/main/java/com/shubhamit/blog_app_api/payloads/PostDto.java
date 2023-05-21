package com.shubhamit.blog_app_api.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	
	private Integer postId;
	
	@NotEmpty
	@Size(min = 5,message ="Min of 5 Chars")
	private String  title;
	
	@NotEmpty
	@Size(min =5,max = 10,message = "Content Min 5 Chars and Max 10 Chars...!!")
	private  String content;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	@NotEmpty
	private UserDto user;
	
	private Set<CommentDto>comments=new HashSet<>();

}

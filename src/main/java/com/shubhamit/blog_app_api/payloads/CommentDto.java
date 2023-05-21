package com.shubhamit.blog_app_api.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

	private int id;
	
	private String content;
}

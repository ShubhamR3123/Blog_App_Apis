package com.shubhamit.blog_app_api.payloads;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CategoryDto {

	private Integer categoryId;
	
	@NotNull
	@Size(min = 4,max = 100,message = "Title Must be 4 chars and max 100 chars..!!")
	private String categoryTitle;
	
	private String categoryDescription;
	
	
}

package com.shubhamit.blog_app_api.exception;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	
	String resourceName;
	
	String fieldName;
	 static Long filedValue;

	public ResourceNotFoundException(String resourceName, String fieldName, Long filedValue) {
		super(String.format("%s not Found  with %s:%s", resourceName,fieldName,filedValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.filedValue = filedValue;
	}
	public ResourceNotFoundException(String resourceName2, String fieldName2, Integer categoryId) {
		super(String.format("%s not Found  with %s:%s", resourceName2,fieldName2,filedValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.filedValue = filedValue;	}
	
	

	 
}

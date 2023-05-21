package com.shubhamit.blog_app_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shubhamit.blog_app_api.entity.Category;
import com.shubhamit.blog_app_api.entity.Post;
import com.shubhamit.blog_app_api.entity.User;

public interface PostRepository  extends JpaRepository<Post,Integer>{
	
	List<Post>findAllByUser(User user);
	
	List<Post>findByCategory(Category category);
	
		List<Post>findByTitleContaining(String title);

}

package com.shubhamit.blog_app_api.service;

import java.util.List;

import com.shubhamit.blog_app_api.payloads.PostDto;
import com.shubhamit.blog_app_api.payloads.PostResponse;

public interface PostService {

	// create post
	PostDto createPost(PostDto postDto, Long userId, Integer categoryId);

	// update post
	PostDto updatePost(PostDto postDto, Integer postId);

	// delete
	void deletePost(Integer postId);

	// getAll Posts
	PostResponse getAllPost(Integer pageSize,Integer pageNumber,String sortBy,String sortDir);

	// get single post
	PostDto getPostById(Integer postId);

	// get all posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);

	// get all posts by user
	List<PostDto> getPostsByUser(Long userId);

	// serach posts
	List<PostDto> searchPosts(String Keyword);

}

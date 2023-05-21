package com.shubhamit.blog_app_api.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shubhamit.blog_app_api.entity.Category;
import com.shubhamit.blog_app_api.entity.Post;
import com.shubhamit.blog_app_api.entity.User;
import com.shubhamit.blog_app_api.exception.ResourceNotFoundException;
import com.shubhamit.blog_app_api.helper.AppConstants;
import com.shubhamit.blog_app_api.payloads.PostDto;
import com.shubhamit.blog_app_api.payloads.PostResponse;
import com.shubhamit.blog_app_api.repository.CategoryRepo;
import com.shubhamit.blog_app_api.repository.PostRepository;
import com.shubhamit.blog_app_api.repository.UserRepository;
import com.shubhamit.blog_app_api.service.PostService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private UserRepository userRepository;

	/**
	 * @author Shubham
	 * @apiNote This Method is used to create posts
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return
	 */
	@Override
	public PostDto createPost(PostDto postDto, Long userId, Integer categoryId) {
		log.info("Initiate Dao Call for create Post Details");
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId, "User", userId));
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId,
						"Category", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost = this.postRepository.save(post);
		log.info("Completed Dao Call for create Post Details");
		return this.modelMapper.map(newPost, PostDto.class);
	}

	/**
	 * @author Shubham
	 * @apiNote This Method is used to Update Post Details
	 * @param postDto
	 * @param postId
	 * @return
	 */

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		log.info("Initiate Dao Call for update Post Details with postId:{}", postId);
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedpost = this.postRepository.save(post);
		log.info("Completed Dao Call for update Post Details with postId:{}",postId);
		return this.modelMapper.map(updatedpost, PostDto.class);
	}

	
	@Override
	public void deletePost(Integer postId) {
		log.info("Initiate Dao Call for Delete Post Details with postId:{}",postId);
		Post post = this.postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND + postId, "post id", postId));
		log.info("Completed Dao Call for Delete Post Details with postId:{}",postId);
		this.modelMapper.map(post, PostDto.class);
	}

	/**
	 * @author Shubham
	 * @apiNote This Method is used to get all Post Details
	 * @param pageSize
	 * @param pageNumber
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */
	@Override
	public PostResponse getAllPost(Integer pageSize, Integer pageNumber, String sortBy, String sortDir) {
		log.info("Initiate Dao Call for getll All Post Details");
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		Page<Post> pagePost = this.postRepository.findAll(p);
		List<Post> allposts = pagePost.getContent();
		List<PostDto> postDtos = allposts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());

		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		log.info("Completed Dao Call for getll All Post Details");
		return postResponse;
	}

	/**
	 * @author Shubham
	 * @apiNote This Method is used to get single Post
	 * @param postId
	 * @return
	 */
	@Override
	public PostDto getPostById(Integer postId) {
		log.info("Initiate Dao Call for getll All Post Details with postId:{}",postId);
		Post post = this.postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND + postId, "Post id", postId));
		log.info("Completed Dao Call for getll All Post Details with postId:{}",postId);
		return this.modelMapper.map(post, PostDto.class);
	}
	/**
	 * @author Shubham
	 * @apiNote This Method is used to get Post by categoryId
	 * @param categoryId
	 * @return
	 */

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		log.info("Initiate Dao Call for get posts by category with categoryId:{}",categoryId);
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND +categoryId,
						"category", categoryId));
		List<Post> posts = this.postRepository.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed Dao Call for get posts by category with categoryId:{}",categoryId);
		return postDtos;
	}
	/**
	 * @author Shubham
	 * @apiNote This Method is Used to get post by user
	 * @param userId
	 * @return
	 */

	@Override
	public List<PostDto> getPostsByUser(Long userId) {
		log.info("Initiate Dao Call for get posts by User with userId:{}",userId);
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId, "user", userId));
		List<Post> posts = this.postRepository.findAllByUser(user);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed Dao Call for get posts by User with userId:{}",userId);
		return postDtos;
	}

	// Search
	/**
	 * @author Shubham
	 * @apiNote This Method is used to Serach Post by Title
	 * @param keyword
	 * @return
	 */
	@Override
	public List<PostDto> searchPosts(String keyword) {
		log.info("Initiated Dao Call for search Posts  Details with keyword:{}",keyword);
		List<Post> posts = this.postRepository.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed Dao Call for search Posts  Details with keyword:{}",keyword);
		return postDtos;
	}

}
package com.shubhamit.blog_app_api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shubhamit.blog_app_api.helper.AppConstants;
import com.shubhamit.blog_app_api.payloads.ApiResponse;
import com.shubhamit.blog_app_api.payloads.PostDto;
import com.shubhamit.blog_app_api.payloads.PostResponse;
import com.shubhamit.blog_app_api.service.FileService;
import com.shubhamit.blog_app_api.service.PostService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// create Post
	/**
	 * @author Shubham
	 * @apiNote This api is used to create posts
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return
	 */

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Long userId,
			@PathVariable Integer categoryId) {
		log.info("Initiated request for create Post details");
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		log.info("Completed request for create Post details");
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

	}

	/**
	 * @author Shubham
	 * @apiNote This api is used to Update Post Details
	 * @param postDto
	 * @param postId
	 * @return
	 */

	// update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		log.info("Initiated request for update Post details with postId:{}", postId);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		log.info("Completed request for update Post details with postId:{}", postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	/**
	 * @author Shubham
	 * @apiNote This Api is used to get all Post Details
	 * @param pageSize
	 * @param pageNumber
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */

	// get AllPosts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		log.info("Initiated request for get all Post details");
		PostResponse postResponse = this.postService.getAllPost(pageSize, pageNumber, sortBy, sortDir);
		log.info("Completed request for get all Post details");
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	/**
	 * @author Shubham
	 * @apiNote This api is used to get single Post
	 * @param postId
	 * @return
	 */

	// get post by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		log.info("Initiated request for get single Post details with postId:{}", postId);
		PostDto postById = this.postService.getPostById(postId);
		log.info("Completed request for get single Post details with postId:{}", postId);
		return new ResponseEntity<PostDto>(postById, HttpStatus.OK);
	}

	// delete post
	/**
	 * @author Shubham
	 * @apiNote This Api is used to Delete Post
	 * @param postId
	 * @return
	 */
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		log.info("Initiated request for delete Post details with postId:{}", postId);
		this.postService.deletePost(postId);
		log.info("Completed request for delete Post details with postId:{}", postId);
		return new ApiResponse("post Deleted Successfully", true);
	}
//	@DeleteMapping("/posts/{postId}")
//	public ResponseEntity<String>deletePost(@PathVariable Integer postId){
//		this.postService.deletePost(postId);
//		return new ResponseEntity<String>(AppConstants.POST_DELETED,HttpStatus.OK);
//	}

//get by user

	/**
	 * @author Shubham
	 * @apiNote This api is Used to get post by user
	 * @param userId
	 * @return
	 */
	@GetMapping("/user/users/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId) {
		log.info("Initiated request for get Post by User with userId:{}", userId);
		List<PostDto> posts = this.postService.getPostsByUser(userId);
		log.info("Completed request for get Post by User with userId:{}", userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}

	// get by category
	/**
	 * @author Shubham
	 * @apiNote This api is used to get Post by categoryId
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/user/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
		log.info("Initiated request for get Post by Category with categoryId:{}", categoryId);
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
		log.info("Completed request for get Post by Category with categoryId:{}", categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}

	// Search
	/**
	 * @author Shubham
	 * @apiNote This api is used to Serach Post by Title
	 * @param keyword
	 * @return
	 */
	@GetMapping("posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> serachPostByTitle(@PathVariable("keyword") String keyword) {
		log.info("Initiated request for get Post by Category with keyword:{}", keyword);
		List<PostDto> result = this.postService.searchPosts(keyword);
		log.info("Completed request for get Post by Category with keyword:{}", keyword);
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);

	}

	// Uplod Image
	/**
	 * @author Shubham
	 * @apiNote This api is used to uplodImage
	 * @param image
	 * @param postId
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/post/image/uplod/{postId}")
	public ResponseEntity<PostDto> uplodPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {
		log.info("Initiated request for uplodImage with postId:{}", postId);
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uplodImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		log.info("Completed request for uplodImage with postId:{}", postId);

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	/**
	 * @apiNote This api is used to DownloadIamge
	 */
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {
		log.info("Initiated request for downloadImage with imageName:{}", imageName);
		InputStream resource = this.fileService.getResource(path, imageName);
		log.info("Completed request for downloadImage with imageName:{}", imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());

	}

}

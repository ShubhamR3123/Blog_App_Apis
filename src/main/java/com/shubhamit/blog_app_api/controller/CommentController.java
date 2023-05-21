package com.shubhamit.blog_app_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubhamit.blog_app_api.payloads.ApiResponse;
import com.shubhamit.blog_app_api.payloads.CommentDto;
import com.shubhamit.blog_app_api.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class CommentController {
	@Autowired
	private CommentService commentService;

	/**
	 * @author Shubham
	 * @apiNote This api is used to create Comments
	 * @param commentDto
	 * @param postId
	 * @return
	 */
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId) {
		log.info("Initiated request for create Comment with postId:{}", postId);
		CommentDto createComment = this.commentService.createComment(commentDto, postId);
		log.info("Completed request for create Comment with postId:{}", postId);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);

	}
	/**
	 * @author Shubham
	 * @apiNote This Api is used to Delete Comment
	 * @param commentId
	 * @return
	 */

//	@DeleteMapping("/comments/{commentId}")
//	public ApiResponse deleteComment(@PathVariable Integer commentId){
//	log.info("Initiated request for delete Comment with commentId:{}", commentId);
//		this.commentService.deleteComment(commentId);
//   	log.info("Completed request for delete Comment with commentId:{}", commentId);
//		return new ApiResponse("Comment Deleted Successfully..!!",true);
//		
//	}

	// Or
	/**
	 * @author Shubham
	 * @apiNote This Api is used to Delete Comment
	 * @param commentId
	 * @return
	 */
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		log.info("Initiated request for delete Comment with commentId:{}", commentId);
		this.commentService.deleteComment(commentId);
		log.info("Completed request for delete Comment with commentId:{}", commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully..!!", true),HttpStatus.OK);

	}

}

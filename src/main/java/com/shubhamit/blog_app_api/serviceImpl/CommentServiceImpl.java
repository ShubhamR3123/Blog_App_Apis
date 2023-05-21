package com.shubhamit.blog_app_api.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shubhamit.blog_app_api.entity.Comment;
import com.shubhamit.blog_app_api.entity.Post;
import com.shubhamit.blog_app_api.exception.ResourceNotFoundException;
import com.shubhamit.blog_app_api.helper.AppConstants;
import com.shubhamit.blog_app_api.payloads.CommentDto;
import com.shubhamit.blog_app_api.repository.CommentRepository;
import com.shubhamit.blog_app_api.repository.PostRepository;
import com.shubhamit.blog_app_api.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;
	

	/**
	 * @author Shubham
	 * @apiNote This Method is used to create Comments
	 * @param commentDto
	 * @param postId
	 * @return
	 */
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		log.info("Initiated Dao Call for create Comment with postId:{}",postId);
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND+postId, "Post Id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment saveComment = this.commentRepository.save(comment);
		log.info("Completed Dao Call for create Comment with postId:{}",postId);
		return this.modelMapper.map(saveComment, CommentDto.class);
	}
	/**
	 * @author Shubham
	 * @apiNote This Method is used to Delete Comment
	 * @param commentId
	 * @return
	 */
	@Override
	public void deleteComment(Integer commentId) {
		log.info("Initiated Dao Call for delete Comment with commentId:{}",commentId);
		Comment comment = this.commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.COMMENT_DELETED+commentId, "CommentId", commentId));
		log.info("Completed Dao Call for delete Comment with commentId:{}",commentId);
		this.commentRepository.delete(comment);
	}

}

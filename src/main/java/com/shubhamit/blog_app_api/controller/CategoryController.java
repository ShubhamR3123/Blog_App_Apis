package com.shubhamit.blog_app_api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubhamit.blog_app_api.helper.AppConstants;
import com.shubhamit.blog_app_api.payloads.CategoryDto;
import com.shubhamit.blog_app_api.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	

	/**
	 * @author Shubham
	 * @apiNote This api is used to create Category
	 * @param categoryDto
	 * @return
	 */
	@PostMapping("/category")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		log.info("Initiated request for create Category Details");
		CategoryDto createCategory = categoryService.createCategory(categoryDto);
		log.info("Completed request for create Category Details");
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}
	/**
	 * @author Shubham
	 * @apiNote This api is used to update Category
	 * @param categoryDto
	 * @param categoryId
	 * @return
	 */

	@PutMapping("/category/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("id") Integer categoryId) {
		log.info("Initiated request for update Category with categoryId:{}",categoryId);
		CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
		log.info("Completed request for update Category with categoryId:{}",categoryId);
		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
	}
/**
 * @author Shubham
 * @apiNote This api is used to Delete Category
 * @Param categoryId
 * @return
 */
	@DeleteMapping("category/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId) {
		log.info("Initiated request for delete Category with categoryId:{}",categoryId);
		categoryService.deleteCategory(categoryId);
		log.info("Completed request for delete Category with categoryId:{}",categoryId);
		return new ResponseEntity<String>(AppConstants.USER_DELETED, HttpStatus.OK);

	}
	/**
	 * @author Shubham
	 * @apiNote This api is used to get single Category
	 * @Param categoryId
	 * @return
	 */

	@GetMapping("category/{id}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable("id") Integer categoryId) {
		log.info("Initiated request for get Single Category with categoryId:{}",categoryId);
		CategoryDto category = categoryService.getCategory(categoryId);
		log.info("Completed request for get Single Category with categoryId:{}",categoryId);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);

	}
	/**
	 * @author Shubham
	 * @apiNote This api is used to get AllCategory
	 * @return
	 */

	@GetMapping("category")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		log.info("Initiated request for get All Category Category ");
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		log.info("Completed request for get All Category Category");
		return new ResponseEntity<>(allCategory, HttpStatus.OK);

	}

}

package com.shubhamit.blog_app_api.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shubhamit.blog_app_api.entity.Category;
import com.shubhamit.blog_app_api.exception.ResourceNotFoundException;
import com.shubhamit.blog_app_api.helper.AppConstants;
import com.shubhamit.blog_app_api.payloads.CategoryDto;
import com.shubhamit.blog_app_api.repository.CategoryRepo;
import com.shubhamit.blog_app_api.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * @author Shubham
	 * @apiNote This Method is used to create Category
	 * @param categoryDto
	 * @return
	 */
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		log.info("Initiated Dao Call for create Category");
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category saveCategory = categoryRepo.save(category);
		log.info("Completed Dao Call for create Category");
		return this.modelMapper.map(saveCategory, CategoryDto.class);
	}
	/**
	 * @author Shubham
	 * @apiNote This Method is used to update Category
	 * @param categoryDto
	 * @param categoryId
	 * @return
	 */

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		log.info("Initiated Dao Call for update Category with categoryId:{}",categoryId);
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId,
						"Category", categoryId));
		category.setCategoryId(categoryDto.getCategoryId());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());

		Category updatedCategory = categoryRepo.save(category);
		log.info("Completed Dao Call for update Category with categoryId:{}",categoryId);
		return modelMapper.map(updatedCategory, CategoryDto.class);
	}

	/**
	 * @author Shubham
	 * @apiNote This Method is used to Delete Category
	 * @Param categoryId
	 * @return
	 */
	@Override
	public void deleteCategory(Integer categoryId) {
		log.info("Initiated Dao Call for delete Category with categoryId:{}",categoryId);
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGPRY_DELETED + categoryId, "Category",
						categoryId));
		log.info("Completed Dao Call for delete Category with categoryId:{}",categoryId);
		this.categoryRepo.delete(category);
	}
	/**
	 * @author Shubham
	 * @apiNote This Method is used to get single Category
	 * @Param categoryId
	 * @return
	 */

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		log.info("Initiated Dao Call for get single Category with categoryId:{}",categoryId);
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND + categoryId,
						"Cayegory", categoryId));
		log.info("Completed Dao Call for get single Category with categoryId:{}",categoryId);
		return modelMapper.map(category, CategoryDto.class);
	}
	/**
	 * @author Shubham
	 * @apiNote This Method is used to get AllCategory
	 * @return
	 */
	@Override
	public List<CategoryDto> getAllCategory() {
		log.info("Initiated Dao Call for get All Category");
		List<Category> allCategory = categoryRepo.findAll();
		List<CategoryDto> list = allCategory.stream()
				.map((categories) -> modelMapper.map(categories, CategoryDto.class)).collect(Collectors.toList());
		log.info("Completed Dao Call for get All Category");
		return list;
	}

}

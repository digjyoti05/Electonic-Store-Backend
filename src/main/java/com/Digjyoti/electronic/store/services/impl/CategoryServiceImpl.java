package com.Digjyoti.electronic.store.services.impl;

import com.Digjyoti.electronic.store.dtos.CategoryDto;
import com.Digjyoti.electronic.store.dtos.PageableResponse;
import com.Digjyoti.electronic.store.entities.Category;
import com.Digjyoti.electronic.store.exceptions.ResourceNotFoundExcepation;
import com.Digjyoti.electronic.store.helper.Helper;
import com.Digjyoti.electronic.store.repositories.CategoryRepository;
import com.Digjyoti.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
//        Create id auto
//            int categoryId = UUID.randomUUID().variant();
//        categoryDto.setCategoryId(categoryId);
        Category category=mapper.map(categoryDto,Category.class);
        System.out.println(category);
        Category savedCategory = categoryRepository.save(category);

        return mapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
//        Get CategoryId
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundExcepation("Category not found Exception"));
//    Update CategoryId
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        return mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundExcepation("Category not found Exception"));
        categoryRepository.delete(category);

    }

    @Override
    public PageableResponse<CategoryDto> getall(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;

    }

    @Override
    public CategoryDto get(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundExcepation("Category not found Exception"));
        return mapper.map(category,CategoryDto.class);
    }
}

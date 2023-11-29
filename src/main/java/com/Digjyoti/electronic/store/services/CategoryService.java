package com.Digjyoti.electronic.store.services;

import com.Digjyoti.electronic.store.dtos.CategoryDto;
import com.Digjyoti.electronic.store.dtos.PageableResponse;
import org.springframework.stereotype.Service;



public interface CategoryService {


//    Create
    CategoryDto create(CategoryDto categoryDto);


//    Update
    CategoryDto update(CategoryDto categoryDto,String categoryId);



//    Delete
    void delete(String categoryId);


//    Get all
    PageableResponse<CategoryDto> getall(int pageNumber,int pageSize,String sortBy,String sortDir);


//    Get single  Category
    CategoryDto get(String categoryId);





}

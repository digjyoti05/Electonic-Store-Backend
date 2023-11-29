package com.Digjyoti.electronic.store.services;

import com.Digjyoti.electronic.store.dtos.PageableResponse;
import com.Digjyoti.electronic.store.dtos.ProductDto;

import java.util.List;

public interface ProductService {
//    create
    ProductDto create(ProductDto productDto);


//    update
    ProductDto update(ProductDto productDto,int productId);



//    delete
    void delete (int productId);


//    Get Single
    ProductDto get(int productId);


//    Get All
    PageableResponse<ProductDto>getAll(int pageNumber,int pageSize,String sortBy,String sortDir);


//    Gel All Live
    PageableResponse<ProductDto>getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);


//    Search
   PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);


//     other
//    create product with Category
    ProductDto createWithCategory(ProductDto productDto,String categoryId);
    ProductDto updateCategory(int productId,String categoryId);
    PageableResponse<ProductDto>getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);
}

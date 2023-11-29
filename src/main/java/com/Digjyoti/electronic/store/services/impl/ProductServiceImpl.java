package com.Digjyoti.electronic.store.services.impl;

import com.Digjyoti.electronic.store.dtos.PageableResponse;
import com.Digjyoti.electronic.store.dtos.ProductDto;
import com.Digjyoti.electronic.store.entities.Category;
import com.Digjyoti.electronic.store.entities.Product;
import com.Digjyoti.electronic.store.exceptions.ResourceNotFoundExcepation;
import com.Digjyoti.electronic.store.helper.Helper;
import com.Digjyoti.electronic.store.repositories.CategoryRepository;
import com.Digjyoti.electronic.store.repositories.ProductRepository;
import com.Digjyoti.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service

public class ProductServiceImpl  implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Value(("${product.profile.image.path}"))
    private  String imagePath;
    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = mapper.map(productDto, Product.class);
        product.setAddedDate(new Date());
        Product saveProduct = productRepository.save(product);
        return mapper.map(saveProduct,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundExcepation("Product Not Found"));
//       product.setProductId(productDto.getProductId());
       product.setTitle(productDto.getTitle());
       product.setDescription(productDto.getDescription());
       product.setPrice(productDto.getPrice());
       product.setDiscountedPrice(productDto.getDiscountedPrice());
       product.setQuantity(productDto.getQuantity());
       product.setLive(productDto.isLive());
       product.setStock(productDto.isStock());
       product.setProductImage(productDto.getProductImage());
        Product updated = productRepository.save(product);
        return mapper.map(updated,ProductDto.class);
    }

    @Override
    public void delete(int productId) {
        Product deletedproduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundExcepation("Product not Found"));

        String fullPath = imagePath +deletedproduct.getProductImage();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException no){
//            logger.info("User Image Is not found in Folder {}");
            no.printStackTrace();

        }catch (IOException io){
            io.printStackTrace();

        }

        productRepository.delete(deletedproduct);

    }

    @Override
    public ProductDto get(int productId) {
        Product getProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundExcepation("Invalid Id"));
        return mapper.map(getProduct,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        return Helper.getPageableResponse(page,ProductDto.class);

    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String  categoryId) {
//        fatch the category
         Category category=categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundExcepation("Product Not Found"));
        Product product = mapper.map(productDto, Product.class);

        product.setAddedDate(new Date());
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);
        return mapper.map(saveProduct,ProductDto.class);
    }

    @Override
    public ProductDto updateCategory(int productId, String categoryId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundExcepation("Product Id Not Found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundExcepation(" category id not found"));
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);

        return mapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundExcepation(" category id not found"));
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product>page=productRepository.findByCategory(category,pageable);
        return  Helper.getPageableResponse(page,ProductDto.class);
    }


}

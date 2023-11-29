package com.Digjyoti.electronic.store.repositories;

import com.Digjyoti.electronic.store.entities.Category;
import com.Digjyoti.electronic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product,Integer> {
//Search
    Page<Product> findByTitleContaining(String subTitle,Pageable pageable);
    Page<Product> findByLiveTrue(Pageable pageable);
   Page<Product> findByCategory(Category category,Pageable pageable);







}

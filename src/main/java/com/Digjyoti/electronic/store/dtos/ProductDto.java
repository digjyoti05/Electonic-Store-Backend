package com.Digjyoti.electronic.store.dtos;

import com.Digjyoti.electronic.store.entities.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductDto {
    private  int productId;
    @Size(min=3,max = 100,message = "Invalid Message")
    private  String title;
    @Size(min = 5,max = 1000,message = "Invalid Description")
    private  String description;
    private  int price;
    private  int discountedPrice;
    private  int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private  String productImage;
    private CategoryDto category;

}

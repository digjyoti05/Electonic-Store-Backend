package com.Digjyoti.electronic.store.dtos;

import com.Digjyoti.electronic.store.entities.Order;
import com.Digjyoti.electronic.store.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class OrderItemDto {

    private  int orderItemId;
    private int quantity;
    private  int totalPrice;
    private ProductDto product;



}

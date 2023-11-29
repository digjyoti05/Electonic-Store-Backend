package com.Digjyoti.electronic.store.dtos;

import com.Digjyoti.electronic.store.entities.Cart;
import com.Digjyoti.electronic.store.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private int cartItemId;
    private Product product;
    private  int quantity;
    private  int totalPrice;

}

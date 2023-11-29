package com.Digjyoti.electronic.store.dtos;

import com.Digjyoti.electronic.store.entities.CartItem;
import com.Digjyoti.electronic.store.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CartDto {
    private String id;
    private Date createdAt;
    private UserDto user;
    private List<CartItemDto> items=new ArrayList<>();
}

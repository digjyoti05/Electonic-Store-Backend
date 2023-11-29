package com.Digjyoti.electronic.store.services;

import com.Digjyoti.electronic.store.dtos.AddItemToCartRequest;
import com.Digjyoti.electronic.store.dtos.CartDto;

public interface CartService {
//    Add cart:
//    if cart is not available then create
    CartDto addItemToCart(int  userId, AddItemToCartRequest request);
//    remove Item From Cart
    void removeItemFromCart(int userId,int cartItem);
//    void Clear Cart
    void clearCart(int userId);
    CartDto getCartByUser(int userId);


}

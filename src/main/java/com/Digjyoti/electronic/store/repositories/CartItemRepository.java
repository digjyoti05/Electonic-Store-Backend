package com.Digjyoti.electronic.store.repositories;

import com.Digjyoti.electronic.store.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface  CartItemRepository  extends JpaRepository<CartItem,Integer> {

}

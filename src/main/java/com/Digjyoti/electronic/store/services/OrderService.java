package com.Digjyoti.electronic.store.services;

import com.Digjyoti.electronic.store.dtos.CreateOrderRequest;
import com.Digjyoti.electronic.store.dtos.OrderDto;
import com.Digjyoti.electronic.store.dtos.PageableResponse;
import com.Digjyoti.electronic.store.entities.Order;
import com.Digjyoti.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderService  {
//    create Order
    OrderDto createOrder(CreateOrderRequest orderDto);
//    RemoveOrder
    void removeOrder(String orderId);
//    get Order of User
    List<OrderDto>getOrderOfUser(int userId);
//    get Order
    PageableResponse<OrderDto> getOrder(int pageNumber,int pageSize,String sortBy,String sortDir);
//    Other Logic






}

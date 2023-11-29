package com.Digjyoti.electronic.store.services.impl;

import com.Digjyoti.electronic.store.dtos.CreateOrderRequest;
import com.Digjyoti.electronic.store.dtos.OrderDto;
import com.Digjyoti.electronic.store.dtos.PageableResponse;
import com.Digjyoti.electronic.store.entities.*;
import com.Digjyoti.electronic.store.exceptions.BadApiRequestException;
import com.Digjyoti.electronic.store.exceptions.ResourceNotFoundExcepation;
import com.Digjyoti.electronic.store.helper.Helper;
import com.Digjyoti.electronic.store.repositories.CartRepository;
import com.Digjyoti.electronic.store.repositories.OrderRepository;
import com.Digjyoti.electronic.store.repositories.UserRepository;
import com.Digjyoti.electronic.store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service

public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartRepository cartRepository;
    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
        int userId=orderDto.getUserId();
        String cartId=orderDto.getCartId();
        User userIsNotFound = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundExcepation("User Is Not Found"));
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundExcepation("Cart Is not Found Given Id"));
        List<CartItem> cartItems = cart.getItems();
        if (cartItems.size() == 0) {
            throw new BadApiRequestException("Invalid Number Of Item in cart");
        }
        Order order = Order.builder().billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(userIsNotFound)
                .build();
        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
//            CartItem->OrderItem
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();

            orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());
        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());
//        CartClear
        cart.getItems().clear();
        cartRepository.save(cart);
        Order save = orderRepository.save(order);
        return modelMapper.map(save,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundExcepation("Order is not found"));
        orderRepository.delete(order);


    }

    @Override
    public List<OrderDto> getOrderOfUser(int userId) {
        User userNotFound = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundExcepation("User Not Found"));
        List<Order> orders = orderRepository.findByUser(userNotFound);
        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDto> getOrder(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
       Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> page = orderRepository.findAll(pageable);
        return Helper.getPageableResponse(page,OrderDto.class);
    }
}

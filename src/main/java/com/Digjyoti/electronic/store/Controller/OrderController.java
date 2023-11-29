package com.Digjyoti.electronic.store.Controller;

import com.Digjyoti.electronic.store.dtos.ApiResponseMessage;
import com.Digjyoti.electronic.store.dtos.CreateOrderRequest;
import com.Digjyoti.electronic.store.dtos.OrderDto;
import com.Digjyoti.electronic.store.dtos.PageableResponse;
import com.Digjyoti.electronic.store.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/created")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request){
        OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);

    }
    @DeleteMapping("/{orderId}")
    public  ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId){
        orderService.removeOrder(orderId);
        ApiResponseMessage orderDeleteSuccessfully = ApiResponseMessage.builder().message("Order Delete Successfully").success(true).status(HttpStatus.OK).build();
        return  new ResponseEntity<>(orderDeleteSuccessfully,HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public  ResponseEntity<List<OrderDto>>getOrderOfUser(@PathVariable int userId){
        List<OrderDto> orderOfUser = orderService.getOrderOfUser(userId);
        return new ResponseEntity<>(orderOfUser,HttpStatus.OK);

    }
    @GetMapping
    public  ResponseEntity<PageableResponse<OrderDto>>getOrder(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderedDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir){
        PageableResponse<OrderDto> orders = orderService.getOrder(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}

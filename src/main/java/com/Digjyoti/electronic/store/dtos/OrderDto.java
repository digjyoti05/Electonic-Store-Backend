package com.Digjyoti.electronic.store.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class OrderDto {

    private  String orderId;
    private  String orderStatus="Pending";
    private  String paymentStatus="NotPaid";
    private int orderAmount;
    private  String billingAddress;
    private  String billingPhone;
    private  String billingName;
    private Date orderedDate=new Date();
    private Date deliveredDate;

    //private  User user;
    private List<OrderItemDto> orderItems=new ArrayList<>();

}

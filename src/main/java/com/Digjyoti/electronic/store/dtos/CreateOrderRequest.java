package com.Digjyoti.electronic.store.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
//    @NotBlank(message = "Cart Id is Required")
    private  String cartId;
//    @NotBlank(message = "userId is Required")
    private  int userId;
    private  String orderStatus="Pending";
    private  String paymentStatus="NotPaid";
    @NotBlank(message = "billing Address is Required")
    private  String billingAddress;
    @NotBlank(message = "billing Phone is Required")
    private  String billingPhone;
    @NotBlank(message = "billing Name is Required")
    private  String billingName;


    //private  User user;

}

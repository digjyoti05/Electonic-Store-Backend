package com.Digjyoti.electronic.store.entities;

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
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private  String orderId;
    private  String orderStatus;
    private  String paymentStatus;
    private int orderAmount;
    @Column(length=1000)
    private  String billingAddress;
    private  String billingPhone;
    private  String billingName;
    private Date orderedDate;
    private  Date deliveredDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_Id")
    private  User user;
    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<OrderItem> orderItems=new ArrayList<>();


}

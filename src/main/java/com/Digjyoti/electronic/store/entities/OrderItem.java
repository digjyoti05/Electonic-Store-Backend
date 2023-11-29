package com.Digjyoti.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Order_items")

public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int orderItemId;
    private  int totalPrice;
    private  int quantity;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private  Order order;


}

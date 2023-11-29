package com.Digjyoti.electronic.store.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int productId;
    private  String title;
    @Column(length = 10000)
    private  String description;
    private  int price;
    private  int discountedPrice;
    private  int quantity;

    private Date addedDate;
    private boolean live;
    private boolean stock;
    private  String productImage;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id")
    private Category category;

}

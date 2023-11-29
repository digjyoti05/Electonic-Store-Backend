package com.Digjyoti.electronic.store.entities;


import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int  CategoryId;
    @Column(name = "category_title",length = 100,nullable = false)
    private  String title;
    @Column(name="category_desc",length = 100)
    private  String description;
    private  String coverImage;
    @OneToMany(mappedBy = "category",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product>products=new ArrayList<>();





}

package com.Digjyoti.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;
    @Column(name="user_name",length = 50)
    private  String name;
    @Column(name = "user_email",unique = true)
    private  String email;
    @Column(name="user_password",length = 20)
    private  String password;
    private  String gender;
    @Column(length = 1000)
    private  String about;
    @Column(name = "user_image")
    private  String image;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    
    private List<Order>orders=new ArrayList<>();


}

package com.Digjyoti.electronic.store.repositories;

import com.Digjyoti.electronic.store.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {


    Optional<User> findByEmail(String Email);
    Optional<User> findByEmailAndPassword(String email,String Password);
    List<User> findByNameContaining(String Keywords);





}

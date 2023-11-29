package com.Digjyoti.electronic.store.services;

import com.Digjyoti.electronic.store.dtos.PageableResponse;
import com.Digjyoti.electronic.store.dtos.UserDto;
import com.Digjyoti.electronic.store.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
//    create
    UserDto createUser(UserDto userDto);

//    Update
    UserDto updateUser(UserDto userDto,int Id);

// delete
    void deleteUser(int userId);


//    get all users
 PageableResponse<UserDto>getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);


//    get single user by id
    UserDto getUserbyId(int userId);
//    get single user by email
    UserDto getUserEmail(String email);
    List<UserDto> searchUser(String keyword);



}

package com.Digjyoti.electronic.store.services.impl;

import com.Digjyoti.electronic.store.dtos.PageableResponse;
import com.Digjyoti.electronic.store.dtos.UserDto;
import com.Digjyoti.electronic.store.entities.User;
import com.Digjyoti.electronic.store.exceptions.ResourceNotFoundExcepation;
import com.Digjyoti.electronic.store.helper.Helper;
import com.Digjyoti.electronic.store.repositories.UserRepository;
import com.Digjyoti.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService   {
    @Autowired
    private UserRepository userRepository;
    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ModelMapper mapper;
    @Value("${user.profile.image.path}")
    private  String imagePath;;
    @Override
    public UserDto createUser(UserDto userDto) {
        System.out.println("ENTERED createUser METHOD...");
        //Generate Random ID
//        int userId= UUID.randomUUID().variant();
//        userDto.setId(userId);
     User user= dtoToEntity(userDto);
     User savedUser=userRepository.save(user);
     UserDto newDto=entityToDto(savedUser);
//        System.out.println("RETURNING createUser METHOD...");

     return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, int userId) {
        System.out.println("ENTERED UPDATION 1");
       User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundExcepation("User Not found Given Id"));
       user.setName(userDto.getName());
       user.setEmail(userDto.getEmail());
       user.setAbout(userDto.getAbout());
       user.setPassword(userDto.getPassword());
       user.setImage(userDto.getImage());
       user.setGender(userDto.getGender());
       User updatedUser=userRepository.save(user);
      UserDto updatedDto= entityToDto(updatedUser);
        return updatedDto;
    }

    @Override
    public void deleteUser(int userId) {
        User user= userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundExcepation("User Not found Given Id"));
        String fullPath = imagePath + user.getImage();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException no){
            logger.info("User Image Is not found in Folder {}");
            no.printStackTrace();

        }catch (IOException io){
            io.printStackTrace();

        }


        userRepository.delete(user);

    }
    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?(Sort.by(sortBy).descending()):Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of( pageNumber,pageSize,sort);
//        System.out.println("AGAIN.......");
        Page<User> page=userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        return response;

    }

    @Override
    public UserDto getUserbyId(int userId) {
        System.out.println("ENTERED... SERVICEIMPL");
       User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundExcepation("User not found With Given Id"));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserEmail(String email) {
        User user=userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundExcepation("User Not found wih Email and password"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
       List<User>users= userRepository.findByNameContaining(keyword);
        List<UserDto>dtoList= users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }
    private UserDto entityToDto(User savedUser) {
//      UserDto userDto=  UserDto.builder()
//                .id(savedUser.getId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .image(savedUser.getImage()).build();

        return mapper.map(savedUser,UserDto.class);



    }

    private User dtoToEntity(UserDto userDto) {
//       User user= User.builder()
//                .id(userDto.getId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//               .image(userDto.getImage()).build();
        return mapper.map(userDto,User.class);
    }
}

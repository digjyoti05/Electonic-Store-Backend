package com.Digjyoti.electronic.store.Controller;

import com.Digjyoti.electronic.store.dtos.ApiResponseMessage;
import com.Digjyoti.electronic.store.dtos.ImageResponse;
import com.Digjyoti.electronic.store.dtos.PageableResponse;
import com.Digjyoti.electronic.store.dtos.UserDto;
import com.Digjyoti.electronic.store.services.FileService;
import com.Digjyoti.electronic.store.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;
     private Logger logger=LoggerFactory.getLogger(UserController.class);

    //    Create
    @PostMapping("/created")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
//        System.out.println("ENTERED CONTROLLER...");
        UserDto userDto1 = userService.createUser(userDto);
//        System.out.println("RETURNED FROM createUser...");
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);

    }

    //    Update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable int userId,
           @Valid @RequestBody UserDto userDto
    ) {
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);


    }

    //    Delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable int userId) {
        ApiResponseMessage message
                = ApiResponseMessage
                .builder()
                .message("User is deleted Successfully !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        userService.deleteUser(userId);

        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    //    Getall
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value ="pageNumber",defaultValue = "0",required = false)int pageNumber,

            @RequestParam(value ="pageSize",defaultValue = "10",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
//        System.out.println("ENTERED.........");
        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir), HttpStatus.OK);

    }

    @GetMapping("/{userId}")
//    Get SingleUser
    public ResponseEntity<UserDto> getuser(@PathVariable int userId) {
        return new ResponseEntity<>(userService.getUserbyId(userId), HttpStatus.OK);
    }

    //    GetByEmail
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getuserbyEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserEmail(email), HttpStatus.OK);
    }

    //Search User
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
        return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);

    }
//    Upload user Image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam("userImage") MultipartFile image,
            @PathVariable int userId) throws IOException {
        System.out.println("BODY ENTERED..");

        String imageName=fileService.uploadFile(image,imageUploadPath);
        UserDto user=userService.getUserbyId(userId);
        user.setImage(imageName);
        UserDto userDto=userService.updateUser(user,userId);
        ImageResponse imageResponse=ImageResponse
                .builder()
                .imageName(imageName)
                .success(true).message("Image Upload Successfully")
                .status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    }
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable int userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.getUserbyId(userId);
        logger.info("User Image Name : {} ",user.getImage());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());



    }
}
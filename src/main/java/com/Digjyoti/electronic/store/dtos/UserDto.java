package com.Digjyoti.electronic.store.dtos;

import com.Digjyoti.electronic.store.validate.ImageNameValid;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private int id;
    @Size(min = 5,max = 100,message = "Invalid Name")
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message = "Invalid Email")
    @Email(message = "Invalid User Email")
    @NotBlank(message = "Email is Blank")
    private String email;
    @NotBlank(message = "password is Required")

    private String password;
    @Size(min = 2,max = 6,message = "Invalid Gender")
    private String gender;
    @NotBlank(message = "Write some thing about yourself")
    private String about;
    @ImageNameValid
    private String image;

}

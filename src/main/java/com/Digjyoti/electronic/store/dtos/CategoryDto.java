package com.Digjyoti.electronic.store.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private  int  CategoryId;
    @NotBlank(message = "Title is required")
    @Size(min =3,max=100,message = "Title must be of minimum 4 Characters")
    private  String title;
    @NotBlank(message = "Desc is Required")
    private  String description;
    private  String coverImage;
}

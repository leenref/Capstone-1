package com.example.ecommercewebsitecapstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {
    @NotEmpty(message = "Id must be filled")
    private String id;

    @NotEmpty(message = "Name must be filled")
    @Size(min = 4,message = "Name must be more than 3 characters long")
    private String name;

}

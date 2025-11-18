package com.example.ecommercewebsitecapstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "Id must be filled")
    private String id;

    @NotEmpty(message = "Name must be filled")
    @Size(min = 4,message = "Name must be more than 3 characters long")
    private String name;

    @NotNull(message = "Price must be filled")
    @Positive(message = "Price must be a positive number")
    private Double price;

    @NotEmpty(message = "Category Id must be filled")
    private String categoryId;
}

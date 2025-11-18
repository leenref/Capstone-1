package com.example.ecommercewebsitecapstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class User {
    @NotEmpty(message = "Id must be filled")
    private String id;

    @NotEmpty(message = "Username must be filled")
    @Size(min = 6, message = "Username must be more than 5 characters")
    private String username;

    @NotEmpty(message = "Password must not be filled")
    @Size(min = 7, message = "Password must be more than 6 characters long")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Password must contain letters and digits")
    private String password;

    @NotEmpty(message = "Email must be filled")
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty(message = "Role must be filled")
    @Pattern(regexp = "(?i)^(Admin|Customer)$", message = "Role must be Admin or Customer ")
    private String role;

    @NotNull(message = "Balance must be filled")
    @Positive(message = "Balance must be a positive number")
    private Double balance;






}

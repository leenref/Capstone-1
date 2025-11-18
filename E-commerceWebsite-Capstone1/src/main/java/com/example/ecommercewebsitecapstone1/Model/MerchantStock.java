package com.example.ecommercewebsitecapstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "Id must be filled")
    private String id;

    @NotEmpty(message = "Id must be filled")
    private String productId;

    @NotEmpty(message = "Id must be filled")
    private String merchantId;

    @NotNull(message = "Stock must be filled")
    @Min(value = 11, message = "Stock must have more than 10 at start")
    private Integer stock;
}

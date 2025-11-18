package com.example.ecommercewebsitecapstone1.Controller;

import com.example.ecommercewebsitecapstone1.ApiResponse.ApiResponse;
import com.example.ecommercewebsitecapstone1.Model.Product;
import com.example.ecommercewebsitecapstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getProducts(){
        return ResponseEntity.status(200).body(productService.getProducts());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        boolean added = productService.addProduct(product);

        if (!added) {
            return ResponseEntity.status(404).body(new ApiResponse("categoryId does not exist"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        boolean updated = productService.updateProduct(id, product);

        if (!updated) {
            return ResponseEntity.status(404).body(new ApiResponse("product or categoryId not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        if(productService.deleteProduct(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Product has been deleted"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Product not found"));

    }

    //Endpoint 3 Sale
    @PutMapping("/add-sale/{userId}/{productId}/{discount}")
    public ResponseEntity<?> addSale(@PathVariable String userId, @PathVariable String productId, @PathVariable double discount) {
        boolean saleAdded = productService.addSale(userId, productId, discount);

        if (!saleAdded) {
            return ResponseEntity.badRequest().body(new ApiResponse("Sale could not be applied: Invalid admin, or product, or discount"));
        }

        return ResponseEntity.ok(new ApiResponse("Discount applied successfully"));
    }

    //EndPoint 5 filter
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> filterByCategory(@PathVariable String categoryId) {
        ArrayList<Product> result = productService.filterByCategory(categoryId);

        if (result.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No products found for this category"));
        }
        return ResponseEntity.status(200).body(result);
    }







}

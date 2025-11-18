package com.example.ecommercewebsitecapstone1.Controller;

import com.example.ecommercewebsitecapstone1.ApiResponse.ApiResponse;
import com.example.ecommercewebsitecapstone1.Model.MerchantStock;
import com.example.ecommercewebsitecapstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant-Stock")
@RequiredArgsConstructor
public class MerchantStockController{
    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity<?> getStocks() {
        return ResponseEntity.status(200).body(merchantStockService.getStocks());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStock(@RequestBody @Valid MerchantStock stock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        boolean added = merchantStockService.addStock(stock);

        if (!added) {
            return ResponseEntity.status(404).body(new ApiResponse("productId or merchantId does not exist"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("MerchantStock added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStock(@PathVariable String id, @RequestBody @Valid MerchantStock stock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        boolean updated = merchantStockService.updateStock(id, stock);

        if (!updated) {
            return ResponseEntity.status(404).body(new ApiResponse("MerchantStock not found or product/merchant not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("MerchantStock updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable String id) {
        boolean deleted = merchantStockService.deleteStock(id);

        if (!deleted) {
            return ResponseEntity.status(404).body(new ApiResponse("MerchantStock not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("MerchantStock deleted successfully"));
    }


    @PutMapping("/add-stock/{productId}/{merchantId}/{amount}")
    public ResponseEntity<?> addMoreStock(@PathVariable String productId, @PathVariable String merchantId, @PathVariable int amount) {
        boolean updated = merchantStockService.addMoreStock(productId, merchantId, amount);

        if (!updated) {
            return ResponseEntity.status(404).body(new ApiResponse("ProductId and merchantId not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Stock increased successfully"));
    }


    @PutMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId) {
        boolean bought = merchantStockService.buyProduct(userId, productId, merchantId);

        if (!bought) {
            return ResponseEntity.status(404).body(new ApiResponse("Purchase failed: invalid IDs, no stock, or insufficient balance"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Product purchased successfully"));
    }

    //Endpoint 1 Refund
    @PutMapping("/refund/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> refundProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId) {
        boolean refunded = merchantStockService.refund(userId, productId, merchantId);

        if (!refunded) {
            return ResponseEntity.status(400).body(new ApiResponse("Refund failed: The product was purchased"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Refund processed successfully"));
    }


    //Endpoint 4 Recommended products
    @GetMapping("/recommended-items/{userId}")
    public ResponseEntity<?> recommendedItems(@PathVariable String userId) {
        return ResponseEntity.status(200).body(merchantStockService.recommendedItems(userId));
    }

}

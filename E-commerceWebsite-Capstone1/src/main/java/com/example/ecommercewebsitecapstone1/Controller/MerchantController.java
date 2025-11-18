package com.example.ecommercewebsitecapstone1.Controller;

import com.example.ecommercewebsitecapstone1.ApiResponse.ApiResponse;
import com.example.ecommercewebsitecapstone1.Model.Merchant;
import com.example.ecommercewebsitecapstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

        @GetMapping("/get")
        public ResponseEntity<?> getMerchants() {
            return ResponseEntity.status(200).body(merchantService.getMerchants());
        }

        @PostMapping("/add")
        public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
            if (errors.hasErrors()) {
                return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
            }

            merchantService.addMerchant(merchant);
            return ResponseEntity.status(200).body(new ApiResponse("Merchant added successfully"));
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<?> updateMerchant(@PathVariable String id, @RequestBody @Valid  Merchant merchant, Errors errors) {

            if (errors.hasErrors()) {
                return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
            }

            boolean updated = merchantService.updateMerchant(id, merchant);

            if (!updated) {
                return ResponseEntity.status(404).body(new ApiResponse("Merchant not found"));
            }

            return ResponseEntity.status(200).body(new ApiResponse("Merchant updated successfully"));
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<?> deleteMerchant(@PathVariable String id) {
            boolean deleted = merchantService.deleteMerchant(id);

            if (!deleted) {
                return ResponseEntity.status(404).body(new ApiResponse("Merchant not found"));
            }

            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted successfully"));
        }


}

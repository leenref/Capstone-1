package com.example.ecommercewebsitecapstone1.Controller;

import com.example.ecommercewebsitecapstone1.ApiResponse.ApiResponse;
import com.example.ecommercewebsitecapstone1.Model.User;
import com.example.ecommercewebsitecapstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User has been added"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id,@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.updateUser(id,user);
        return ResponseEntity.status(200).body(new ApiResponse("User has been updated"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        if(userService.deleteUser(id)){
            return ResponseEntity.status(200).body(new ApiResponse("User has been deleted"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("User not found"));

    }

    //Endpoint 2 PromoCode
    @PutMapping("/promo/{userId}")
    public ResponseEntity<?> applyPromo(@PathVariable String userId, @RequestParam String code) {

        Double result = userService.applyPromo(userId, code);

        if (result == null) {
            return ResponseEntity.status(404).body(new ApiResponse("User not found"));
        }

        if (result == -1.0) {
            return ResponseEntity.status(400).body(new ApiResponse("Promo code already used"));
        }

        if (result == -2.0) {
            return ResponseEntity.status(400).body(new ApiResponse("Invalid promo code"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Promo code " + code + " applied. " + result + " SR added to your balance"));
    }

}

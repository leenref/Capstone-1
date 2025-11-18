package com.example.ecommercewebsitecapstone1.Service;

import com.example.ecommercewebsitecapstone1.Model.Category;
import com.example.ecommercewebsitecapstone1.Model.Product;
import com.example.ecommercewebsitecapstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class ProductService {
    public ArrayList<Product> products = new ArrayList<>();

    private final CategoryService categoryService;
    private final UserService userService;


    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean addProduct(Product product) {
        for (Category c : categoryService.categories) {
            if (c.getId().equals(product.getCategoryId())) {
                products.add(product);
                return true;
            }
        }
        return false;
    }

    public boolean updateProduct(String id, Product product) {
        for (Product p : products) {
            if (p.getId().equals(id)) {

                boolean categoryRight = false;
                for (Category c : categoryService.categories) {
                    if (c.getId().equals(product.getCategoryId())) {
                        categoryRight = true;
                        break;
                    }
                }
                if (!categoryRight) return false;

                products.set(products.indexOf(p), product);
                return true;
            }
        }

        return false;
    }


    public boolean deleteProduct(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(i);
                return true;
            }
        }

        return false;
    }

     //next method is validation for sale endpoint
     private User getUserById(String userId) {
         for (User u : userService.users) {
             if (u.getId().equals(userId)) {
                 return u;
             }
         }
         return null;
     }

     //Endpoint 3 Sale
     public boolean addSale(String userId, String productId, double discount) {
         //Validate user
         User user = getUserById(userId);
         if (user == null) return false;

         if (!user.getRole().equalsIgnoreCase("Admin")) {
             return false;
         }

         if (discount <= 0 || discount > 90) return false;


         for (Product p : products) {
             if (p.getId().equals(productId)) {

                 double newPrice = p.getPrice() - (p.getPrice() * (discount / 100));

                 if (newPrice <= 0) return false;


                 p.setPrice(newPrice);
                 return true;
             }
         }

         return false;
     }


     //endpoint 5 filter
    public ArrayList<Product> filterByCategory(String categoryId) {
        ArrayList<Product> filtered = new ArrayList<>();

        for (Product p : products) {
            if (p.getCategoryId().equalsIgnoreCase(categoryId)) {
                filtered.add(p);
            }
        }

        return filtered;
    }







}
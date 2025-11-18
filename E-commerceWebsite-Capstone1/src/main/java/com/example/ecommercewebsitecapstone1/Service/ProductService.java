package com.example.ecommercewebsitecapstone1.Service;

import com.example.ecommercewebsitecapstone1.Model.Category;
import com.example.ecommercewebsitecapstone1.Model.Merchant;
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
    private final MerchantService merchantService;


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

    private Merchant getMerchantById(String merchantId) {
        for (Merchant m : merchantService.merchants) {
            if (m.getId().equals(merchantId)) {
                return m;
            }
        }
        return null;
    }

     //Updated Endpoint 3 Sale
    public boolean addSale(String merchantId, String productId, double discount) {
        //Validate merchant
        Merchant merchant = getMerchantById(merchantId);
        if (merchant == null) {
            return false;
        }

        if (discount <= 0 || discount > 90) {
            return false;
        }


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

    //new endpoint 5
    public ArrayList<Product> filterByCategoryAndPrice(String categoryId, double minPrice, double maxPrice) {
        ArrayList<Product> result = new ArrayList<>();

        for (Product p : products) {
            boolean sameCategory = p.getCategoryId().equalsIgnoreCase(categoryId);
            boolean withinRange = p.getPrice() >= minPrice && p.getPrice() <= maxPrice;

            if (sameCategory && withinRange) {
                result.add(p);
            }
        }

        return result;
    }


//     //endpoint 5 filter
//    public ArrayList<Product> filterByCategory(String categoryId) {
//        ArrayList<Product> filtered = new ArrayList<>();
//
//        for (Product p : products) {
//            if (p.getCategoryId().equalsIgnoreCase(categoryId)) {
//                filtered.add(p);
//            }
//        }
//
//        return filtered;
//    }







}
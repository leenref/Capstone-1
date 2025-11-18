package com.example.ecommercewebsitecapstone1.Service;

import com.example.ecommercewebsitecapstone1.Model.Merchant;
import com.example.ecommercewebsitecapstone1.Model.MerchantStock;
import com.example.ecommercewebsitecapstone1.Model.Product;
import com.example.ecommercewebsitecapstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    public ArrayList<MerchantStock> stocks = new ArrayList<>();
    private ArrayList<String[]> purchasedProducts = new ArrayList<>();


    private final ProductService productService;
    private final MerchantService merchantService;
    private final UserService userService;


    public ArrayList<MerchantStock> getStocks() {
        return stocks;
    }

    public boolean addStock(MerchantStock merchantStock) {
        boolean productFound = false;
        boolean merchantFound = false;

        for (Product p : productService.products) {
            if (p.getId().equals(merchantStock.getProductId())) {
                productFound = true;
                break;
            }
        }

        for (Merchant m : merchantService.merchants) {
            if (m.getId().equals(merchantStock.getMerchantId())) {
                merchantFound = true;
                break;
            }
        }

        if (!productFound || !merchantFound) return false;

        stocks.add(merchantStock);
        return true;
    }

    public boolean updateStock(String id, MerchantStock stock) {
        boolean productFound = false;
        boolean merchantFound = false;

        for (Product p : productService.products) {
            if (p.getId().equals(stock.getProductId())) {
                productFound = true;
                break;
            }
        }

        for (Merchant m : merchantService.merchants) {
            if (m.getId().equals(stock.getMerchantId())) {
                merchantFound = true;
                break;
            }
        }

        if (!productFound || !merchantFound) return false;

        for (MerchantStock ms : stocks) {
            if (ms.getId().equals(id)) {
                stocks.set(stocks.indexOf(ms), stock);
                return true;
            }
        }
        return false;
    }

    public boolean deleteStock(String id) {
        for (int i = 0; i < stocks.size(); i++) {
            if (stocks.get(i).getId().equals(id)) {
                stocks.remove(i);
                return true;
            }
        }
        return false;
    }


    public boolean addMoreStock(String productId, String merchantId, int amount) {
        for (MerchantStock ms : stocks) {
            if (ms.getProductId().equals(productId) && ms.getMerchantId().equals(merchantId)) {
                ms.setStock(ms.getStock() + amount);
                return true;
            }
        }

        return false;
    }

    //next methods are validations for buy endpoint, refund endpoint, and recommendation endpoint
    private User getUserById(String userId) {
        for (User u : userService.users) {
            if (u.getId().equals(userId)) {
                return u;
            }
        }
        return null;
    }

    private Product getProductById(String productId) {
        for (Product p : productService.products) {
            if (p.getId().equals(productId)) {
                return p;
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

    private MerchantStock getMerchantStock(String productId, String merchantId) {
        for (MerchantStock ms : stocks) {
            if (ms.getProductId().equals(productId) &&
                    ms.getMerchantId().equals(merchantId)) {
                return ms;
            }

        }
        return null;
    }

    public boolean buyProduct(String userId, String productId, String merchantId) {
        //Validate user
        User user = getUserById(userId);
        if (user == null) return false;

        //Validate product
        Product product = getProductById(productId);
        if (product == null) return false;

        //Validate merchant
        Merchant merchant = getMerchantById(merchantId);
        if (merchant == null) return false;

        //Check  stock
        MerchantStock merchantStock = getMerchantStock(productId, merchantId);
        if (merchantStock == null) return false;

        if (merchantStock.getStock() <= 0) return false;

        //Check user balance
        if (user.getBalance() < product.getPrice()) return false;


        merchantStock.setStock(merchantStock.getStock() - 1);
        user.setBalance(user.getBalance() - product.getPrice());

        purchasedProducts.add(new String[]{userId, productId});

        return true;

    }

    //Endpoint 1 Refund
    public boolean refund(String userId, String productId, String merchantId) {

        //Validate user
        User user = getUserById(userId);
        if (user == null) return false;

        //Validate product
        Product product = getProductById(productId);
        if (product == null) return false;

        //Validate merchant
        Merchant merchant = getMerchantById(merchantId);
        if (merchant == null) return false;

        //Validate the merchant stock product
        MerchantStock merchantStock = getMerchantStock(productId, merchantId);
        if (merchantStock == null) return false;

        //Check user purchased the product
        boolean wasPurchased = false;
        String[] itemToRemove = null;

        for (String[] purchase : purchasedProducts) {
            if (purchase[0].equals(userId) && purchase[1].equals(productId)) {
                wasPurchased = true;

                itemToRemove = purchase;
                break;
            }
        }

        if (!wasPurchased) return false;

        purchasedProducts.remove(itemToRemove);
        merchantStock.setStock(merchantStock.getStock() + 1);
        user.setBalance(user.getBalance() + product.getPrice());

        return true;
    }



    //Endpoint 4 recommendation
    public ArrayList<Product> recommendedItems(String userId) {
        //Validate user
        User user = getUserById(userId);
        if (user == null) return new ArrayList<>();

        //Find categories of the purchased product
        ArrayList<String> categoriesBought = new ArrayList<>();

        for (String[] purchase : purchasedProducts) {
            String purchasedUserId = purchase[0];
            String purchasedProductId = purchase[1];

            if (purchasedUserId.equals(userId)) {
                Product purchasedProduct = getProductById(purchasedProductId);
                if (purchasedProduct != null) {
                    categoriesBought.add(purchasedProduct.getCategoryId());
                }
            }
        }

        //return products from the same categories
        ArrayList<Product> recommendations = new ArrayList<>();

        for (Product p : productService.products) {
            if (categoriesBought.contains(p.getCategoryId())) {

                //skip the purchased product
                boolean alreadyBought = false;
                for (String[] purchase : purchasedProducts) {
                    if (purchase[0].equals(userId) && purchase[1].equals(p.getId())) {
                        alreadyBought = true;
                        break;
                    }
                }

                if (!alreadyBought) {
                    recommendations.add(p);
                }
            }
        }

        return recommendations;
    }












}

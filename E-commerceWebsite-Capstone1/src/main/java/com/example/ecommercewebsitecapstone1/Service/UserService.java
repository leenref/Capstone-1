package com.example.ecommercewebsitecapstone1.Service;

import com.example.ecommercewebsitecapstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    ArrayList<User> users=new ArrayList<>();
    private ArrayList<String> usedPromos = new ArrayList<>();


    public ArrayList<User> getUsers(){
        return users;
    }

    public void addUser(User user){
        users.add(user);
    }

    public boolean updateUser(String id, User user){
        for(int i=0; i< users.size();i++){
            if(users.get(i).getId().equals(id)){
                users.set(i,user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String id){
        for(int i=0; i< users.size();i++){
            if(users.get(i).getId().equals(id)){
                users.remove(i);
                return true;
            }
        }
        return false;

    }



    //next method is validation for promo code endpoint
    public User getUserById(String userId) {
        for (User u : users) {
            if (u.getId().equals(userId)) {
                return u;
            }
        }
        return null;
    }


    //Endpoint 2 promo code
    public Double applyPromo(String userId, String code) {
        //validate user
        User user = getUserById(userId);
        if (user == null) return null;

        //check if code already used
        String key = userId + "-" + code;
        if (usedPromos.contains(key)) return -1.0;

        double amount = 0;

        switch (code.toUpperCase()) {
            case "PROMO10":
                amount = 10;
                break;
            case "PROMO20":
                amount = 20;
                break;
            case "PROMO50":
                amount = 50;
                break;
            default:
                return -2.0;
        }

        user.setBalance(user.getBalance() + amount);
        usedPromos.add(key);

        return amount;
    }



}

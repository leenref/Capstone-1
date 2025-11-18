package com.example.ecommercewebsitecapstone1.Service;

import com.example.ecommercewebsitecapstone1.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }

    public void addMerchant(Merchant merchant) {
        merchants.add(merchant);
    }

    public boolean updateMerchant(String id, Merchant merchant) {
        for (Merchant m : merchants) {
            if (m.getId().equals(id)) {
                merchants.set(merchants.indexOf(m), merchant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchant(String id) {
        return merchants.removeIf(m -> m.getId().equals(id));
    }


}


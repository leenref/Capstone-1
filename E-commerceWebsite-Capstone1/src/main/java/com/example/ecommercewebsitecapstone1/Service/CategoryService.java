package com.example.ecommercewebsitecapstone1.Service;

import com.example.ecommercewebsitecapstone1.Model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CategoryService {

    public ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public boolean updateCategory(String id, Category category) {
        for (Category c : categories) {
            if (c.getId().equals(id)) {
                categories.set(categories.indexOf(c), category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String id) {
        return categories.removeIf(c -> c.getId().equals(id));
    }
}

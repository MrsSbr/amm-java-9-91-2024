package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    void createCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Long id);
}

package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Сategory;

import java.util.List;

public interface CategoryService {
    List<Сategory> getAllCategories();
    Сategory getCategoryById(Long id);
    void createCategory(Сategory category);
    void updateCategory(Сategory category);
    void deleteCategory(Long id);
}

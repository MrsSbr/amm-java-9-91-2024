package ru.vsu.amm.java.service.impl;

import ru.vsu.amm.java.entities.Author;
import ru.vsu.amm.java.entities.Сategory;
import ru.vsu.amm.java.repository.AuthorRepository;
import ru.vsu.amm.java.repository.CategoryRepository;
import ru.vsu.amm.java.service.CategoryService;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryServiceImpl implements CategoryService {
    private static final Logger log = Logger.getLogger(CategoryServiceImpl.class.getName());

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl() {
        log.info("Call CategoryServiceImpl constructor");
        categoryRepository = new CategoryRepository();
    }

    @Override
    public List<Сategory> getAllCategories() {
        log.info("Call getAllCategories");
        try {
            return categoryRepository.findAll();
        } catch (SQLException e) {
            String errorMsg = "Ошибка при получении списка категорий: " + e.getMessage();
            log.log(Level.SEVERE, errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }

    @Override
    public Сategory getCategoryById(Long id) {
        log.info("Call getCategoryById");
        try {
            return categoryRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Категория с id " + id + " не найдена")
            );
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка доступа к базе данных", e);
            throw new RuntimeException("Ошибка при получении категории из базы", e);
        }
    }

    @Override
    public void createCategory(Сategory category) {
        log.info("Call createCategory");
        try {
            categoryRepository.save(category);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при создании категории", e);
            throw new RuntimeException("Не удалось создать категорию: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateCategory(Сategory category) {
        log.info("Call updateCategory");
        try {
            categoryRepository.update(category);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при обновлении категории", e);
            throw new RuntimeException("Не удалось обновить категорию: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteCategory(Long id) {
        log.info("Call deleteCategory");
        try {
            Сategory category = getCategoryById(id);
            categoryRepository.delete(category);
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "Ошибка при удалении категории", e);
            throw new RuntimeException("Не удалось удалить категорию с ID " + id + ": " + e.getMessage(), e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка базы данных при удалении категории", e);
            throw new RuntimeException("Ошибка базы данных при удалении категории: " + e.getMessage(), e);
        }
    }
}

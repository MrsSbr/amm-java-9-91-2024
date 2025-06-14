package ru.vsu.amm.java.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.entities.Category;
import ru.vsu.amm.java.repository.CategoryRepository;
import ru.vsu.amm.java.service.impl.CategoryServiceImpl;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category testCategory;
    private List<Category> testCategories;

    @BeforeEach
    void setUp() throws Exception {
        Field repoField = CategoryServiceImpl.class.getDeclaredField("categoryRepository");
        repoField.setAccessible(true);
        repoField.set(categoryService, categoryRepository);

        testCategory = new Category(1L, "Technology");
        testCategories = Collections.singletonList(testCategory);
    }

    @Test
    void getAllCategoriesShouldReturnCategories() throws SQLException {
        when(categoryRepository.findAll()).thenReturn(testCategories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Technology", result.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getAllCategoriesShouldThrowRuntimeExceptionOnError() throws SQLException {
        when(categoryRepository.findAll()).thenThrow(new SQLException("Database error"));

        Exception exception = assertThrows(RuntimeException.class,
                () -> categoryService.getAllCategories());

        assertTrue(exception.getMessage().contains("Ошибка при получении списка категорий"));
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategoryByIdShouldReturnCategory() throws SQLException {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));

        Category result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals("Technology", result.getName());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void getCategoryByIdShouldThrowRuntimeExceptionWhenNotFound() throws SQLException {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> categoryService.getCategoryById(99L));

        assertTrue(exception.getMessage().contains("Категория с id 99 не найдена"));
        verify(categoryRepository, times(1)).findById(99L);
    }

    @Test
    void createCategoryShouldSaveCategory() throws SQLException {
        doAnswer(invocation -> {
            Category category = invocation.getArgument(0);
            category.setId(1L);
            return null;
        }).when(categoryRepository).save(any(Category.class));

        categoryService.createCategory(testCategory);

        assertNotNull(testCategory.getId());
        verify(categoryRepository, times(1)).save(testCategory);
    }

    @Test
    void updateCategoryShouldUpdateCategory() throws SQLException {
        doNothing().when(categoryRepository).update(any(Category.class));

        categoryService.updateCategory(testCategory);

        verify(categoryRepository, times(1)).update(testCategory);
    }

    @Test
    void deleteCategoryShouldDeleteCategory() throws SQLException {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        doNothing().when(categoryRepository).delete(any(Category.class));

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).delete(testCategory);
    }

    private Category createTestCategory(Long id) {
        Category category = new Category();
        category.setId(id);
        category.setName("Test Category");
        return category;
    }
}
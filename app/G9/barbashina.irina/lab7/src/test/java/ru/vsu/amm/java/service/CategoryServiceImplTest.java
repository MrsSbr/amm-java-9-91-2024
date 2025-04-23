package ru.vsu.amm.java.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.vsu.amm.java.entities.Category;
import ru.vsu.amm.java.repository.CategoryRepository;
import ru.vsu.amm.java.service.impl.CategoryServiceImpl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategoriesShouldReturnCategories() throws SQLException {
        // Arrange
        Category category1 = createTestCategory(1L);
        Category category2 = createTestCategory(2L);
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        // Act
        List<Category> categories = categoryService.getAllCategories();

        // Assert
        assertEquals(2, categories.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategoryByIdShouldReturnCategoryWhenExists() throws SQLException {
        // Arrange
        Category expected = createTestCategory(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(expected));

        // Act
        Category actual = categoryService.getCategoryById(1L);

        // Assert
        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void createCategoryShouldSaveNewCategory() throws SQLException {
        // Arrange
        Category newCategory = createTestCategory(null);

        doAnswer(invocation -> {
            Category c = invocation.getArgument(0);
            c.setId(1L);
            return null;
        }).when(categoryRepository).save(any(Category.class));

        // Act
        categoryService.createCategory(newCategory);

        // Assert
        assertNotNull(newCategory.getId());
        verify(categoryRepository, times(1)).save(newCategory);
    }

    @Test
    void updateCategoryShouldUpdateExistingCategory() throws SQLException {
        // Arrange
        Category existingCategory = createTestCategory(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));

        doNothing().when(categoryRepository).update(existingCategory);

        // Act
        categoryService.updateCategory(existingCategory);

        // Assert
        verify(categoryRepository, times(1)).update(existingCategory);
    }

    @Test
    void deleteCategoryShouldDeleteExistingCategory() throws SQLException {
        // Arrange
        Category existingCategory = createTestCategory(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));

        doNothing().when(categoryRepository).delete(existingCategory);

        // Act
        categoryService.deleteCategory(1L);

        // Assert
        verify(categoryRepository, times(1)).delete(existingCategory);
    }

    private Category createTestCategory(Long id) {
        Category category = new Category();
        category.setId(id);
        category.setName("Test Category");
        return category;
    }
}
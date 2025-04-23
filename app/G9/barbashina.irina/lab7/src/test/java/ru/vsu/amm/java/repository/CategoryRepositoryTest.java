package ru.vsu.amm.java.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.Category;
import ru.vsu.amm.java.util.TestDataSourceProvider;
import ru.vsu.amm.java.util.TestDatabaseInitializer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CategoryRepositoryTest {
    private CategoryRepository categoryRepository;
    private TestDatabaseInitializer dbInitializer;

    @BeforeEach
    void setUp() throws SQLException, IOException {
        dbInitializer = new TestDatabaseInitializer(TestDataSourceProvider.getTestDataSource());
        dbInitializer.initializeDatabase("src/test/resources/schema.sql", "src/test/resources/data.sql");

        categoryRepository = new CategoryRepository();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = TestDataSourceProvider.getTestDataSource().getConnection();
            stmt = conn.createStatement();
            stmt.execute("DROP ALL OBJECTS");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Test
    void findByIdShouldReturnCategoryWhenExists() throws SQLException {
        Optional<Category> category = categoryRepository.findById(1L);
        assertTrue(category.isPresent());
        assertEquals("Technology", category.get().getName());
    }

    @Test
    void findAllShouldReturnAllCategories() throws SQLException {
        List<Category> categories = categoryRepository.findAll();
        assertFalse(categories.isEmpty());
        assertEquals(2, categories.size());
    }

    @Test
    void saveShouldInsertNewCategory() throws SQLException {
        Category newCategory = new Category();
        newCategory.setName("Science");

        categoryRepository.save(newCategory);

        assertNotNull(newCategory.getId());
        Optional<Category> savedCategory = categoryRepository.findById(newCategory.getId());
        assertTrue(savedCategory.isPresent());
    }

    @Test
    void updateShouldModifyExistingCategory() throws SQLException {
        Category category = categoryRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName("Updated");

        categoryRepository.update(category);

        Category updatedCategory = categoryRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        assertEquals("Updated", updatedCategory.getName());
    }

    @Test
    void deleteShouldRemoveCategory() throws SQLException {
        Category category = categoryRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);

        Optional<Category> deletedCategory = categoryRepository.findById(1L);
        assertFalse(deletedCategory.isPresent());
    }
}
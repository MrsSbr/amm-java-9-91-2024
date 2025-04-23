package ru.vsu.amm.java.repository;

import org.junit.jupiter.api.*;
import ru.vsu.amm.java.entities.Article;
import ru.vsu.amm.java.util.TestDataSourceProvider;
import ru.vsu.amm.java.util.TestDatabaseInitializer;

import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ArticleRepositoryTest {
    private ArticleRepository articleRepository;
    private static DataSource testDataSource;

    @BeforeAll
    static void initTestDataSource() {
        testDataSource = TestDataSourceProvider.getTestDataSource();
    }

    @BeforeEach
    void setUp() throws SQLException, IOException {
        // Полностью изолированная инициализация тестовой БД
        TestDatabaseInitializer dbInitializer = new TestDatabaseInitializer(testDataSource);
        dbInitializer.initializeDatabase("src/test/resources/schema.sql", "src/test/resources/data.sql");

        // Создаем репозиторий с тестовым DataSource
        articleRepository = new ArticleRepository();
        injectTestDataSource(articleRepository, testDataSource);
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Полная очистка тестовой БД
        try (Connection conn = testDataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP ALL OBJECTS");
        }
    }

    private void injectTestDataSource(ArticleRepository repository, DataSource dataSource) {
        try {
            Field dataSourceField = ArticleRepository.class.getDeclaredField("dataSource");
            dataSourceField.setAccessible(true);
            dataSourceField.set(repository, dataSource);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject test DataSource", e);
        }
    }

    @Test
    void findByIdShouldReturnArticle() throws SQLException {
        Optional<Article> article = articleRepository.findById(1L);
        assertTrue(article.isPresent());
        assertEquals("Test Article", article.get().getTitle());
    }

    @Test
    void findAllShouldReturnAllArticles() throws SQLException {
        List<Article> articles = articleRepository.findAll();
        assertEquals(2, articles.size());
    }
}
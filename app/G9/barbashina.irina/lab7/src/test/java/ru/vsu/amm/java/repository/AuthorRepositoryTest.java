package ru.vsu.amm.java.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.Author;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AuthorRepositoryTest {
    private AuthorRepository authorRepository;
    private static DataSource testDataSource;

    @BeforeAll
    static void initTestDataSource() {
        testDataSource = TestDataSourceProvider.getTestDataSource();
    }

    @BeforeEach
    void setUp() throws SQLException, IOException {
        TestDatabaseInitializer dbInitializer = new TestDatabaseInitializer(testDataSource);
        dbInitializer.initializeDatabase("src/test/resources/schema.sql", "src/test/resources/data.sql");

        authorRepository = new AuthorRepository();
        injectTestDataSource(authorRepository, testDataSource);
    }

    private void injectTestDataSource(AuthorRepository repository, DataSource dataSource) {
        try {
            Field dataSourceField = AuthorRepository.class.getDeclaredField("dataSource");
            dataSourceField.setAccessible(true);
            dataSourceField.set(repository, dataSource);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject test DataSource", e);
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = testDataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP ALL OBJECTS");
        }
    }

    @Test
    void findByIdShouldReturnAuthor() throws SQLException {
        Optional<Author> author = authorRepository.findById(1L);
        assertTrue(author.isPresent());
        assertEquals("Doe", author.get().getSurname());
        assertEquals("John", author.get().getName());
    }

    @Test
    void findAllShouldReturnAllAuthors() throws SQLException {
        List<Author> authors = authorRepository.findAll();
        assertEquals(2, authors.size());
    }
}
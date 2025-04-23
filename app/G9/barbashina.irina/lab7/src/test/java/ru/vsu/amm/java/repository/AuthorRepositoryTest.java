package ru.vsu.amm.java.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.Author;
import ru.vsu.amm.java.util.TestDataSourceProvider;
import ru.vsu.amm.java.util.TestDatabaseInitializer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthorRepositoryTest {
    private AuthorRepository authorRepository;
    private TestDatabaseInitializer dbInitializer;

    @BeforeEach
    void setUp() throws SQLException, IOException {
        dbInitializer = new TestDatabaseInitializer(TestDataSourceProvider.getTestDataSource());
        dbInitializer.initializeDatabase("src/test/resources/schema.sql", "src/test/resources/data.sql");

        authorRepository = new AuthorRepository();
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
    void findByIdShouldReturnAuthorWhenExists() throws SQLException {
        Optional<Author> author = authorRepository.findById(1L);
        assertTrue(author.isPresent());
        assertEquals("Doe", author.get().getSurname());
    }

    @Test
    void findAllShouldReturnAllAuthors() throws SQLException {
        List<Author> authors = authorRepository.findAll();
        assertFalse(authors.isEmpty());
        assertEquals(2, authors.size());
    }

    @Test
    void saveShouldInsertNewAuthor() throws SQLException {
        Author newAuthor = new Author();
        newAuthor.setSurname("Smith");
        newAuthor.setName("John");
        newAuthor.setRegistrationDate(LocalDate.now());

        authorRepository.save(newAuthor);

        assertNotNull(newAuthor.getId());
        Optional<Author> savedAuthor = authorRepository.findById(newAuthor.getId());
        assertTrue(savedAuthor.isPresent());
    }

    @Test
    void updateShouldModifyExistingAuthor() throws SQLException {
        Author author = authorRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author.setSurname("Updated");

        authorRepository.update(author);

        Author updatedAuthor = authorRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        assertEquals("Updated", updatedAuthor.getSurname());
    }

    @Test
    void deleteShouldRemoveAuthor() throws SQLException {
        Author author = authorRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        authorRepository.delete(author);

        Optional<Author> deletedAuthor = authorRepository.findById(1L);
        assertFalse(deletedAuthor.isPresent());
    }
}
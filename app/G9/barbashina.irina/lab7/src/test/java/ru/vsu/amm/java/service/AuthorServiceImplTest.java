package ru.vsu.amm.java.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.vsu.amm.java.entities.Author;
import ru.vsu.amm.java.repository.AuthorRepository;
import ru.vsu.amm.java.service.impl.AuthorServiceImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAuthorsShouldReturnAuthors() throws SQLException {
        // Arrange
        Author author1 = createTestAuthor(1L);
        Author author2 = createTestAuthor(2L);
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        // Act
        List<Author> authors = authorService.getAllAuthors();

        // Assert
        assertEquals(2, authors.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void getAuthorByIdShouldReturnAuthorWhenExists() throws SQLException {
        Author expected = createTestAuthor(1L);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(expected));

        Author actual = authorService.getAuthorById(1L);

        assertEquals(expected, actual);
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void createAuthorShouldSaveNewAuthor() throws SQLException {
        // Arrange
        Author newAuthor = createTestAuthor(null);

        doAnswer(invocation -> {
            Author a = invocation.getArgument(0);
            a.setId(1L);
            return null;
        }).when(authorRepository).save(any(Author.class));

        // Act
        authorService.createAuthor(newAuthor);

        // Assert
        assertNotNull(newAuthor.getId());
        verify(authorRepository, times(1)).save(newAuthor);
    }

    @Test
    void updateAuthorShouldUpdateExistingAuthor() throws SQLException {
        // Arrange
        Author existingAuthor = createTestAuthor(1L);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(existingAuthor));

        doNothing().when(authorRepository).update(existingAuthor);

        // Act
        authorService.updateAuthor(existingAuthor);

        // Assert
        verify(authorRepository, times(1)).update(existingAuthor);
    }

    @Test
    void deleteAuthorShouldDeleteExistingAuthor() throws SQLException {
        // Arrange
        Author existingAuthor = createTestAuthor(1L);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(existingAuthor));

        doNothing().when(authorRepository).delete(existingAuthor);

        // Act
        authorService.deleteAuthor(1L);

        // Assert
        verify(authorRepository, times(1)).delete(existingAuthor);
    }

    private Author createTestAuthor(Long id) {
        Author author = new Author();
        author.setId(id);
        author.setSurname("Doe");
        author.setName("John");
        author.setPatronymic("Smith");
        author.setRegistrationDate(LocalDate.now());
        return author;
    }
}
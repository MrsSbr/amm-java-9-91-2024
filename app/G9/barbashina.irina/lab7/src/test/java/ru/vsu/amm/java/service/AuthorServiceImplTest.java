package ru.vsu.amm.java.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.entities.Author;
import ru.vsu.amm.java.repository.AuthorRepository;
import ru.vsu.amm.java.service.impl.AuthorServiceImpl;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
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
class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author testAuthor;
    private List<Author> testAuthors;

    @BeforeEach
    void setUp() throws Exception {
        Field repoField = AuthorServiceImpl.class.getDeclaredField("authorRepository");
        repoField.setAccessible(true);
        repoField.set(authorService, authorRepository);

        testAuthor = new Author(1L, "Doe", "John", null, LocalDate.now());
        testAuthors = Collections.singletonList(testAuthor);
    }

    @Test
    void getAllAuthorsShouldReturnAuthors() throws SQLException {
        when(authorRepository.findAll()).thenReturn(testAuthors);

        List<Author> result = authorService.getAllAuthors();

        assertEquals(1, result.size());
        assertEquals("Doe", result.get(0).getSurname());
        assertEquals("John", result.get(0).getName());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void getAllAuthorsShouldThrowRuntimeExceptionOnError() throws SQLException {
        when(authorRepository.findAll()).thenThrow(new SQLException("Database error"));

        Exception exception = assertThrows(RuntimeException.class,
                () -> authorService.getAllAuthors());

        assertTrue(exception.getMessage().contains("Ошибка при получении списка авторов"));
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void getAuthorByIdShouldReturnAuthor() throws SQLException {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(testAuthor));

        Author result = authorService.getAuthorById(1L);

        assertNotNull(result);
        assertEquals("Doe", result.getSurname());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void getAuthorByIdShouldThrowRuntimeExceptionWhenNotFound() throws SQLException {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> authorService.getAuthorById(99L));

        assertTrue(exception.getMessage().contains("Автор с id 99 не найден"));
        verify(authorRepository, times(1)).findById(99L);
    }

    @Test
    void createAuthorShouldSaveAuthor() throws SQLException {
        doAnswer(invocation -> {
            Author author = invocation.getArgument(0);
            author.setId(1L);
            return null;
        }).when(authorRepository).save(any(Author.class));

        authorService.createAuthor(testAuthor);

        assertNotNull(testAuthor.getId());
        verify(authorRepository, times(1)).save(testAuthor);
    }

    @Test
    void updateAuthorShouldUpdateAuthor() throws SQLException {
        doNothing().when(authorRepository).update(any(Author.class));

        authorService.updateAuthor(testAuthor);

        verify(authorRepository, times(1)).update(testAuthor);
    }

    @Test
    void deleteAuthorShouldDeleteAuthor() throws SQLException {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(testAuthor));
        doNothing().when(authorRepository).delete(any(Author.class));

        authorService.deleteAuthor(1L);

        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).delete(testAuthor);
    }
}
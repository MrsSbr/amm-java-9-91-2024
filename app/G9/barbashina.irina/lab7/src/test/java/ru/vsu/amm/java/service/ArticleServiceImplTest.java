package ru.vsu.amm.java.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.entities.Article;
import ru.vsu.amm.java.entities.Author;
import ru.vsu.amm.java.entities.Category;
import ru.vsu.amm.java.repository.ArticleRepository;
import ru.vsu.amm.java.service.impl.ArticleServiceImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {
    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    void getAllArticlesShouldReturnArticles() throws SQLException {
        // Arrange
        Article article1 = createTestArticle(1L);
        Article article2 = createTestArticle(2L);
        when(articleRepository.findAll()).thenReturn(Arrays.asList(article1, article2));

        // Act
        List<Article> articles = articleService.getAllArticles();

        // Assert
        assertEquals(2, articles.size());
        verify(articleRepository, times(1)).findAll();
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    void getArticleByIdShouldReturnArticleWhenExists() throws SQLException {
        // Arrange
        Article expected = createTestArticle(1L);
        when(articleRepository.findById(1L)).thenReturn(Optional.of(expected));

        // Act
        Article actual = articleService.getArticleById(1L);

        // Assert
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(articleRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    void getArticleByIdShouldThrowExceptionWhenNotFound() throws SQLException {
        // Arrange
        when(articleRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> articleService.getArticleById(99L));
        verify(articleRepository, times(1)).findById(99L);
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    void createArticleShouldSaveNewArticle() throws SQLException {
        // Arrange
        Article newArticle = createTestArticle(null);
        doAnswer(invocation -> {
            Article a = invocation.getArgument(0);
            a.setId(1L); // Эмулируем установку ID
            return null;
        }).when(articleRepository).save(any(Article.class));

        // Act
        articleService.createArticle(newArticle);

        // Assert
        assertNotNull(newArticle.getId());
        assertEquals(1L, newArticle.getId());
        verify(articleRepository, times(1)).save(newArticle);
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    void updateArticleShouldUpdateExistingArticle() throws SQLException {
        // Arrange
        Article existingArticle = createTestArticle(1L);
        when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));
        doNothing().when(articleRepository).update(existingArticle);

        // Act
        articleService.updateArticle(existingArticle);

        // Assert
        verify(articleRepository, times(1)).findById(1L);
        verify(articleRepository, times(1)).update(existingArticle);
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    void updateArticleShouldThrowExceptionWhenNotFound() throws SQLException {
        // Arrange
        Article nonExistingArticle = createTestArticle(99L);
        when(articleRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> articleService.updateArticle(nonExistingArticle));
        verify(articleRepository, times(1)).findById(99L);
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    void deleteArticleShouldDeleteExistingArticle() throws SQLException {
        // Arrange
        Article existingArticle = createTestArticle(1L);
        when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));
        doNothing().when(articleRepository).delete(existingArticle);

        // Act
        articleService.deleteArticle(1L);

        // Assert
        verify(articleRepository, times(1)).findById(1L);
        verify(articleRepository, times(1)).delete(existingArticle);
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    void deleteArticleShouldThrowExceptionWhenNotFound() throws SQLException {
        // Arrange
        when(articleRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> articleService.deleteArticle(99L));
        verify(articleRepository, times(1)).findById(99L);
        verifyNoMoreInteractions(articleRepository);
    }

    private Article createTestArticle(Long id) {
        Article article = new Article();
        article.setId(id);
        article.setTitle("Test Article");
        article.setContent("Test Content");
        article.setDatePublication(LocalDate.now());

        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        article.setCategory(category);

        Author author = new Author();
        author.setId(1L);
        author.setName("Test");
        author.setSurname("Author");
        article.setAuthor(author);

        return article;
    }
}
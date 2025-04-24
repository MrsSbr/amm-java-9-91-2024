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
class ArticleServiceImplTest {
    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private Article testArticle;
    private List<Article> testArticles;

    @BeforeEach
    void setUp() throws Exception {
        Field repoField = ArticleServiceImpl.class.getDeclaredField("articleRepository");
        repoField.setAccessible(true);
        repoField.set(articleService, articleRepository);

        Author author = new Author(1L, "Doe", "John", null, LocalDate.now());
        Category category = new Category(1L, "Technology");
        testArticle = new Article(1L, "Test Article", "Content",
                LocalDate.now(), category, author);
        testArticles = Collections.singletonList(testArticle);
    }

    @Test
    void getAllArticlesShouldReturnArticles() throws SQLException {
        when(articleRepository.findAll()).thenReturn(testArticles);

        List<Article> result = articleService.getAllArticles();

        assertEquals(1, result.size());
        assertEquals("Test Article", result.get(0).getTitle());
        verify(articleRepository, times(1)).findAll();
    }

    @Test
    void getAllArticlesShouldThrowRuntimeExceptionOnError() throws SQLException {
        when(articleRepository.findAll()).thenThrow(new SQLException("Database error"));

        Exception exception = assertThrows(RuntimeException.class,
                () -> articleService.getAllArticles());

        assertTrue(exception.getMessage().contains("Ошибка при получении списка статей"));
        verify(articleRepository, times(1)).findAll();
    }

    @Test
    void getArticleByIdShouldReturnArticle() throws SQLException {
        when(articleRepository.findById(1L)).thenReturn(Optional.of(testArticle));

        Article result = articleService.getArticleById(1L);

        assertNotNull(result);
        assertEquals("Test Article", result.getTitle());
        verify(articleRepository, times(1)).findById(1L);
    }

    @Test
    void getArticleByIdShouldThrowRuntimeExceptionWhenNotFound() throws SQLException {
        when(articleRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> articleService.getArticleById(99L));

        assertTrue(exception.getMessage().contains("Статья с id 99 не найдена"));
        verify(articleRepository, times(1)).findById(99L);
    }

    @Test
    void createArticleShouldSaveArticle() throws SQLException {
        doAnswer(invocation -> {
            Article article = invocation.getArgument(0);
            article.setId(1L);
            return null;
        }).when(articleRepository).save(any(Article.class));

        articleService.createArticle(testArticle);

        assertNotNull(testArticle.getId());
        verify(articleRepository, times(1)).save(testArticle);
    }

    @Test
    void updateArticleShouldUpdateArticle() throws SQLException {
        doNothing().when(articleRepository).update(any(Article.class));

        articleService.updateArticle(testArticle);

        verify(articleRepository, times(1)).update(testArticle);
    }

    @Test
    void deleteArticleShouldDeleteArticle() throws SQLException {
        when(articleRepository.findById(1L)).thenReturn(Optional.of(testArticle));
        doNothing().when(articleRepository).delete(any(Article.class));

        articleService.deleteArticle(1L);

        verify(articleRepository, times(1)).findById(1L);
        verify(articleRepository, times(1)).delete(testArticle);
    }
}
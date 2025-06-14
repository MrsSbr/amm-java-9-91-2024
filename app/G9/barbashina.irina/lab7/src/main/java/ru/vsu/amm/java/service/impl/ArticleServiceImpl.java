package ru.vsu.amm.java.service.impl;

import ru.vsu.amm.java.entities.Article;
import ru.vsu.amm.java.repository.ArticleRepository;
import ru.vsu.amm.java.service.ArticleService;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleServiceImpl implements ArticleService {
    private static final Logger log = Logger.getLogger(ArticleServiceImpl.class.getName());

    private final ArticleRepository articleRepository;
    public ArticleServiceImpl() {
        log.info("Call ArticleServiceImpl constructor");
        articleRepository = new ArticleRepository();
    }

    @Override
    public List<Article> getAllArticles() {
        log.info("Call getAllArticles");
        try {
            return articleRepository.findAll();
        } catch (SQLException e) {
            String errorMsg = "Ошибка при получении списка статей: " + e.getMessage();
            log.log(Level.SEVERE, errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }

    @Override
    public Article getArticleById(Long id) {
        log.info("Call getArticleById");
        try {
            return articleRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Статья с id " + id + " не найдена")
            );
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка доступа к базе данных", e);
            throw new RuntimeException("Ошибка при получении статьи из базы", e);
        }
    }

    @Override
    public void createArticle(Article article) {
        log.info("Call createArticle");
        try {
            articleRepository.save(article);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при создании статьи", e);
            throw new RuntimeException("Не удалось создать статью: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateArticle(Article article) {
        log.info("Call updateArticle");
        try {
            articleRepository.update(article);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при обновлении статьи", e);
            throw new RuntimeException("Не удалось обновить статью: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteArticle(Long id) {
        log.info("Call deleteArticle");
        try {
            Article article = getArticleById(id);
            articleRepository.delete(article);
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "Ошибка при удалении статьи", e);
            throw new RuntimeException("Не удалось удалить статью с ID " + id + ": " + e.getMessage(), e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка базы данных при удалении статьи", e);
            throw new RuntimeException("Ошибка базы данных при удалении статьи: " + e.getMessage(), e);
        }
    }
}

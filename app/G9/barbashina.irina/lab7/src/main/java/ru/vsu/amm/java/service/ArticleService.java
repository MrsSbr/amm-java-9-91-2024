package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Article;

import java.util.List;

public interface ArticleService {
    List<Article> getAllArticles();
    Article getArticleById(Long id);
    void createArticle(Article article);
    void updateArticle(Article article);
    void deleteArticle(Long id);
}

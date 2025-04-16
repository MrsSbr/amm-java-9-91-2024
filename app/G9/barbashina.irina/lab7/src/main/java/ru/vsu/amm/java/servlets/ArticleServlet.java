package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entities.Article;
import ru.vsu.amm.java.entities.Author;
import ru.vsu.amm.java.entities.Category;
import ru.vsu.amm.java.service.ArticleService;
import ru.vsu.amm.java.service.AuthorService;
import ru.vsu.amm.java.service.CategoryService;
import ru.vsu.amm.java.service.impl.ArticleServiceImpl;
import ru.vsu.amm.java.service.impl.AuthorServiceImpl;
import ru.vsu.amm.java.service.impl.CategoryServiceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/articles")
public class ArticleServlet extends HttpServlet {
    private final ArticleService articleService = new ArticleServiceImpl();
    private final AuthorService authorService = new AuthorServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("new".equals(action)) {
            List<Category> categories = categoryService.getAllCategories();
            List<Author> authors = authorService.getAllAuthors();

            req.setAttribute("categories", categories);
            req.setAttribute("authors", authors);
            req.setAttribute("article", new Article());
            req.getRequestDispatcher("/WEB-INF/article-form.jsp").forward(req, resp);
        }
        else if ("edit".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            Article article = articleService.getArticleById(id);

            List<Category> categories = categoryService.getAllCategories();
            List<Author> authors = authorService.getAllAuthors();

            req.setAttribute("categories", categories);
            req.setAttribute("authors", authors);
            req.setAttribute("article", article);
            req.getRequestDispatcher("/WEB-INF/article-form.jsp").forward(req, resp);
        }
        else if ("delete".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            articleService.deleteArticle(id);
            resp.sendRedirect(req.getContextPath() + "/articles");
        }
        else {
            List<Article> articles = articleService.getAllArticles();
            req.setAttribute("articles", articles);
            req.getRequestDispatcher("/WEB-INF/article-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String action = req.getParameter("action");
        Long id = null;

        if ("update".equals(action)) {
            id = req.getParameter("id") != null && !req.getParameter("id").isEmpty() ?
                    Long.parseLong(req.getParameter("id")) : null;
        }

        try {
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            String categoryIdStr = req.getParameter("category");
            String authorIdStr = req.getParameter("author");

            if (title == null || title.trim().isEmpty() ||
                    content == null || content.trim().isEmpty() ||
                    categoryIdStr == null || categoryIdStr.trim().isEmpty() ||
                    authorIdStr == null || authorIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Все обязательные поля должны быть заполнены");
            }

            Article article = new Article();
            article.setId(id);
            article.setTitle(title.trim());
            article.setContent(content.trim());

            Long categoryId = Long.parseLong(categoryIdStr);
            Long authorId = Long.parseLong(authorIdStr);

            Category category = categoryService.getCategoryById(categoryId);
            Author author = authorService.getAuthorById(authorId);

            if (category == null) {
                throw new IllegalArgumentException("Выбранная категория не существует");
            }
            if (author == null) {
                throw new IllegalArgumentException("Выбранный автор не существует");
            }

            article.setCategory(category);
            article.setAuthor(author);
            article.setDatePublication(new Date());

            if ("insert".equals(action)) {
                articleService.createArticle(article);
            } else if ("update".equals(action)) {
                articleService.updateArticle(article);
            }

            resp.sendRedirect(req.getContextPath() + "/articles");
        } catch (NumberFormatException e) {
            handleError(req, resp, "Неверный формат ID категории или автора", action);
        } catch (IllegalArgumentException e) {
            handleError(req, resp, e.getMessage(), action);
        } catch (Exception e) {
            handleError(req, resp, "Ошибка при сохранении статьи: " + e.getMessage(), action);
        }
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp,
                             String errorMessage, String action)
            throws ServletException, IOException {
        req.setAttribute("error", errorMessage);

        List<Category> categories = categoryService.getAllCategories();
        List<Author> authors = authorService.getAllAuthors();

        req.setAttribute("categories", categories);
        req.setAttribute("authors", authors);

        Article article = new Article();
        article.setTitle(req.getParameter("title"));
        article.setContent(req.getParameter("content"));

        if ("update".equals(action)) {
            article.setId(req.getParameter("id") != null && !req.getParameter("id").isEmpty() ?
                    Long.parseLong(req.getParameter("id")) : null);
        }

        req.setAttribute("article", article);

        req.getRequestDispatcher("/WEB-INF/article-form.jsp").forward(req, resp);
    }
}
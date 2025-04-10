package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entities.Article;
import ru.vsu.amm.java.service.ArticleService;
import ru.vsu.amm.java.service.AuthorService;
import ru.vsu.amm.java.service.CategoryService;
import ru.vsu.amm.java.service.impl.ArticleServiceImpl;
import ru.vsu.amm.java.service.impl.AuthorServiceImpl;
import ru.vsu.amm.java.service.impl.CategoryServiceImpl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ArticleServlet extends HttpServlet {
    private final ArticleService articleService = new ArticleServiceImpl();
    private final AuthorService authorService = new AuthorServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(req, resp);
                    break;
                case "edit":
                    showEditForm(req, resp);
                    break;
                case "delete":
                    deleteArticle(req, resp);
                    break;
                case "list":
                default:
                    listArticles(req, resp);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "insert":
                    insertArticle(req, resp);
                    break;
                case "update":
                    updateArticle(req, resp);
                    break;
                default:
                    listArticles(req, resp);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listArticles(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Article> articles = articleService.getAllArticles();
        req.setAttribute("articles", articles);
        req.getRequestDispatcher("/article-list.jsp").forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("authors", authorService.getAllAuthors());
        req.setAttribute("categories", categoryService.getAllCategories());
        req.getRequestDispatcher("/article-form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Article article = articleService.getArticleById(id);
        req.setAttribute("article", article);
        req.setAttribute("authors", authorService.getAllAuthors());
        req.setAttribute("categories", categoryService.getAllCategories());
        req.getRequestDispatcher("/article-form.jsp").forward(req, resp);
    }

    private void insertArticle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        try {
            Article article = getArticleFromRequest(req);
            articleService.createArticle(article);
            resp.sendRedirect("articles");
        } catch (Exception e) {
            req.setAttribute("error", "Ошибка при создании статьи: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    private void updateArticle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Article article = getArticleFromRequest(req);
            article.setId(id);
            articleService.updateArticle(article);
            resp.sendRedirect("articles");
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Некорректный ID статьи");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Ошибка при обновлении статьи: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    private void deleteArticle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            articleService.deleteArticle(id);
            resp.sendRedirect("articles");
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Некорректный ID статьи");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Ошибка при удалении статьи: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    private Article getArticleFromRequest(HttpServletRequest req) throws ServletException {
        try {
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            Date publicationDate = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("publicationDate"));
            Long authorId = Long.parseLong(req.getParameter("authorId"));
            Long categoryId = Long.parseLong(req.getParameter("categoryId"));

            Article article = new Article();
            article.setTitle(title);
            article.setСontent(content);
            article.setDatePublication(publicationDate);
            article.setAuthor(authorService.getAuthorById(authorId));
            article.setCategory(categoryService.getCategoryById(categoryId));

            return article;
        } catch (ParseException e) {
            throw new ServletException("Некорректный формат даты", e);
        } catch (NumberFormatException e) {
            throw new ServletException("Некорректный ID автора или категории", e);
        } catch (Exception e) {
            throw new ServletException("Ошибка при обработке данных статьи", e);
        }
    }
}

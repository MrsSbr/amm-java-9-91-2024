package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entities.Article;
import ru.vsu.amm.java.service.ArticleService;
import ru.vsu.amm.java.service.impl.ArticleServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("")
public class MainServlet extends HttpServlet {
    private final ArticleService articleService = new ArticleServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Article> articles = articleService.getAllArticles();
        req.setAttribute("articles", articles);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
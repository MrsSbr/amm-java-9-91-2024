package ru.vsu.amm.java.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/categories/add")
public class AddCategoryServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AddCategoryServlet.class);
    private CategoryService categoryService;

    @Override
    public void init() {
        this.categoryService = new CategoryService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        String title = req.getParameter("title");

        try {
            categoryService.addCategory(username, title);
            logger.info("Added new category '{}' for user '{}'", title, username);
        } catch (DatabaseException e) {
            logger.error("Error adding category: {}", e.getMessage());
            req.getSession().setAttribute("error", "Failed to add category");
        }

        resp.sendRedirect("/notes");
    }
}
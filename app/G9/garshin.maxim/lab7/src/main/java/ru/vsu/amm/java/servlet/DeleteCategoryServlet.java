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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/categories/delete")
public class DeleteCategoryServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DeleteCategoryServlet.class);
    private CategoryService categoryService;


    @Override
    public void init() {
        this.categoryService = new CategoryService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        long categoryId = Long.parseLong(req.getParameter("categoryId"));

        HttpSession session = req.getSession();

        try {
            categoryService.deleteCategory(username, categoryId);
            logger.info("Deleted category {} for user '{}'", categoryId, username);
        } catch (NumberFormatException e) {
            logger.error("Invalid category ID: {}", e.getMessage());
            session.setAttribute("error", "Invalid category ID");
        } catch (DatabaseException e) {
            logger.error("Error deleting category: {}", e.getMessage());
            session.setAttribute("error", e.getMessage());
        }

        resp.sendRedirect("/notes");
    }
}
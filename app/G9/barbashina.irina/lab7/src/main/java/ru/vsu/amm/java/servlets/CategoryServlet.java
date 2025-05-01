package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entities.Category;
import ru.vsu.amm.java.service.CategoryService;
import ru.vsu.amm.java.service.impl.CategoryServiceImpl;

import java.io.IOException;

@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {
    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("new".equals(action)) {
            req.setAttribute("category", new Category());
            req.getRequestDispatcher("/WEB-INF/category-form.jsp").forward(req, resp);
        }
        else if ("edit".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            Category category = categoryService.getCategoryById(id);
            req.setAttribute("category", category);
            req.getRequestDispatcher("/WEB-INF/category-form.jsp").forward(req, resp);
        }
        else if ("delete".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            categoryService.deleteCategory(id);
            resp.sendRedirect(req.getContextPath() + "/categories");
        }
        else {
            req.setAttribute("categories", categoryService.getAllCategories());
            req.getRequestDispatcher("/WEB-INF/category-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String action = req.getParameter("action");
        Long id = req.getParameter("id") != null && !req.getParameter("id").isEmpty() ?
                Long.parseLong(req.getParameter("id")) : null;

        try {
            Category category = new Category();
            category.setId(id);
            category.setName(req.getParameter("name"));

            if ("insert".equals(action)) {
                categoryService.createCategory(category);
            } else if ("update".equals(action)) {
                categoryService.updateCategory(category);
            }

            resp.sendRedirect(req.getContextPath() + "/categories");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }
    }
}
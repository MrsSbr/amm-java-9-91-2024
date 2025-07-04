package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getPathInfo();

        if (action == null || action.equals("/list")) {
            getServletContext().setAttribute("users", userRepository.getAll());
            getServletContext().getRequestDispatcher("/userlist.jsp").forward(req, resp);
        } else if (action.equals("/new")) {
            getServletContext().getRequestDispatcher("/userform.jsp").forward(req, resp);
        } else if (action.equals("/edit")) {
            long id = Long.parseLong(req.getParameter("id"));
            req.setAttribute("user", userRepository.getById(id));
            req.getRequestDispatcher("/userform.jsp").forward(req, resp);
        } else if (action.equals("/delete")) {
            long id = Long.parseLong(req.getParameter("id"));
            userRepository.delete(id);
            resp.sendRedirect(req.getContextPath() + "/users/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getPathInfo();

        if (action.equals("/save")) {

        }
    }
}
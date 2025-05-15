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

@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getPathInfo();

        if (action == null || action.equals("/login")) {
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        } else if (action.equals("/register")) {
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        } else if (action.equals("/logout")) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getPathInfo();
        User user = null;
        if (action.equals("/login")) {
            String login = req.getParameter("login");
            String password = req.getParameter("password");

            user = userRepository.findByLoginAndPassword(login, password);
        } else if (action.equals("/register")) {
            user = new User();
            user.setPassword(req.getParameter("password"));
            user.setPhoneNumber(req.getParameter("phoneNumber"));
            user.setEmail(req.getParameter("email"));
            user.setLogin(req.getParameter("login"));
            user.setUsername(req.getParameter("username"));
            userRepository.save(user);
        }
        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            getServletContext().setAttribute("error", "Invalid login or password");
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}

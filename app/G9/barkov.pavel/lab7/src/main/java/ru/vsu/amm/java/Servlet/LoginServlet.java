package ru.vsu.amm.java.Servlet;

import ru.vsu.amm.java.Exeption.UnCorrectDataException;
import ru.vsu.amm.java.Repository.Entities.Shareholder;
import ru.vsu.amm.java.Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    public LoginServlet() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("Email");
        String password = req.getParameter("Password");
        try {
            Shareholder user = userService.login(email, password);
            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getFio());
            resp.sendRedirect("/get-my");
        }
        catch (UnCorrectDataException e) {
            resp.sendRedirect("/register");
        }
        catch (RuntimeException | SQLException e) {
            resp.sendRedirect("/register");
        }
    }
}

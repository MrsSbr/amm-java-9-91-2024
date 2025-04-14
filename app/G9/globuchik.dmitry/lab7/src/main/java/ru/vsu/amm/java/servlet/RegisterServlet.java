package ru.vsu.amm.java.servlet;

import lombok.AllArgsConstructor;

import ru.vsu.amm.java.services.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@AllArgsConstructor
@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String nickname = req.getParameter("nickname");
        String phonenumber = req.getParameter("phonenumber");

        AuthenticationService service = new AuthenticationService();  //TODO EXCEPTION HANDLER + REDIRECT TO HOME + ADD ALL ACHIEVEMENTS TO earnedachievement of this user
        service.register(login, password, email, nickname, phonenumber);

        HttpSession session = req.getSession();
        session.setAttribute("login", login);
    }
}

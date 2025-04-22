package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.services.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = userService.findUserByLoginAndPassword(login, password);

        if (user != null) {
            session.setAttribute("user", user);

            String redirectTo = (String) session.getAttribute("redirectTo");
            if (redirectTo == null || redirectTo.isEmpty()) {
                redirectTo = "index.jsp";
            }

            session.removeAttribute("redirectTo");
            response.sendRedirect(redirectTo);
        } else {
            String errorMessage = URLEncoder.encode("Неверный логин или пароль", StandardCharsets.UTF_8);
            response.sendRedirect("login.jsp?error=" + errorMessage);
        }
    }
}

package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/register")
public class RegisterUserServlet extends HttpServlet {
    private UserRepository userRepository = new UserRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phoneNumber");

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNumber(phone);

        try {
            userRepository.save(user);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            String redirectTo = (String) request.getSession().getAttribute("redirectTo");
            if (redirectTo == null || redirectTo.isEmpty()) {
                redirectTo = "index.jsp";
            }

            String successMessage = URLEncoder.encode("Регистрация успешна, войдите в свой аккаунт", StandardCharsets.UTF_8.toString());
            response.sendRedirect("login.jsp?message=" + successMessage + "&redirectTo=" + URLEncoder.encode(redirectTo, StandardCharsets.UTF_8.toString()));
        } catch (RuntimeException e) {
            String errorMessage;
            if (e.getMessage().contains("Duplicate key detected")) {
                errorMessage = URLEncoder.encode("Такой логин уже существует", StandardCharsets.UTF_8.toString());
            } else {
                errorMessage = URLEncoder.encode("Ошибка при регистрации. Попробуйте снова", StandardCharsets.UTF_8.toString());
            }
            response.sendRedirect("register.jsp?error=" + errorMessage);
        }
    }
}

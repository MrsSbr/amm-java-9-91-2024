package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");  // Email – строка
        String password = request.getParameter("password");

        UserRepository userRepository = new UserRepository();
        User user = userRepository.getByEmail(email);  // ✅ Теперь мы можем искать по email

        if (user != null && user.getPassword().equals(password)) {  // Тут лучше добавить хеширование пароля
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("dashboard.jsp");  // Перенаправление в личный кабинет
        } else {
            response.sendRedirect("login.jsp?error=1");
        }
    }
}


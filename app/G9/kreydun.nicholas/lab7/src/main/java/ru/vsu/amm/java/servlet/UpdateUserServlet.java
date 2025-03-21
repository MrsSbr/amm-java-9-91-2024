package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;

import java.io.IOException;

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (newPassword != null && !newPassword.isEmpty()) {
            if (!newPassword.equals(confirmPassword)) {
                session.setAttribute("error", "Пароли не совпадают!");
                response.sendRedirect("dashboard.jsp");
                return;
            }
            user.setPassword(newPassword);
        }

        user.setUsername(username);
        user.setEmail(email);

        UserRepository userRepository = new UserRepository();
        boolean isUpdated = userRepository.update(user);

        if (isUpdated) {
            session.setAttribute("user", user);
            session.setAttribute("success", "Данные успешно обновлены");
        } else {
            session.setAttribute("error", "Ошибка обновления данных");
        }
        response.sendRedirect("dashboard.jsp");
    }
}
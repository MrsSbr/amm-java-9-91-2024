package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.services.UserService;

import java.io.IOException;

import java.util.UUID;

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        Object userIdAttr = session.getAttribute("userId");
        if (userIdAttr == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UUID userId = UUID.fromString(userIdAttr.toString());
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals(confirmPassword)) {
            session.setAttribute("error", "Пароли не совпадают!");
            response.sendRedirect("dashboard.jsp");
            return;
        }

        boolean isUpdated = userService.update(userId, username, email, newPassword);

        if (isUpdated) {
            User updatedUser = userService.getById(userId);
            session.setAttribute("user", updatedUser);

            session.setAttribute("success", "Данные успешно обновлены");
        } else {
            session.setAttribute("error", "Ошибка обновления данных");
        }

        response.sendRedirect("dashboard.jsp");
    }
}

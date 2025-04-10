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

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UUID userId = user.getId();
        boolean isDeleted = userService.delete(userId);

        if (isDeleted) {
            session.invalidate();
            response.sendRedirect("login.jsp");
        } else {
            session.setAttribute("error", "Ошибка при удалении аккаунта.");
            response.sendRedirect("dashboard.jsp");
        }
    }
}

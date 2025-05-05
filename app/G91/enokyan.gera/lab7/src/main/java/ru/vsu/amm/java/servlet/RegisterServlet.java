package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/auth/register")
public class RegisterServlet extends HttpServlet {
    private final UserService userService;

    public RegisterServlet() {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //response.setContentType("text/html; charset=UTF-8");

        try {
            String nickname = request.getParameter("nickname");
            String password = request.getParameter("password");

            if (userService.getUserByNickname(nickname) != null) {
                response.sendRedirect("/auth/register?error=nicknameIsAlreadyTaken");
                return;
            }

            userService.addUser(nickname, password, List.of(Role.Player));

            response.sendRedirect("/auth/login?success=1");

        } catch (Exception e) {
            response.sendRedirect("/auth/register?error=unknown");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}

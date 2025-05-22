package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.service.UserService;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/auth/register")
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RegisterServlet.class.getName());

    private final UserService userService;

    public RegisterServlet() {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String ip = request.getRemoteAddr();

        if (userService.getUserByNickname(nickname) != null) {
            logger.log(Level.WARNING, MessageFormat.format("Неудачная попытка регистрации с nickname={0}, ip={1}", nickname, ip));
            response.sendRedirect("/auth/register?error=nicknameIsAlreadyTaken");
            return;
        }

        userService.addUser(nickname, password, List.of(Role.Player));

        logger.log(Level.WARNING, MessageFormat.format("Успешная регистрации с nickname={0}, ip={1}", nickname, ip));
        response.sendRedirect("/auth/login?success=1");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.FINE, MessageFormat.format("Запрос страницы регистрации с IP={0}", request.getRemoteAddr()));
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}

package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.UserService;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    private final UserService userService;

    public LoginServlet() {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String ip = request.getRemoteAddr();

        logger.log(Level.INFO, MessageFormat.format("Попытка входа с nickname={0}, ip={1}", nickname, ip));

        UserEntity user = userService.getUserByNickname(nickname);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("isReferee", user.getRoles().contains(Role.Referee));
            logger.log(Level.INFO, MessageFormat.format("Успешный вход с nickname={0}, ip={1}", nickname, ip));
            response.sendRedirect("/leaderboard");
        } else {
            logger.log(Level.WARNING, MessageFormat.format("Неудачна попытка входа с nickname={0}, ip={1}", nickname, ip));
            response.sendRedirect("/auth/login?error=incorrectCredentials");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.FINE, MessageFormat.format("Запрос страницы входа с IP={0}", request.getRemoteAddr()));
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
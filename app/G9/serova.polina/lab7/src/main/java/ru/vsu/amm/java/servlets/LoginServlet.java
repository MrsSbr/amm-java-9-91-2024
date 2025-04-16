package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.UserService;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private final UserService userService;

    public LoginServlet() {
        userService = new UserService();
    }

    public LoginServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String ipAddress = request.getRemoteAddr();

        logger.info("Попытка входа с email: {} (IP: {})", email, ipAddress);

        try {
            String password = request.getParameter("password");
            UserEntity user = userService.getUserByEmail(email);

            if (user != null && checkPassword(password, user.getPasswordHash())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                logger.info("Успешный вход пользователя: {} (ID: {}, Роль: {})",
                        email, user.getId(), user.getRole());

                response.sendRedirect("protected/home");
            } else {
                logger.warn("Неудачная попытка входа для email: {} (IP: {})", email, ipAddress);
                response.sendRedirect("login.jsp?error=1");
            }
        } catch (Exception e) {
            logger.error("Ошибка при обработке входа для email: {} (IP: {}): {}",
                    email, ipAddress, e.getMessage(), e);
            response.sendRedirect("login.jsp?error=2");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        logger.debug("Запрос страницы входа (IP: {})", request.getRemoteAddr());
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private boolean checkPassword(String rawPassword, String storedHash) {
        return BCrypt.checkpw(rawPassword, storedHash);
    }
}